package com.example.proyecto;

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

public class Fondo {
    private static Fondo instancia;
    TextField textFieldVertice;
    TextField txtOrigen;
    TextField txtPeso;
    TextField txtDestino;
    TextField txtNombreGrafo;
    Button btnExportarCsv;
    private static boolean botonExportarPresionado = false;

    // Contenido principal que dividira las zonas de la pantalla
    private BorderPane panelPrincipal;

    // Lienzo donde se dibujaran los grafos
    private Pane lienzoGrafo;

    // Panel izquierdo para los botones y controles
    private VBox panelControladores;

    public Fondo() {
    //Combobox para la carga de los 3 csv
    private ComboBox<String> cargaCSV;

    public Fondo(){
        instancia=this;
        panelPrincipal = new BorderPane();
        panelPrincipal.setStyle("-fx-background-color: #f4f6f9;");

        // PARTE SUPERIOR
        HBox barraSuperior = new HBox();
        barraSuperior.setPadding(new Insets(15));
        barraSuperior.setStyle("-fx-background-color: #2c3e50;");

        Label titulo = new Label("Sistema de Gestion de grafos");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        barraSuperior.getChildren().add(titulo);

        // PARTE IZQUIERDA  BOTONES Y CONTROLES
        panelControladores = new VBox(10);
        panelControladores.setPadding(new Insets(15));
        panelControladores.setPrefWidth(265);
        panelControladores.setStyle("-fx-background-color: #ecf0f1;");

        // Construccion manual y edicion del grafo
        Label lblManual = new Label("Construccion manual del grafo");
        lblManual.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        CheckBox checkBoxDirigido = new CheckBox("Grafo Dirigido");

        TextField textFieldVertice = new TextField();
        textFieldVertice.setPromptText("nombre de la ubicacion del vertice");

        txtNombreGrafo=new TextField();
        txtNombreGrafo.setPromptText("Ingresa el nombre del grafo: ");

        Button btnAgregarVertice = new Button("Agregar Vertice");
        btnAgregarVertice.setMaxWidth(Double.MAX_VALUE);
        HBox filaBotonesVertice = new HBox(6);
        Button btnAgregarVertice = new Button("Agregar");
        btnAgregarVertice.setPrefWidth(78);
        Button btnEditarVertice = new Button("Editar");
        btnEditarVertice.setPrefWidth(78);
        Button btnEliminarVertice = new Button("Eliminar");
        btnEliminarVertice.setPrefWidth(78);
        filaBotonesVertice.getChildren().addAll(btnAgregarVertice, btnEditarVertice, btnEliminarVertice);

         txtOrigen = new TextField();
        txtOrigen.setPromptText("Vertice Origen");


         txtDestino = new TextField();
        txtDestino.setPromptText("Vertice Destino");

        txtPeso = new TextField();
        txtPeso.setPromptText("Peso (distancia)");

        Button btnAgregarArista = new Button("Conectar Ubicaciones");
        btnAgregarArista.setMaxWidth(Double.MAX_VALUE);
        btnExportarCsv=new Button("Guardar en CSV");
        btnExportarCsv.setOnAction(event ->{
            botonExportarPresionado=true;
        });

        HBox filaBotonesArista = new HBox(8);
        Button btnEditarArista = new Button("Editar Conexion");
        btnEditarArista.setPrefWidth(118);
        Button btnEliminarArista = new Button("Eliminar Conexion");
        btnEliminarArista.setPrefWidth(118);
        filaBotonesArista.getChildren().addAll(btnEditarArista, btnEliminarArista);

        //Opciones del Grafo
        Label lblOpciones = new Label("Opciones del Grafo");
        lblOpciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Button btnMostrarMatriz = new Button("Ver Matriz de Adyacencia");
        btnMostrarMatriz.setMaxWidth(Double.MAX_VALUE);

        Button btnMostrarLista = new Button("Ver Lista de Adyacencia");
        btnMostrarLista.setMaxWidth(Double.MAX_VALUE);

        Button btnLimpiarGrafo = new Button("Limpiar Lienzo");
        btnLimpiarGrafo.setMaxWidth(Double.MAX_VALUE);

        // Carga y Exportacion de CSV
        Label lblCsv = new Label("Gestion de Archivos CSV");
        lblCsv.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        ComboBox<String> comboCsv = new ComboBox<>();
        comboCsv.getItems().addAll("CSV20.csv", "CSV100.csv", "CSV500.csv");
        comboCsv.setPromptText("Selecciona un archivo CSV");
        comboCsv.setMaxWidth(Double.MAX_VALUE);

        HBox filaCsv = new HBox(8);
        Button btnCargarCsv = new Button("Cargar CSV");
        btnCargarCsv.setPrefWidth(118);
        Button btnExportarCsv = new Button("Guardar CSV");
        btnExportarCsv.setPrefWidth(118);
        filaCsv.getChildren().addAll(btnCargarCsv, btnExportarCsv);

        // Agregamos los elementos visuales al panel izquierdo
        panelControladores.getChildren().addAll(
                lblManual,
                checkBoxDirigido,
                textFieldVertice,
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
                comboCsv,
                filaCsv
        );

        // Envolvemos el panel izquierdo en un ScrollPane
        ScrollPane scrollIzquierdo = new ScrollPane(panelControladores);
        scrollIzquierdo.setFitToWidth(true);
        scrollIzquierdo.setStyle("-fx-background-color: #ecf0f1;");

        // LIENZO CENTRAL DE GRAFO
        lienzoGrafo = new Pane();
        lienzoGrafo.setStyle("-fx-background-color: white; -fx-border-color: #dcdde1;");

        //ACOMODO DE LOS PANELES
        panelPrincipal.setTop(barraSuperior);
        panelPrincipal.setLeft(scrollIzquierdo);
        panelPrincipal.setCenter(lienzoGrafo);
    }

    public BorderPane getPanel() {
        return panelPrincipal;
    }

    public TextField getTxtOrigen(){
        return txtOrigen;
    }
    public TextField getTxtPeso(){
        return txtPeso;
    }
    public TextField getTxtDestino(){
        return txtDestino;
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

    public static boolean BotonExportarPresionado(){
        return botonExportarPresionado;
    }


}