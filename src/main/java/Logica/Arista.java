package Logica;

public class Arista {
    private int id_Arista;
    private Vertice origen;
    private Vertice destino;
    private int peso;
    private boolean dirigido;

    public Arista(){

    }
    public Arista(int id_Arista,Vertice origen,Vertice destino,int peso,boolean dirigido){
        this.id_Arista=id_Arista;
        this.origen=origen;
        this.destino=destino;
        this.peso=peso;
        this.dirigido=dirigido;
    }
}
