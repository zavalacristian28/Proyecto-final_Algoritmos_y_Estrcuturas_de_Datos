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

    //Metodo añadido para poder actualizar el peso cuando se modifica un grafo
    public boolean modificarPeso(String origen, String destino, int nuevoPeso) {
        if (nuevoPeso < 0) {
            return false;
        }
        Vertice vOrigen = buscarVertice(origen);
        if (vOrigen == null) {
            return false;
        }
        for (Arista a : vOrigen.getAristas()) {
            if (a.getDestino().getDato().equalsIgnoreCase(destino)) {
                a.setPeso(nuevoPeso);
                return true;
            }
        }
        return false;
    }

    //Metodo para poder actualizar el nombre o texto de un vertice
    public boolean modificarVertice(String nombreActual, String nuevoNombre) {
        Vertice vertice = buscarVertice(nombreActual);
        if (vertice == null || nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return false;
        }

        // Evitamos nombres duplicados
        if (buscarVertice(nuevoNombre) != null) {
            return false;
        }
        vertice.setDato(nuevoNombre.trim());
        return true;
    }

    //Metodo para eliminar un vertice, donde se eliminar las aristas relacionadas con el
    public boolean eliminarVertice(String nombre) {
        Vertice eliminado = buscarVertice(nombre);
        if (eliminado == null) {
            return false;
        }

        // Eliminar las aristas que llegan al vertice
        for (Vertice v : vertices) {
            v.getAristas().removeIf(a -> a.getDestino() == eliminado);
        }

        vertices.remove(eliminado);

        //Con este ciclo, se re actualizan los id de los vertices contemplando el eliminado
        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).setId_Vertice(i);
        }
        //Finalmente se actualiza la matriz de adyacencia
        crearMatrizAdyacencia();

        return true;
    }

    public boolean eliminarArista(String origen, String destino) {
        Vertice vOrigen = buscarVertice(origen);
        if (vOrigen == null) {
            return false;
        }
        // Se busca la arista por medio de una lambda donde se compara el valor del vertice destino de la arista
        boolean eliminada = vOrigen.getAristas().removeIf(
                a -> a.getDestino().getDato().equalsIgnoreCase(destino));
        if (eliminada) {
            crearMatrizAdyacencia();
        }
        return eliminada;
    }

    public double[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }
}