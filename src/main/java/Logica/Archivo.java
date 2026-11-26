package Logica;

import com.example.proyecto.Labels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class Archivo {
    private static String rutaArchivo;

    // Carga un CSV incluido en resources o en la carpeta del proyecto como grafo dirigido
    public static Grafo cargarArchivo(String nombre) {
        return cargarArchivo(nombre, true);
    }

    // Carga un CSV por nombre indicando si el grafo sera dirigido o no dirigido
    public static Grafo cargarArchivo(String nombre, boolean dirigido) {
        try (BufferedReader bf = abrirArchivo(nombre)) {
            return procesarCsv(bf, dirigido);
        } catch (IOException e) {
            Labels.texto("Error al cargar el archivo CSV: " + nombre);
            return new Grafo(dirigido);
        }
    }

    // Carga un archivo CSV seleccionado desde el explorador de archivos
    public static Grafo cargarArchivo(File archivo, boolean dirigido) {
        if (archivo == null || !archivo.exists()) {
            Labels.texto("El archivo seleccionado no existe");
            return new Grafo(dirigido);
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(archivo))) {
            return procesarCsv(bf, dirigido);
        } catch (IOException e) {
            Labels.texto("Error al leer el archivo: " + archivo.getName());
            return new Grafo(dirigido);
        }
    }

    // Lee linea por linea, valida la estructura del CSV y construye lista y matriz de adyacencia
    private static Grafo procesarCsv(BufferedReader bf, boolean dirigido) throws IOException {
        Grafo grafo = new Grafo(dirigido);
        int idArista = 0;
        int aristasImportadas = 0;
        int numeroLinea = 1;
        ArrayList<String> errores = new ArrayList<>();

        // Leer la primera linea para validar encabezados
        String encabezado = bf.readLine();
        if (encabezado == null || encabezado.trim().isEmpty()) {
            Labels.texto("Error: El archivo CSV esta vacio");
            return grafo;
        }

        // Eliminar posible marca BOM de archivos UTF-8
        encabezado = encabezado.replace("\uFEFF", "").trim();
        String[] columnas = encabezado.split(",", -1);
        if (columnas.length != 3
                || !columnas[0].trim().equalsIgnoreCase("origen")
                || !columnas[1].trim().equalsIgnoreCase("destino")
                || !columnas[2].trim().equalsIgnoreCase("peso")) {
            Labels.texto("Error de formato: El CSV debe tener exactamente las columnas 'origen,destino,peso'");
            return grafo;
        }

        String linea;
        while ((linea = bf.readLine()) != null) {
            numeroLinea++;
            if (linea.trim().isEmpty()) {
                continue;
            }

            // Se usa -1 en split para detectar columnas vacias al final de la fila
            String[] datosArchivo = linea.split(",", -1);

            // Validar que el registro tenga exactamente 3 columnas
            if (datosArchivo.length != 3) {
                errores.add("Linea " + numeroLinea + ": registro incompleto o con columnas extra (" + linea + ")");
                continue;
            }

            String origen = datosArchivo[0].trim();
            String destino = datosArchivo[1].trim();
            String pesoTexto = datosArchivo[2].trim();

            // Validar que no haya vertices vacios
            if (origen.isEmpty() || destino.isEmpty()) {
                errores.add("Linea " + numeroLinea + ": conexion con vertice vacio");
                continue;
            }

            // Evitar conexiones de un vertice hacia si mismo
            if (origen.equalsIgnoreCase(destino)) {
                errores.add("Linea " + numeroLinea + ": el vertice origen y destino son iguales (" + origen + ")");
                continue;
            }

            // Validar que el peso sea un numero entero no negativo
            if (pesoTexto.isEmpty()) {
                errores.add("Linea " + numeroLinea + ": peso vacio");
                continue;
            }

            int peso;
            try {
                peso = Integer.parseInt(pesoTexto);
            } catch (NumberFormatException e) {
                errores.add("Linea " + numeroLinea + ": peso no numerico ('" + pesoTexto + "')");
                continue;
            }

            if (peso < 0) {
                errores.add("Linea " + numeroLinea + ": peso negativo no permitido (" + peso + ")");
                continue;
            }

            // Validar conexiones duplicadas
            if (grafo.existeArista(origen, destino)) {
                errores.add("Linea " + numeroLinea + ": conexion duplicada (" + origen + " -> " + destino + ")");
                continue;
            }

            // Agregar la conexion al grafo
            grafo.agregarArista(idArista, origen, destino, peso);
            idArista++;

            // Si el grafo es no dirigido se agrega tambien la conexion de regreso
            if (!dirigido && !grafo.existeArista(destino, origen)) {
                grafo.agregarArista(idArista, destino, origen, peso);
                idArista++;
            }

            aristasImportadas++;
        }

        // Construir tanto la lista de adyacencia como la matriz de adyacencia
        grafo.crearListaAdyacencia();
        grafo.crearMatrizAdyacencia();

        // Informar el resumen de importacion y los errores encontrados
        StringBuilder resumen = new StringBuilder();
        resumen.append("Importacion CSV completada:\n");
        resumen.append("- Vertices importados: ").append(grafo.getVertices().size()).append("\n");
        resumen.append("- Conexiones importadas: ").append(aristasImportadas);

        if (!errores.isEmpty()) {
            resumen.append("\n\nAdvertencias/Errores detectados (").append(errores.size()).append("):\n");
            int limite = Math.min(errores.size(), 8);
            for (int i = 0; i < limite; i++) {
                resumen.append("* ").append(errores.get(i)).append("\n");
            }
            if (errores.size() > limite) {
                resumen.append("... y ").append(errores.size() - limite).append(" errores mas.");
            }
        }

        Labels.texto(resumen.toString());
        return grafo;
    }

    // Abre el archivo CSV desde resources o desde el sistema de archivos local
    private static BufferedReader abrirArchivo(String nombre) throws IOException {
        String nombreArchivo = nombre.endsWith(".csv") ? nombre : nombre + ".csv";

        rutaArchivo = "/com/example/proyecto/" + nombreArchivo;
        InputStream entrada = Archivo.class.getResourceAsStream(rutaArchivo);
        if (entrada != null) {
            return new BufferedReader(new InputStreamReader(entrada, StandardCharsets.UTF_8));
        }

        rutaArchivo = nombreArchivo;
        return new BufferedReader(new FileReader(rutaArchivo));
    }

    // Guarda el grafo en un archivo CSV evitando duplicar aristas en grafos no dirigidos
    public static void guardarGrafoEnArchivo(Grafo grafo, String nombre) {
        String nombreArchivo = nombre.endsWith(".csv") ? nombre : nombre + ".csv";
        guardarGrafoEnArchivo(grafo, new File(nombreArchivo));
    }

    public static void guardarGrafoEnArchivo(Grafo grafo, File archivoDestino) {
        HashSet<String> conexionesGuardadas = new HashSet<>();

        try (FileWriter fw = new FileWriter(archivoDestino)) {
            fw.write("origen,destino,peso\n");

            for (Vertice v : grafo.getVertices()) {
                for (Arista a : v.getAristas()) {
                    String origen = a.getOrigen().getDato();
                    String destino = a.getDestino().getDato();
                    String claveDirecta = origen.toLowerCase() + "->" + destino.toLowerCase();

                    if (!grafo.isDirigido()) {
                        String claveInversa = destino.toLowerCase() + "->" + origen.toLowerCase();
                        if (conexionesGuardadas.contains(claveDirecta) || conexionesGuardadas.contains(claveInversa)) {
                            continue;
                        }
                    }

                    conexionesGuardadas.add(claveDirecta);
                    fw.write(origen + "," + destino + "," + a.getPeso() + "\n");
                }
            }
            Labels.texto("Grafo guardado correctamente en: " + archivoDestino.getName());
        } catch (IOException e) {
            Labels.texto("Error al guardar el grafo en el archivo");
        }
    }
}