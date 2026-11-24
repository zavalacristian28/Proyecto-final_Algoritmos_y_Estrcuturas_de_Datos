
package com.example.proyecto;

import Logica.Grafo;
import Logica.Vertice;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.*;

public class Fondo {

    private static Fondo instancia;

    private TextField textFieldVertice;
    private TextField txtOrigen;
    private TextField txtPeso;
    private TextField txtDestino;
    private TextField txtNombreGrafo;
    private Button btnExportarCsv;
    private static boolean botonExportarPresionado = false;

    private BorderPane panelPrincipal;
    private Pane lienzoGrafo;
    private VBox panelControladores;

    // ComboBox
    private static ComboBox<String> cargaCSV;
    private static ComboBox<String> operacionesModificables;
    private static ComboBox<String> verticesDelGrafo;
    private static ComboBox<String> verticesDestino;

    public Fondo() {

        instancia = this;

        panelPrincipal = new BorderPane();
        panelPrincipal.setStyle("-fx-background-color: #f4f6f9;");

        // PARTE SUPERIOR
        HBox barraSuperior = new HBox();
        barraSuperior.setPadding(new Insets(15));
        barraSuperior.setStyle("-fx-background-color: #2c3e50;");

        Label titulo = new Label("Sistema de Gestion de grafos");
        titulo.setStyle(
                "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold;"
        );

        barraSuperior.getChildren().add(titulo);

        panelControladores = new VBox(10);
        panelControladores.setPadding(new Insets(15));
        panelControladores.setPrefWidth(265);
        panelControladores.setStyle("-fx-background-color: #ecf0f1;");

        Label lblManual = new Label("Construccion manual del grafo");
        lblManual.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        CheckBox checkBoxDirigido = new CheckBox("Grafo Dirigido");

        textFieldVertice = new TextField();
        textFieldVertice.setPromptText("Nombre de la ubicacion del vertice");

        Button btnAgregarVertice = new Button("Agregar");
        Button btnEditarVertice = new Button("Editar");
        Button btnEliminarVertice = new Button("Eliminar");

        btnAgregarVertice.setPrefWidth(78);
        btnEditarVertice.setPrefWidth(78);
        btnEliminarVertice.setPrefWidth(78);

        HBox filaBotonesVertice = new HBox(6);
        filaBotonesVertice.getChildren().addAll(
                btnAgregarVertice,
                btnEditarVertice,
                btnEliminarVertice
        );

        txtNombreGrafo = new TextField();
        txtNombreGrafo.setPromptText("Ingresa el nombre del grafo");

        // ARISTAS
        txtOrigen = new TextField();
        txtOrigen.setPromptText("Vertice Origen");

        txtDestino = new TextField();
        txtDestino.setPromptText("Vertice Destino");

        txtPeso = new TextField();
        txtPeso.setPromptText("Peso (distancia)");

        Button btnAgregarArista = new Button("Conectar Ubicaciones");
        btnAgregarArista.setMaxWidth(Double.MAX_VALUE);

        Button btnEditarArista = new Button("Editar Conexion");
        Button btnEliminarArista = new Button("Eliminar Conexion");

        btnEditarArista.setPrefWidth(118);
        btnEliminarArista.setPrefWidth(118);

        HBox filaBotonesArista = new HBox(8);
        filaBotonesArista.getChildren().addAll(
                btnEditarArista,
                btnEliminarArista
        );

        Label lblOpciones = new Label("Opciones del Grafo");
        lblOpciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Button btnMostrarMatriz = new Button("Ver Matriz de Adyacencia");
        btnMostrarMatriz.setMaxWidth(Double.MAX_VALUE);

        Button btnMostrarLista = new Button("Ver Lista de Adyacencia");
        btnMostrarLista.setMaxWidth(Double.MAX_VALUE);

        Button btnLimpiarGrafo = new Button("Limpiar Lienzo");
        btnLimpiarGrafo.setMaxWidth(Double.MAX_VALUE);

        Label lblCsv = new Label("Gestion de Archivos CSV");
        lblCsv.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        /*
            Declaracion de los comboBox utilizados en la interfaz
         */
        cargaCSV = new ComboBox<>();
        cargaCSV.getItems().addAll(
                "CSV20.csv",
                "CSV100.csv",
                "CSV500.csv"
        );
        operacionesModificables= new ComboBox<>();
        operacionesModificables.getItems().addAll(
                "Modificar peso", "Modificar valor del vertice","Eliminar vertice","Eliminar arista"
        );

        verticesDelGrafo=new ComboBox<>();
        verticesDestino=new ComboBox<>();

        cargaCSV.setPromptText("Selecciona un archivo CSV");
        cargaCSV.setMaxWidth(Double.MAX_VALUE);

        Button btnCargarCsv = new Button("Cargar CSV");
        btnExportarCsv = new Button("Guardar CSV");

        btnCargarCsv.setPrefWidth(118);
        btnExportarCsv.setPrefWidth(118);

        btnExportarCsv.setOnAction(event -> {
            botonExportarPresionado = true;
        });

        HBox filaCsv = new HBox(8);
        filaCsv.getChildren().addAll(
                btnCargarCsv,
                btnExportarCsv
        );


        panelControladores.getChildren().addAll(
                lblManual,
                checkBoxDirigido,
                textFieldVertice,
                txtNombreGrafo,
                filaBotonesVertice,
                new Separator(),
                txtOrigen,
                txtDestino,
                txtPeso,
                btnAgregarArista,
                filaBotonesArista,
                new Separator(),
                lblOpciones,
                btnMostrarMatriz,
                btnMostrarLista,
                btnLimpiarGrafo,
                new Separator(),
                lblCsv,
                cargaCSV,
                filaCsv
        );

        ScrollPane scrollIzquierdo = new ScrollPane(panelControladores);
        scrollIzquierdo.setFitToWidth(true);

        lienzoGrafo = new Pane();
        lienzoGrafo.setStyle(
                "-fx-background-color: white; -fx-border-color: #dcdde1;"
        );

        // ACOMODO
        panelPrincipal.setTop(barraSuperior);
        panelPrincipal.setLeft(scrollIzquierdo);
        panelPrincipal.setCenter(lienzoGrafo);
    }

