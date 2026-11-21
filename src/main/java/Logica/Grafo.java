package Logica;

public class Grafo {
    private Vertice[] vertices;
    private boolean dirigido;

    public Grafo(Vertice vertices,boolean dirigido){
        this.dirigido=dirigido;
        vertices=new Vertice();
    }
}
