package Logica;

public class Vertice {
    private int id_Vertice;
    private String dato;
    private Arista[] aristas;
    private int grado_Vertice;

    public Vertice(){

    }
    public Vertice(int id_Vertice,String dato,Arista aristas,int grado_Vertice){
        this.id_Vertice=id_Vertice;
        this.dato=dato;
        aristas= new Arista();
        this.grado_Vertice=grado_Vertice;
    }
}
