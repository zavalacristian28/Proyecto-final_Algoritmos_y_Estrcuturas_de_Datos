package Logica;

import com.example.proyecto.Fondo;
import com.example.proyecto.Labels;

import java.util.ArrayList;
import java.util.HashSet;

public class AccionesConElGrafo {
    // Numero de repeticiones usadas para promediar los tiempos del analisis empirico
    private static final int REPETICIONES = 5;

    private ArrayList<Grafo> grafos;

    public AccionesConElGrafo() {
        grafos = new ArrayList<>();
    }

    public ArrayList<Grafo> getGrafos() {
        return grafos;
    }

    public static void buscarEnAnchura(Grafo grafo) {
        if (grafo == null || grafo.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo cargado");
            return;
        }
        String nombre = Fondo.valorComboBoxVerticesGrafo();
        Vertice inicio = grafo.buscarVertice(nombre);
        if (inicio == null) {
            Labels.texto("Selecciona un vertice de origen valido");
            return;
        }
        String resultadoBFS = AlgoritmosBusOrd.BFS(grafo, inicio.getId_Vertice());
        Labels.texto("Recorrido BFS desde " + inicio.getDato() + ":\n" + resultadoBFS);
    }

    public static void buscarEnProfundidad(Grafo grafo) {
        if (grafo == null || grafo.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo cargado");
            return;
        }
        String nombre = Fondo.valorComboBoxVerticesGrafo();
        Vertice inicio = grafo.buscarVertice(nombre);
        if (inicio == null) {
            Labels.texto("Selecciona un vertice de origen valido");
            return;
        }
        String resultadoDFS = AlgoritmosBusOrd.DFS(grafo, inicio.getId_Vertice());
        Labels.texto("Recorrido DFS desde " + inicio.getDato() + ":\n" + resultadoDFS);
    }

    public static void buscarConDijkstra(Grafo grafo) {
        if (grafo == null || grafo.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo cargado");
            return;
        }
        String nombre = Fondo.valorComboBoxVerticesGrafo();
        String nombreDestino = Fondo.valorComboBoxVerticesDestino();
        Vertice inicio = grafo.buscarVertice(nombre);
        Vertice destino = grafo.buscarVertice(nombreDestino);

        if (inicio == null || destino == null) {
            Labels.texto("Selecciona un vertice de origen y uno de destino validos");
            return;
        }

        String resultado = AlgoritmosBusOrd.dijkstra(grafo, inicio.getId_Vertice(), destino.getId_Vertice());
        Labels.texto(resultado);
    }

    // Obtiene las conexiones del grafo sin duplicar las aristas inversas si es no dirigido
    public static Arista[] obtenerAristasUnicas(Grafo grafo) {
        ArrayList<Arista> lista = new ArrayList<>();
        HashSet<String> registradas = new HashSet<>();

        for (Vertice v : grafo.getVertices()) {
            for (Arista a : v.getAristas()) {
                String origen = a.getOrigen().getDato().toLowerCase();
                String destino = a.getDestino().getDato().toLowerCase();
                String clave = origen + "->" + destino;

                if (!grafo.isDirigido()) {
                    String claveInversa = destino + "->" + origen;
                    if (registradas.contains(clave) || registradas.contains(claveInversa)) {
                        continue;
                    }
                }
                registradas.add(clave);
                lista.add(a);
            }
        }
        return lista.toArray(new Arista[0]);
    }

    // Ordena las conexiones del grafo con QuickSort (porPeso = true por peso, false por nombre)
    public static String ordenarConexionesQuickSort(Grafo grafo, boolean porPeso) {
        if (grafo == null || grafo.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo cargado");
            return "";
        }

        Arista[] aristas = obtenerAristasUnicas(grafo);
        if (aristas.length == 0) {
            Labels.texto("El grafo no tiene conexiones para ordenar");
            return "";
        }

        if (aristas.length > 1) {
            AlgoritmosBusOrd.quickSortAristas(aristas, 0, aristas.length - 1, porPeso);
        }

        String criterio = porPeso ? "peso" : "nombre de vertices";
        String separador = grafo.isDirigido() ? " -> " : " - ";
        StringBuilder resultado = new StringBuilder();
        resultado.append("Conexiones ordenadas con QuickSort (por ").append(criterio).append("):\n");

        for (Arista a : aristas) {
            resultado.append(a.getOrigen().getDato())
                    .append(separador)
                    .append(a.getDestino().getDato())
                    .append(" | Peso: ")
                    .append(a.getPeso())
                    .append("\n");
        }

        String textoFinal = resultado.toString();
        Labels.texto(textoFinal);
        return textoFinal;
    }

