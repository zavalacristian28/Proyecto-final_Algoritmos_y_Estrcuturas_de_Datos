package Logica;

import com.example.proyecto.Fondo;
import com.example.proyecto.Labels;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class AccionesConElGrafo {
    private ArrayList<Grafo> grafos;

    public AccionesConElGrafo(){
        grafos=new ArrayList<>();
    }

    public ArrayList<Grafo> getGrafos(){
        return grafos;
    }

    public Grafo crearGrafo(){

        String nombreGrafo;

        while(Fondo.BotonExportarPresionado()){

        }
        Vertice origen=new Vertice();
        Vertice destino=new Vertice();
        Arista arista=new Arista();
        //Aqui se añadira el textField del nombre del archivo
        nombreGrafo="";
        //Aqui va a ir el textField.getValue de origen
        String datoOrigen= Fondo.valorTxtOrigen();
        String datoDestino=Fondo.valorTxtDestino();
        //Validacion por si son el mismo dato
        while(datoDestino.equals(datoOrigen)){
            Labels.texto("El vertice de destino no puede ser igual al de destino, ingresa otro: ");
            //Aqui se obtendra el nuevo valor de destino
            datoDestino=Fondo.valorTxtDestino();
        }
        //Aqui el textFiel.getValue del peso
        String Textopeso=Fondo.valorTxtPeso();
        int peso=Integer.parseInt(Textopeso);
        //validacion para pesos negativos
        while (peso<0){
            Labels.texto("Peso invalido. El peso no puede ser negativo, ingresa otro: ");
            //Aqui se obtendra de nuevo del textField.getValue
            Textopeso=Fondo.valorTxtPeso();
            peso=Integer.parseInt(Textopeso);
        }
        Grafo grafo=new Grafo();
    }

    public void modificarGrafo(Grafo grafo){

    }
}
