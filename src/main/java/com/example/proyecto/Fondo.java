package com.example.proyecto;

import Logica.Archivo;
import Logica.Grafo;
import Logica.Vertice;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.File;
import java.util.Optional;

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

    // Grafo que se esta mostrando actualmente en el lienzo
    private Grafo grafoActual;
    private CheckBox checkBoxDirigido;

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

        checkBoxDirigido = new CheckBox("Grafo Dirigido");
        checkBoxDirigido.setOnAction(event -> cambiarTipoGrafo());

        // Grafo vacio con el que se empieza a trabajar
        grafoActual = new Grafo(checkBoxDirigido.isSelected());

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
            guardarGrafoCsv();
        });

        btnCargarCsv.setOnAction(event -> cargarCsvSeleccionado());
        btnLimpiarGrafo.setOnAction(event -> limpiarLienzo());
        btnAgregarVertice.setOnAction(event -> agregarVertice());
        btnAgregarArista.setOnAction(event -> conectarUbicaciones());
        btnEditarVertice.setOnAction(event -> editarVertice());
        btnEliminarVertice.setOnAction(event -> eliminarVertice());
        btnEditarArista.setOnAction(event -> editarConexion());
        btnEliminarArista.setOnAction(event -> eliminarConexion());
        btnMostrarMatriz.setOnAction(event -> verMatrizAdyacencia());
        btnMostrarLista.setOnAction(event -> verListaAdyacencia());

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

    // Carga el CSV elegido en el comboBox y dibuja el grafo en el lienzo
    private void cargarCsvSeleccionado() {
        String nombreArchivo = cargaCSV.getValue();
        if (nombreArchivo == null) {
            Labels.texto("Selecciona un archivo CSV");
            return;
        }

        grafoActual = Archivo.cargarArchivo(nombreArchivo);
        checkBoxDirigido.setSelected(grafoActual.isDirigido());
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
    }

    // Agrega al grafo el vertice escrito en el campo de texto y lo dibuja
    private void agregarVertice() {
        String nombre = textFieldVertice.getText().trim();

        if (nombre.isEmpty()) {
            Labels.texto("Escribe el nombre del vertice");
            return;
        }
        if (grafoActual.buscarVertice(nombre) != null) {
            Labels.texto("Ya existe un vertice con ese nombre");
            return;
        }

        grafoActual.obtenerOCrearVertice(nombre);
        textFieldVertice.clear();
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Vertice agregado correctamente");
    }

    // Crea una arista entre los vertices escritos en origen y destino con el peso indicado
    private void conectarUbicaciones() {
        String origen = txtOrigen.getText().trim();
        String destino = txtDestino.getText().trim();
        String textoPeso = txtPeso.getText().trim();

        if (origen.isEmpty() || destino.isEmpty()) {
            Labels.texto("Escribe el vertice origen y el vertice destino");
            return;
        }
        if (origen.equalsIgnoreCase(destino)) {
            Labels.texto("El vertice de destino no puede ser igual al de origen");
            return;
        }

        int peso = leerPeso(textoPeso);
        if (peso == -1) {
            return;
        }

        if (grafoActual.existeArista(origen, destino)) {
            Labels.texto("Ya existe una conexion entre esos vertices, usa Editar Conexion");
            return;
        }

        int idArista = siguienteIdArista();
        grafoActual.agregarArista(idArista, origen, destino, peso);
        //En un grafo no dirigido la conexion tambien existe de regreso
        if (!grafoActual.isDirigido()) {
            grafoActual.agregarArista(idArista + 1, destino, origen, peso);
        }

        limpiarCamposArista();
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Conexion agregada correctamente");
    }

    // Convierte el texto del peso a numero, si no es valido muestra el mensaje y regresa -1
    private int leerPeso(String textoPeso) {
        int peso;
        try {
            peso = Integer.parseInt(textoPeso);
        } catch (NumberFormatException e) {
            Labels.texto("El peso debe ser un numero entero");
            return -1;
        }
        //El peso 0 no se permite porque en la matriz de adyacencia el 0 significa que no hay conexion
        if (peso <= 0) {
            Labels.texto("El peso debe ser mayor a 0");
            return -1;
        }
        return peso;
    }

    private void limpiarCamposArista() {
        txtOrigen.clear();
        txtDestino.clear();
        txtPeso.clear();
    }

    // El id de la nueva arista es la cantidad de aristas que ya tiene el grafo
    private int siguienteIdArista() {
        int cantidad = 0;
        for (Vertice v : grafoActual.getVertices()) {
            cantidad += v.getAristas().size();
        }
        return cantidad;
    }

    // Cambia el nombre del vertice escrito en el campo de texto, el nombre nuevo se pide en una ventana
    private void editarVertice() {
        String nombreActual = textFieldVertice.getText().trim();

        if (nombreActual.isEmpty()) {
            Labels.texto("Escribe el nombre del vertice que quieres editar");
            return;
        }
        if (grafoActual.buscarVertice(nombreActual) == null) {
            Labels.texto("No existe un vertice con ese nombre");
            return;
        }

        TextInputDialog dialogo = new TextInputDialog(nombreActual);
        dialogo.setTitle("Editar vertice");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Nuevo nombre:");
        Optional<String> respuesta = dialogo.showAndWait();

        if (respuesta.isPresent()) {
            renombrarVertice(nombreActual, respuesta.get());
        }
    }

    private void renombrarVertice(String nombreActual, String nuevoNombre) {
        nuevoNombre = nuevoNombre.trim();

        if (nuevoNombre.isEmpty()) {
            Labels.texto("El nuevo nombre no puede estar vacio");
            return;
        }
        if (nuevoNombre.equals(nombreActual)) {
            Labels.texto("El nombre nuevo es igual al actual");
            return;
        }
        if (!grafoActual.modificarVertice(nombreActual, nuevoNombre)) {
            Labels.texto("Ya existe un vertice con ese nombre");
            return;
        }

        textFieldVertice.clear();
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Vertice editado correctamente");
    }

    // Elimina el vertice escrito en el campo de texto junto con todas sus conexiones
    private void eliminarVertice() {
        String nombre = textFieldVertice.getText().trim();

        if (nombre.isEmpty()) {
            Labels.texto("Escribe el nombre del vertice que quieres eliminar");
            return;
        }
        if (!grafoActual.eliminarVertice(nombre)) {
            Labels.texto("No existe un vertice con ese nombre");
            return;
        }

        textFieldVertice.clear();
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Vertice eliminado correctamente");
    }

    // Cambia el peso de la conexion entre origen y destino por el peso escrito
    private void editarConexion() {
        String origen = txtOrigen.getText().trim();
        String destino = txtDestino.getText().trim();

        if (origen.isEmpty() || destino.isEmpty()) {
            Labels.texto("Escribe el vertice origen y el vertice destino");
            return;
        }
        int peso = leerPeso(txtPeso.getText().trim());
        if (peso == -1) {
            return;
        }
        if (!grafoActual.existeArista(origen, destino)) {
            Labels.texto("No existe una conexion entre esos vertices");
            return;
        }

        grafoActual.modificarPeso(origen, destino, peso);
        //En un grafo no dirigido tambien se cambia la conexion de regreso
        if (!grafoActual.isDirigido()) {
            grafoActual.modificarPeso(destino, origen, peso);
        }

        limpiarCamposArista();
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Conexion editada correctamente");
    }

    // Elimina la conexion entre origen y destino (los vertices se quedan en el grafo)
    private void eliminarConexion() {
        String origen = txtOrigen.getText().trim();
        String destino = txtDestino.getText().trim();

        if (origen.isEmpty() || destino.isEmpty()) {
            Labels.texto("Escribe el vertice origen y el vertice destino");
            return;
        }
        if (!grafoActual.existeArista(origen, destino)) {
            Labels.texto("No existe una conexion entre esos vertices");
            return;
        }

        grafoActual.eliminarArista(origen, destino);
        if (!grafoActual.isDirigido()) {
            grafoActual.eliminarArista(destino, origen);
        }

        limpiarCamposArista();
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Conexion eliminada correctamente");
    }

    // Muestra la matriz de adyacencia del grafo en una ventana
    private void verMatrizAdyacencia() {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo para mostrar");
            return;
        }
        mostrarVentanaTexto("Matriz de adyacencia", grafoActual.matrizAdyacenciaComoTexto());
    }

    // Muestra la lista de adyacencia del grafo en una ventana
    private void verListaAdyacencia() {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo para mostrar");
            return;
        }
        mostrarVentanaTexto("Lista de adyacencia", grafoActual.listaAdyacenciaComoTexto());
    }

    // Ventana con un texto que se puede desplazar, sirve para matrices y listas grandes
    private void mostrarVentanaTexto(String titulo, String texto) {
        TextArea areaTexto = new TextArea(texto);
        areaTexto.setEditable(false);
        areaTexto.setStyle("-fx-font-family: monospace;");
        areaTexto.setPrefSize(650, 450);

        Alert ventana = new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle(titulo);
        ventana.setHeaderText(null);
        ventana.getDialogPane().setContent(areaTexto);
        ventana.setResizable(true);
        ventana.showAndWait();
    }
       // Guarda el grafo en un CSV con el nombre escrito en el campo nombre del grafo
       // y lo agrega al comboBox para poder cargarlo despues.
       // Nota: el CSV solo guarda las conexiones, por lo que los vertices sin conexiones no se guardan.
    private void guardarGrafoCsv() {
        String nombre = txtNombreGrafo.getText().trim();
        if (nombre.toLowerCase().endsWith(".csv")) {
            nombre = nombre.substring(0, nombre.length() - 4).trim();
        }

        if (nombre.isEmpty()) {
            Labels.texto("Escribe el nombre del grafo");
            return;
        }
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo para guardar");
            return;
        }
        // Caracteres que no se pueden usar en el nombre de un archivo
        if (nombre.matches(".*[\\/:*?\"<>|].*")) {
            Labels.texto("El nombre del grafo tiene caracteres no permitidos");
            return;
        }
        // Los CSV incluidos en el proyecto no se pueden reemplazar
        if (Fondo.class.getResource("/com/example/proyecto/" + nombre + ".csv") != null) {
            Labels.texto("Ese nombre lo usa uno de los CSV incluidos, escribe otro");
            return;
        }

        Archivo.guardarGrafoEnArchivo(grafoActual, nombre);

        if (new File(nombre + ".csv").exists() && !cargaCSV.getItems().contains(nombre)) {
            cargaCSV.getItems().add(nombre);
        }
    }

    // Borra el dibujo y empieza con un grafo vacio del tipo que indique el checkBox
    private void limpiarLienzo() {
        lienzoGrafo.getChildren().clear();
        grafoActual = new Grafo(checkBoxDirigido.isSelected());
    }
       // El tipo de grafo (dirigido o no) solo se puede cambiar cuando esta vacio,
       // si ya tiene vertices se regresa el checkBox a su valor anterior
    private void cambiarTipoGrafo() {
        if (grafoActual.getVertices().isEmpty()) {
            grafoActual = new Grafo(checkBoxDirigido.isSelected());
        } else {
            checkBoxDirigido.setSelected(grafoActual.isDirigido());
            Labels.texto("Limpia el lienzo para cambiar el tipo de grafo");
        }
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