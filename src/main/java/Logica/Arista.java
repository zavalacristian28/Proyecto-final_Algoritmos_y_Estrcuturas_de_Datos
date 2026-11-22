package Logica;

public class Arista {

    private int id_Arista;
    private Vertice origen;
    private Vertice destino;
    private int peso;

    public Arista() {

    }

    public Arista(int id_Arista, Vertice origen,
                  Vertice destino, int peso) {

        this.id_Arista = id_Arista;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getDestino() {
        return destino;
    }

    public Vertice getOrigen() {
        return origen;
    }

    public int getPeso() {
        return peso;
    }
}