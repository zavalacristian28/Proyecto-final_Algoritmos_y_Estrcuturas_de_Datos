package Logica;

import com.example.proyecto.Labels;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Archivo {
    private static String rutaArchivo;

    public static Grafo cargarArchivo(String nombre){
        Grafo grafo=new Grafo(true);
        int idArista=0;
        int numVertices=0;
        int numAristas=0;
        try(BufferedReader bf=abrirArchivo(nombre)){
            String linea;
            bf.readLine();
            while((linea=bf.readLine())!=null){
                if(linea.trim().isEmpty()){
                    continue;
                }
                String datosArchivo[] = linea.split(",");

                //Validacion que el archivo tenga las columnas requeridas
                //3 columnas: origen, destino y peso
                if(datosArchivo.length != 3){
                    System.out.println("Error en la línea: " + linea);
                    continue;
                }

                String origen=datosArchivo[0].trim();
                String destino=datosArchivo[1].trim();
                String pesoTexto=datosArchivo[2].trim();

                //validacion de vertices vacios
                if(origen.isEmpty() || destino.isEmpty()){
                    continue;
                }

                //validacion para evitar conexion con vertices vacios
                if(datosArchivo[0].equals(datosArchivo[1])){
                    System.out.println("vertice vacio");
                    continue;
                }
                int peso;
                try{
                    peso=Integer.parseInt(datosArchivo[2]);
                }catch (NumberFormatException e){
                    continue;
                }

                grafo.agregarArista(idArista,origen,destino,peso);
                idArista++;
                /*
                    Queda pendiente contar el numero de vertices cargadso y de aristas y añadirlos
                    al Labels.texto como label informativo
                 */
            }

        }catch (IOException e){
            System.out.println("Error al cargar");
        }
        return grafo;
    }

    // Abre el archivo CSV a leer.
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

    public static void guardarGrafoEnArchivo(Grafo grafo,String nombre){
        String nombreArchivo=nombre+".csv";

        try(FileWriter fw=new FileWriter(nombreArchivo)){

            fw.write("origen,destino,peso\n");
            //Recorremos todos los vertices del grafo
            for (Vertice v : grafo.getVertices()) {
                //Recorremos todas las aristas de cada vertice
                for (Arista a : v.getAristas()) {

                    fw.write(a.getOrigen().getDato() + "," +
                            a.getDestino().getDato() + "," +
                            a.getPeso() + "\n"
                    );
                }
            }
            Labels.texto("Grafo guardado correctamente");

        }catch (IOException e){
            Labels.texto("Error al guardar el grafo en el archivo");
        }
    }
}