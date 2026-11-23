package Logica;


import java.util.ArrayList;

public class Vertice {
    private int id_Vertice;
    private String dato;
    private ArrayList<Arista> aristas;
    private int grado_Vertice;

    public Vertice() {
        this.aristas = new ArrayList<>();
        this.grado_Vertice = 0;
    }

    public Vertice(int id_Vertice, String dato) {
        this.id_Vertice = id_Vertice;
        this.dato = dato;
        this.aristas = new ArrayList<>();
        this.grado_Vertice = 0;
    }

    public int getId_Vertice(){
        return id_Vertice;
    }

    public ArrayList<Arista> getAristas(){
        return aristas;
    }
    public String getDato(){
        return dato;
    }
    public int getGrado_Vertice(){
        return grado_Vertice;
    }
    public void setDato(String dato){
        this.dato=dato;
    }
}