    public BorderPane getPanel() {
        return panelPrincipal;
    }

    public static String valorTxtOrigen() {
        return instancia.txtOrigen.getText();
    }

    public static String valorTxtDestino() {
        return instancia.txtDestino.getText();
    }

    public static String valorTxtPeso() {
        return instancia.txtPeso.getText();
    }

    public static String valorTxtVertice() {
        return instancia.textFieldVertice.getText();
    }

    public static String valorTxtNombreGrafo() {
        return instancia.txtNombreGrafo.getText();
    }

    public static boolean BotonExportarPresionado() {
        return botonExportarPresionado;
    }

    public static void ReiniciarBotonExportar() {
        botonExportarPresionado = false;
    }
    public static void añadirValorAlComboBox(String nombre){
        cargaCSV.getItems().add(nombre);
    }
    public static String valorComboBox(){
        return instancia.cargaCSV.getValue();
    }
    public static String valorComboBoxOpModificables(){
        return instancia.operacionesModificables.getValue();
    }
    public static String valorComboBoxVerticesGrafo(){
        return instancia.verticesDelGrafo.getValue();
    }
    public static String valorComboBoxVerticesDestino(){
        return instancia.verticesDestino.getValue();
    }

    public static void llenarComboBoxVertices(Grafo grafo) {
        instancia.verticesDelGrafo.getItems().clear();
        for (Vertice v : grafo.getVertices()) {
            verticesDelGrafo.getItems().add(v.getDato());
            verticesDestino.getItems().add(v.getDato());
        }
    }

}
