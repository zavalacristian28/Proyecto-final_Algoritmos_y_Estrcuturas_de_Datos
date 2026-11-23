package com.example.proyecto;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Fondo {

    //Contenido principal que dividira las zonas de la pantalla
    private BorderPane panelPrincipal;

    //Lienzo donde se dibujara los grafos
    private Pane lienzoGrafo;

    //Panel para los botones
    private VBox panelControladores;

    //Combobox para la carga de los 3 csv
    private ComboBox<String> cargaCSV;

    public Fondo(){
        panelPrincipal = new BorderPane();
        panelPrincipal.setStyle("-fx-background-color: #f4f6f9;");

        //Parte superior
        HBox barraSuperior = new HBox();
        barraSuperior.setPadding(new Insets(15));
        barraSuperior.setStyle("-fx-background-color: #2c3e50;");

        Label titulo = new Label("Sistema de Gestion de grafos");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        barraSuperior.getChildren().add(titulo);

        //parte izquierda los botones/controles
        panelControladores = new VBox(12);
        panelControladores.setPadding(new Insets(15));
        panelControladores.setPrefWidth(260);
        panelControladores.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7;");

        //Lienzo de Grafo
        lienzoGrafo = new Pane();
        lienzoGrafo.setStyle("-fx-background-color: white; -fx-border-color: #dcdde1;");

        //Acomodos de los paneles
        panelPrincipal.setTop(barraSuperior);
        panelPrincipal.setLeft(panelControladores);
        panelPrincipal.setCenter(lienzoGrafo);

        //Construccion manual del grafo
        Label lblManual = new Label("Construccion manual del grafo ");
        lblManual.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        CheckBox checkBoxDirigido = new CheckBox("Grafo Dirigido");
        TextField textFieldVertice = new TextField();
        textFieldVertice.setPromptText("nombre de la ubicacion del vertice");

        Button btnAgregarVertice = new Button("Agregar Vertice");
        btnAgregarVertice.setMaxWidth(Double.MAX_VALUE);

        TextField txtOrigen = new TextField();
        txtOrigen.setPromptText("Vertice Origen");

        TextField txtDestino = new TextField();
        txtDestino.setPromptText("Vertice Destino");

        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso (distancia)");

        Button btnAgregarArista = new Button("Conectar Ubicaciones");
        btnAgregarArista.setMaxWidth(Double.MAX_VALUE);

        //Botones generales
        Label lblOpciones = new Label("Opciones del Grafo");
        lblOpciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Button btnMostrarMatriz = new Button("Ver Matriz de Adyacencia");
        btnMostrarMatriz.setMaxWidth(Double.MAX_VALUE);

        Button btnMostrarLista = new Button("Ver Lista de Adyacencia");
        btnMostrarLista.setMaxWidth(Double.MAX_VALUE);

        Button btnLimpiarGrafo = new Button("Limpiar Lienzo");
        btnLimpiarGrafo.setMaxWidth(Double.MAX_VALUE);

        Label lblCSV = new Label("Carga de CSV");
        lblCSV.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        //Combobox para elegir entre los 3 CSV
        cargaCSV = new ComboBox<>();
        cargaCSV.getItems().addAll("CSV20.csv", "CSV100.csv", "CSV500.csv");
        cargaCSV.setPromptText("Selecciona un archivo CSV");
        cargaCSV.setMaxWidth(Double.MAX_VALUE);

        Button btnCSV = new Button("Cargar CSV");
        btnCSV.setMaxWidth(Double.MAX_VALUE);

        // Agregamos todos los elementos visuales al panel izquierdo
        panelControladores.getChildren().addAll(
                lblManual,
                checkBoxDirigido,
                textFieldVertice,
                btnAgregarVertice,
                new Separator(),
                txtOrigen,
                txtDestino,
                txtPeso,
                btnAgregarArista,
                new Separator(),
                lblOpciones,
                btnMostrarMatriz,
                btnMostrarLista,
                btnLimpiarGrafo,
                new Separator(),
                lblCSV,
                cargaCSV,
                btnCSV
        );





    }

    public BorderPane getPanel() {
        return panelPrincipal;
    }



}
