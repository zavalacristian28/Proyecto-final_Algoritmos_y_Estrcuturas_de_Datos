package Logica;

import java.util.ArrayList;

public class Grafo {
    private ArrayList<Vertice> vertices;
    private boolean dirigido;
    private double[][] matrizAdyacencia;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.dirigido = false;
        matrizAdyacencia=new double[vertices.size()][vertices.size()];
    }

    public Grafo(boolean dirigido) {
        this.vertices = new ArrayList<>();
        this.dirigido = dirigido;
        matrizAdyacencia=new double[vertices.size()][vertices.size()];
    }
    public ArrayList<Vertice> getVertices(){
        return vertices;
    }

    public Vertice buscarVertice(String dato) {
        for (Vertice v : vertices) {
            if (v.getDato().equalsIgnoreCase(dato)) {
                return v;
            }
        }
        return null;
    }
    public Vertice obtenerOCrearVertice(String dato) {

        Vertice encontrado = buscarVertice(dato);

        if (encontrado != null) {
            return encontrado;
        }

        Vertice nuevo = new Vertice(vertices.size(), dato);

        vertices.add(nuevo);

        return nuevo;
    }
    public void agregarArista(int id, String origen, String destino, int peso) {
        Vertice vOrigen = obtenerOCrearVertice(origen);
        Vertice vDestino = obtenerOCrearVertice(destino);
        Arista nueva = new Arista(id, vOrigen, vDestino, peso);
        vOrigen.getAristas().add(nueva);
    }

    public void crearMatrizAdyacencia() {
        int n = vertices.size();
        matrizAdyacencia = new double[n][n];

        for (Vertice v : vertices) {
            int renglon = v.getId_Vertice();
            for (Arista a : v.getAristas()) {
                int columna = a.getDestino().getId_Vertice();
                matrizAdyacencia[renglon][columna] = a.getPeso();
            }
        }
    }

    public void mostrarMatrizAdyacencia() {

        System.out.print("\t");
        for (Vertice v : vertices) {
            System.out.print(v.getId_Vertice() + "\t");
        }

        System.out.println();
        for (int i = 0; i < matrizAdyacencia.length; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < matrizAdyacencia[i].length; j++) {
                System.out.print(matrizAdyacencia[i][j] + "\t");
            }

            System.out.println();
        }
    }

    public void crearListaAdyacencia() {

    }

    public void mostrarListaAdyacencia() {

        for (Vertice v : vertices) {

            System.out.print(v.getDato() + " -> ");

            for (Arista a : v.getAristas()) {

                System.out.print(
                        a.getDestino().getDato() +
                                "(" + a.getPeso() + ") "
                );
            }

            System.out.println();
        }
    }
}