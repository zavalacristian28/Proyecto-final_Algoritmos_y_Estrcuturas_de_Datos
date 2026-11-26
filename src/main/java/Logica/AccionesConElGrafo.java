package Logica;

import com.example.proyecto.Fondo;
import com.example.proyecto.Labels;

import java.util.ArrayList;

public class AccionesConElGrafo {
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
        long tiempoInicial = System.nanoTime();
        switch (metodo) {
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
        long tiempoFinal = System.nanoTime();
        double tiempoMS = (tiempoFinal - tiempoInicial) / 1_000_000.0;
        return new Resultados(metodo, tiempoMS, grafo.getVertices().size(), contarAristas(grafo));
    }

    public static int contarAristas(Grafo grafo) {
        int cantidad = 0;
        for (Vertice v : grafo.getVertices()) {
            cantidad += v.getAristas().size();
        }
        return cantidad;
    }

    public static Resultados medirCargaArchivo(String nombreArchivo) {
        Labels.setModoSilencioso(true);
        long tiempoInicial = System.nanoTime();
        Grafo grafo = Archivo.cargarArchivo(nombreArchivo);
        long tiempoFinal = System.nanoTime();
        Labels.setModoSilencioso(false);

        double tiempoMS = (tiempoFinal - tiempoInicial) / 1_000_000.0;
        return new Resultados("Carga CSV: " + nombreArchivo, tiempoMS, grafo.getVertices().size(), contarAristas(grafo));
    }
}

