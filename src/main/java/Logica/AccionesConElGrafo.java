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

        //Se usa el campo del nombre del grafo para crear el nombre del archivo que tendra el grafo
        String nombreGrafo=Fondo.valorTxtNombreGrafo();
        int idArista=0;
        Grafo grafo=new Grafo(true);
        while(Fondo.BotonExportarPresionado()) {
            Vertice origen = new Vertice();
            Vertice destino = new Vertice();
            Arista arista = new Arista();
            //Aqui se añadira el textField del nombre del archivo
            nombreGrafo = "";
            //Aqui va a ir el textField.getValue de origen
            String datoOrigen = Fondo.valorTxtOrigen();
            String datoDestino = Fondo.valorTxtDestino();
            //Validacion por si son el mismo dato
            while (datoDestino.equals(datoOrigen)) {
                Labels.texto("El vertice de destino no puede ser igual al de destino, ingresa otro: ");
                //Aqui se obtendra el nuevo valor de destino
                datoDestino = Fondo.valorTxtDestino();
            }
            //Aqui el textFiel.getValue del peso
            String Textopeso = Fondo.valorTxtPeso();
            int peso = Integer.parseInt(Textopeso);
            //validacion para pesos negativos
            while (peso < 0) {
                Labels.texto("Peso invalido. El peso no puede ser negativo, ingresa otro: ");
                //Aqui se obtendra de nuevo del textField.getValue
                Textopeso = Fondo.valorTxtPeso();
                peso = Integer.parseInt(Textopeso);
            }

            grafo.agregarArista(idArista, datoOrigen, datoDestino, peso);

            idArista++;
        }

        Archivo.guardarGrafoEnArchivo(grafo,nombreGrafo);
        Fondo.añadirValorAlComboBox(nombreGrafo);
        Fondo.llenarComboBoxVertices(grafo);

    return grafo;
    }

    public void modificarGrafo(Grafo grafo){
        String nombreACargar=Fondo.valorComboBox();
        Grafo grafoCargado=Archivo.cargarArchivo(nombreACargar);

        String operacionAEjecutar=Fondo.valorComboBoxOpModificables();
        switch (operacionAEjecutar){
            case "Modificar peso":
                    String vOrigen=Fondo.valorTxtOrigen();
                    String vDestino=Fondo.valorTxtDestino();
                    String textoPeso=Fondo.valorTxtPeso();
                    int peso=Integer.parseInt(textoPeso);
                    if(grafoCargado.modificarPeso(vOrigen,vDestino,peso)){
                        Labels.texto("Peso modificado corrctamente");
                    }else{
                        Labels.texto("No se pudo actualizar el peso");
                    }
                break;

            case "Modificar valor del vertice":
                    String nombreVOrigen=Fondo.valorTxtOrigen();
                    String nuevoNombreVOrigen=Fondo.valorTxtDestino();
                    if(grafoCargado.modificarVertice(nombreVOrigen,nuevoNombreVOrigen)){
                        Labels.texto("Vertice modificado correctamente");
                    }else{
                        Labels.texto("No se pudo actualizar el vertice");
                    }
                break;
            case "Eliminar vertice":
                    String nombreVEliminar=Fondo.valorTxtOrigen();
                    if(grafoCargado.eliminarVertice(nombreVEliminar)){
                        Labels.texto("Vertice eliminado correctamente");
                        Fondo.llenarComboBoxVertices(grafo);
                    }else{
                        Labels.texto("No se pudo eliminar el vertice");
                    }
                break;
            case "Eliminar arista":
                    String nombreVorigen=Fondo.valorTxtOrigen();
                    String nombreVdestino=Fondo.valorTxtDestino();
                    if(grafoCargado.eliminarArista(nombreVorigen,nombreVdestino)){
                        Labels.texto("Arista eliminada correctamente");
                    }else{
                        Labels.texto("No se pudo eliminar la arista");
                    }
                break;
        }
        Archivo.guardarGrafoEnArchivo(grafoCargado,nombreACargar);
    }

    public void buscarEnAnchura(Grafo grafo){
        String nombre=Fondo.valorComboBoxVerticesGrafo();
        Vertice inicio=grafo.buscarVertice(nombre);
        while(inicio==null){
            Labels.texto("Selecciona un vertice valido");
            nombre=Fondo.valorComboBoxVerticesGrafo();
        }
        String resultadoBFS=AlgoritmosBusOrd.BFS(inicio.getId_Vertice());
        Labels.texto("Recorrido BFS: "+resultadoBFS);
    }

    public void buscarEnProfundidad(Grafo grafo){
        String nombre=Fondo.valorComboBoxVerticesGrafo();
        Vertice inicio=grafo.buscarVertice(nombre);
        while(inicio==null){
            Labels.texto("Selecciona un vertice valido");
            nombre=Fondo.valorComboBoxVerticesGrafo();
        }
        String resultado=AlgoritmosBusOrd.DFS(grafo,inicio.getId_Vertice());
        Labels.texto("Recorrido DFS: "+resultado);
    }
}
