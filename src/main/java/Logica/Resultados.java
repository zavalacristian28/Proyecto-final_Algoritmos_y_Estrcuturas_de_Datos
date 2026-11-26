package Logica;

import java.util.List;

public class Resultados {
    private String metodo;
    private double tiempoMs;
    private int cantidadVertices;
    private int cantidadAristas;

    public Resultados(String metodo, double tiempoMs, int cantidadVertices, int cantidadAristas) {
        this.metodo = metodo;
        this.tiempoMs = tiempoMs;
        this.cantidadVertices = cantidadVertices;
        this.cantidadAristas = cantidadAristas;
    }

    public String getMetodo() {
        return metodo;
    }

    public double getTiempoMs() {
        return tiempoMs;
    }

    public int getCantidadVertices() {
        return cantidadVertices;
    }

    public int getCantidadAristas() {
        return cantidadAristas;
    }
}