    // Ordena las conexiones del grafo con Shell Sort (porPeso = true por peso, false por nombre)
    public static String ordenarConexionesShell(Grafo grafo, boolean porPeso) {
        if (grafo == null || grafo.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo cargado");
            return "";
        }

        Arista[] aristas = obtenerAristasUnicas(grafo);
        if (aristas.length == 0) {
            Labels.texto("El grafo no tiene conexiones para ordenar");
            return "";
        }

        if (aristas.length > 1) {
            AlgoritmosBusOrd.shellAristas(aristas, aristas.length, porPeso);
        }

        String criterio = porPeso ? "peso" : "nombre de vertices";
        String separador = grafo.isDirigido() ? " -> " : " - ";
        StringBuilder resultado = new StringBuilder();
        resultado.append("Conexiones ordenadas con Shell Sort (por ").append(criterio).append("):\n");

        for (Arista a : aristas) {
            resultado.append(a.getOrigen().getDato())
                    .append(separador)
                    .append(a.getDestino().getDato())
                    .append(" | Peso: ")
                    .append(a.getPeso())
                    .append("\n");
        }

        String textoFinal = resultado.toString();
        Labels.texto(textoFinal);
        return textoFinal;
    }

    // Ordena los nombres de los vertices con QuickSort
    public static String ordenarQuicksort(Grafo grafo) {
        int n = grafo.getVertices().size();
        String[] vertices = new String[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = grafo.getVertices().get(i).getDato();
        }
        if (n > 1) {
            AlgoritmosBusOrd.QuickSort(vertices, 0, n - 1);
        }
        StringBuilder resultado = new StringBuilder();
        resultado.append("Vertices ordenados con QuickSort:\n");
        for (String v : vertices) {
            resultado.append(v).append("\n");
        }
        return resultado.toString();
    }

    // Ordena los nombres de los vertices con Shell Sort
    public static String ordenarShell(Grafo grafo) {
        int n = grafo.getVertices().size();
        String[] vertices = new String[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = grafo.getVertices().get(i).getDato();
        }
        if (n > 1) {
            AlgoritmosBusOrd.shell(vertices, n);
        }
        StringBuilder resultado = new StringBuilder();
        resultado.append("Vertices ordenados con Shell Sort:\n");
        for (String v : vertices) {
            resultado.append(v).append("\n");
        }
        return resultado.toString();
    }

    public static Resultados medirTiempo(String metodo, Grafo grafo, int inicio, int destino) {
        Labels.setModoSilencioso(true);
        long total = 0;
        for (int r = 0; r < REPETICIONES; r++) {
            long tiempoInicial = System.nanoTime();
            ejecutarMetodo(metodo, grafo, inicio, destino);
            total += System.nanoTime() - tiempoInicial;
        }
        Labels.setModoSilencioso(false);

        double tiempoMS = (total / (double) REPETICIONES) / 1_000_000.0;
        return new Resultados(metodo, tiempoMS, grafo.getVertices().size(), contarAristas(grafo));
    }

    private static void ejecutarMetodo(String metodo, Grafo grafo, int inicio, int destino) {
        switch (metodo) {
            case "Quicksort Peso":
                ordenarConexionesQuickSort(grafo, true);
                break;
            case "Quicksort Nombre":
                ordenarConexionesQuickSort(grafo, false);
                break;
            case "Shell Peso":
                ordenarConexionesShell(grafo, true);
                break;
            case "Shell Nombre":
                ordenarConexionesShell(grafo, false);
                break;
            case "Quicksort":
                ordenarQuicksort(grafo);
                break;
            case "Shell":
                ordenarShell(grafo);
                break;
            case "Dijkstra":
                AlgoritmosBusOrd.dijkstra(grafo, inicio, destino);
                break;
            case "BFS":
                AlgoritmosBusOrd.BFS(grafo, inicio);
                break;
            case "DFS":
                AlgoritmosBusOrd.DFS(grafo, inicio);
                break;
        }
    }

    public static int contarAristas(Grafo grafo) {
        return obtenerAristasUnicas(grafo).length;
    }

    public static Resultados medirCargaArchivo(String nombreArchivo) {
        Labels.setModoSilencioso(true);
        long total = 0;
        Grafo grafo = null;
        for (int r = 0; r < REPETICIONES; r++) {
            long tiempoInicial = System.nanoTime();
            grafo = Archivo.cargarArchivo(nombreArchivo);
            total += System.nanoTime() - tiempoInicial;
        }
        Labels.setModoSilencioso(false);

        double tiempoMS = (total / (double) REPETICIONES) / 1_000_000.0;
        return new Resultados("Carga CSV: " + nombreArchivo, tiempoMS, grafo.getVertices().size(), contarAristas(grafo));
    }
}
