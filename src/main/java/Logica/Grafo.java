package Logica;

import java.util.ArrayList;

public class Grafo {
    private ArrayList<Vertice> vertices;
    private boolean dirigido;
    private double[][] matrizAdyacencia;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.dirigido = false;
    }

    public Grafo(boolean dirigido) {
        this.vertices = new ArrayList<>();
        this.dirigido = dirigido;
    }
    public ArrayList<Vertice> getVertices(){
        return vertices;
    }

    public void crearMatrizAdyacencia() {

    }

    public void mostrarMatrizAdyacencia() {

    }

    public void crearListaAdyacencia() {

    }

    public void mostrarListaAdyacencia() {

    }
}