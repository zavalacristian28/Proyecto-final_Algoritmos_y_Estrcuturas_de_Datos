package com.example.proyecto;

import Logica.AccionesConElGrafo;
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
import javafx.stage.FileChooser;

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
    private TextArea areaResultados;

    // Grafo que se muestra actualmente en el lienzo
    private Grafo grafoActual;
    private CheckBox checkBoxDirigido;

    // ComboBox de la interfaz
    private static ComboBox<String> cargaCSV;
    private static ComboBox<String> operacionesModificables;
    private static ComboBox<String> verticesDelGrafo;
    private static ComboBox<String> verticesDestino;
    private ComboBox<String> criterioOrdenamiento;

    public Fondo() {
        instancia = this;

        panelPrincipal = new BorderPane();
        panelPrincipal.setStyle("-fx-background-color: #f4f6f9;");

        // Barra superior
        HBox barraSuperior = new HBox();
        barraSuperior.setPadding(new Insets(12, 15, 12, 15));
        barraSuperior.setStyle("-fx-background-color: #2c3e50;");

        Label titulo = new Label("Sistema de Gestion y Analisis de Grafos");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        barraSuperior.getChildren().add(titulo);

        // Panel izquierdo de controles
        panelControladores = new VBox(8);
        panelControladores.setPadding(new Insets(12));
        panelControladores.setPrefWidth(280);
        panelControladores.setStyle("-fx-background-color: #ecf0f1;");

        // Seccion 1: Construccion manual del grafo
        Label lblManual = new Label("1. Construccion del Grafo");
        lblManual.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        checkBoxDirigido = new CheckBox("Grafo Dirigido");
        checkBoxDirigido.setOnAction(event -> cambiarTipoGrafo());

        grafoActual = new Grafo(checkBoxDirigido.isSelected());

        textFieldVertice = new TextField();
        textFieldVertice.setPromptText("Nombre del vertice");

        Button btnAgregarVertice = new Button("Agregar");
        Button btnEditarVertice = new Button("Editar");
        Button btnEliminarVertice = new Button("Eliminar");
        btnAgregarVertice.setPrefWidth(80);
        btnEditarVertice.setPrefWidth(80);
        btnEliminarVertice.setPrefWidth(80);

        HBox filaBotonesVertice = new HBox(6);
        filaBotonesVertice.getChildren().addAll(btnAgregarVertice, btnEditarVertice, btnEliminarVertice);

        txtOrigen = new TextField();
        txtOrigen.setPromptText("Vertice Origen");

        txtDestino = new TextField();
        txtDestino.setPromptText("Vertice Destino");

        txtPeso = new TextField();
        txtPeso.setPromptText("Peso (entero > 0)");

        Button btnAgregarArista = new Button("Conectar Ubicaciones");
        btnAgregarArista.setMaxWidth(Double.MAX_VALUE);

        Button btnEditarArista = new Button("Editar Peso");
        Button btnEliminarArista = new Button("Eliminar Conexion");
        btnEditarArista.setPrefWidth(122);
        btnEliminarArista.setPrefWidth(122);

        HBox filaBotonesArista = new HBox(6);
        filaBotonesArista.getChildren().addAll(btnEditarArista, btnEliminarArista);

        // Seccion 2: Algoritmos de busqueda y ruta optima
        Label lblAlgoritmos = new Label("2. Busqueda y Ruta Optima");
        lblAlgoritmos.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        verticesDelGrafo = new ComboBox<>();
        verticesDelGrafo.setPromptText("Vertice inicial / origen");
        verticesDelGrafo.setMaxWidth(Double.MAX_VALUE);

        verticesDestino = new ComboBox<>();
        verticesDestino.setPromptText("Vertice destino (Dijkstra)");
        verticesDestino.setMaxWidth(Double.MAX_VALUE);

        Button btnBFS = new Button("BFS (Anchura)");
        Button btnDFS = new Button("DFS (Profundidad)");
        btnBFS.setPrefWidth(122);
        btnDFS.setPrefWidth(122);

        HBox filaBusquedas = new HBox(6);
        filaBusquedas.getChildren().addAll(btnBFS, btnDFS);

        Button btnDijkstra = new Button("Ruta Optima (Dijkstra)");
        btnDijkstra.setMaxWidth(Double.MAX_VALUE);

        // Seccion 3: Algoritmos de ordenamiento
        Label lblOrdenamiento = new Label("3. Ordenar Conexiones");
        lblOrdenamiento.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        criterioOrdenamiento = new ComboBox<>();
        criterioOrdenamiento.getItems().addAll("Peso de las aristas", "Nombre de los vertices");
        criterioOrdenamiento.setValue("Peso de las aristas");
        criterioOrdenamiento.setMaxWidth(Double.MAX_VALUE);

        Button btnQuickSort = new Button("QuickSort");
        Button btnShellSort = new Button("Shell Sort");
        btnQuickSort.setPrefWidth(122);
        btnShellSort.setPrefWidth(122);

        HBox filaOrdenamiento = new HBox(6);
        filaOrdenamiento.getChildren().addAll(btnQuickSort, btnShellSort);

        // Seccion 4: Estructuras y analisis
        Label lblOpciones = new Label("4. Estructuras y Analisis");
        lblOpciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Button btnMostrarMatriz = new Button("Matriz Adyacencia");
        Button btnMostrarLista = new Button("Lista Adyacencia");
        btnMostrarMatriz.setPrefWidth(122);
        btnMostrarLista.setPrefWidth(122);

        HBox filaEstructuras = new HBox(6);
        filaEstructuras.getChildren().addAll(btnMostrarMatriz, btnMostrarLista);

        Button btnAnalisisEmpirico = new Button("Analisis Empirico y Graficas");
        btnAnalisisEmpirico.setMaxWidth(Double.MAX_VALUE);
        btnAnalisisEmpirico.setStyle("-fx-font-weight: bold;");

        Button btnLimpiarGrafo = new Button("Limpiar Lienzo");
        btnLimpiarGrafo.setMaxWidth(Double.MAX_VALUE);

        // Seccion 5: Gestion de archivos CSV
        Label lblCsv = new Label("5. Importar / Exportar CSV");
        lblCsv.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        cargaCSV = new ComboBox<>();
        cargaCSV.getItems().addAll(
                "CSV20.csv",
                "CSV100.csv",
                "CSV500.csv",
                "CSV500_Denso.csv",
                "CSV1000.csv"
        );
        cargaCSV.setPromptText("Selecciona un CSV de prueba");
        cargaCSV.setMaxWidth(Double.MAX_VALUE);

        operacionesModificables = new ComboBox<>();
        operacionesModificables.getItems().addAll(
                "Modificar peso", "Modificar valor del vertice", "Eliminar vertice", "Eliminar arista"
        );

        Button btnCargarCsv = new Button("Cargar Lista");
        Button btnExaminarCsv = new Button("Examinar CSV...");
        btnCargarCsv.setPrefWidth(122);
        btnExaminarCsv.setPrefWidth(122);

        HBox filaCargaCsv = new HBox(6);
        filaCargaCsv.getChildren().addAll(btnCargarCsv, btnExaminarCsv);

        txtNombreGrafo = new TextField();
        txtNombreGrafo.setPromptText("Nombre del archivo a guardar");

        btnExportarCsv = new Button("Guardar Grafo en CSV");
        btnExportarCsv.setMaxWidth(Double.MAX_VALUE);

        // Asignacion de eventos
        btnAgregarVertice.setOnAction(event -> agregarVertice());
        btnEditarVertice.setOnAction(event -> editarVertice());
        btnEliminarVertice.setOnAction(event -> eliminarVertice());
        btnAgregarArista.setOnAction(event -> conectarUbicaciones());
        btnEditarArista.setOnAction(event -> editarConexion());
        btnEliminarArista.setOnAction(event -> eliminarConexion());

        btnBFS.setOnAction(event -> AccionesConElGrafo.buscarEnAnchura(grafoActual));
        btnDFS.setOnAction(event -> AccionesConElGrafo.buscarEnProfundidad(grafoActual));
        btnDijkstra.setOnAction(event -> AccionesConElGrafo.buscarConDijkstra(grafoActual));

        btnQuickSort.setOnAction(event -> ejecutarOrdenamiento(true));
        btnShellSort.setOnAction(event -> ejecutarOrdenamiento(false));

        btnMostrarMatriz.setOnAction(event -> verMatrizAdyacencia());
        btnMostrarLista.setOnAction(event -> verListaAdyacencia());
        btnAnalisisEmpirico.setOnAction(event -> VentanaAnalisis.mostrar());
        btnLimpiarGrafo.setOnAction(event -> limpiarLienzo());

        btnCargarCsv.setOnAction(event -> cargarCsvSeleccionado());
        btnExaminarCsv.setOnAction(event -> examinarCsvExterno());
        btnExportarCsv.setOnAction(event -> {
            botonExportarPresionado = true;
            guardarGrafoCsv();
        });

        panelControladores.getChildren().addAll(
                lblManual,
                checkBoxDirigido,
                textFieldVertice,
                filaBotonesVertice,
                txtOrigen,
                txtDestino,
                txtPeso,
                btnAgregarArista,
                filaBotonesArista,
                new Separator(),
                lblAlgoritmos,
                verticesDelGrafo,
                verticesDestino,
                filaBusquedas,
                btnDijkstra,
                new Separator(),
                lblOrdenamiento,
                criterioOrdenamiento,
                filaOrdenamiento,
                new Separator(),
                lblOpciones,
                filaEstructuras,
                btnAnalisisEmpirico,
                btnLimpiarGrafo,
                new Separator(),
                lblCsv,
                cargaCSV,
                filaCargaCsv,
                txtNombreGrafo,
                btnExportarCsv
        );

        ScrollPane scrollIzquierdo = new ScrollPane(panelControladores);
        scrollIzquierdo.setFitToWidth(true);

        // Lienzo central
        lienzoGrafo = new Pane();
        lienzoGrafo.setStyle("-fx-background-color: white; -fx-border-color: #dcdde1;");

        // Area inferior de resultados y mensajes del sistema
        VBox panelInferior = new VBox(4);
        panelInferior.setPadding(new Insets(8, 12, 10, 12));
        panelInferior.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #dcdde1;");

        Label lblSalida = new Label("Resultados y Mensajes del Sistema:");
        lblSalida.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

        areaResultados = new TextArea();
        areaResultados.setEditable(false);
        areaResultados.setWrapText(true);
        areaResultados.setPrefHeight(130);
        areaResultados.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
        areaResultados.setPromptText("Aqui se mostraran los recorridos BFS/DFS, rutas de Dijkstra, conexiones ordenadas y avisos de CSV...");

        // Vincular el area de texto a Labels para que todo se muestre en pantalla
        Labels.setAreaSalida(areaResultados);

        panelInferior.getChildren().addAll(lblSalida, areaResultados);

        // Acomodo principal
        panelPrincipal.setTop(barraSuperior);
        panelPrincipal.setLeft(scrollIzquierdo);
        panelPrincipal.setCenter(lienzoGrafo);
        panelPrincipal.setBottom(panelInferior);
    }

    public BorderPane getPanel() {
        return panelPrincipal;
    }

    // Ejecuta QuickSort o Shell Sort segun el criterio seleccionado
    private void ejecutarOrdenamiento(boolean usarQuickSort) {
        String criterio = criterioOrdenamiento.getValue();
        boolean porPeso = "Peso de las aristas".equals(criterio);
        if (usarQuickSort) {
            AccionesConElGrafo.ordenarConexionesQuickSort(grafoActual, porPeso);
        } else {
            AccionesConElGrafo.ordenarConexionesShell(grafoActual, porPeso);
        }
    }

    // Carga el CSV elegido en el comboBox respetando si el checkBox dirigido esta activo
    private void cargarCsvSeleccionado() {
        String nombreArchivo = cargaCSV.getValue();
        if (nombreArchivo == null) {
            Labels.texto("Selecciona un archivo CSV de la lista");
            return;
        }

        grafoActual = Archivo.cargarArchivo(nombreArchivo, checkBoxDirigido.isSelected());
        llenarComboBoxVertices(grafoActual);
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
    }

    // Abre un explorador de archivos para cargar cualquier CSV externo
    private void examinarCsvExterno() {
        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar archivo CSV de conexiones");
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv"));

        File archivo = selector.showOpenDialog(panelPrincipal.getScene().getWindow());
        if (archivo == null) {
            return;
        }

        grafoActual = Archivo.cargarArchivo(archivo, checkBoxDirigido.isSelected());
        llenarComboBoxVertices(grafoActual);
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
        llenarComboBoxVertices(grafoActual);
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Vertice '" + nombre + "' agregado correctamente");
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
            Labels.texto("Ya existe una conexion entre esos vertices, usa Editar Peso");
            return;
        }

        int idArista = siguienteIdArista();
        grafoActual.agregarArista(idArista, origen, destino, peso);
        if (!grafoActual.isDirigido()) {
            grafoActual.agregarArista(idArista + 1, destino, origen, peso);
        }

        limpiarCamposArista();
        llenarComboBoxVertices(grafoActual);
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Conexion agregada: " + origen + " - " + destino + " (Peso: " + peso + ")");
    }

    private int leerPeso(String textoPeso) {
        int peso;
        try {
            peso = Integer.parseInt(textoPeso);
        } catch (NumberFormatException e) {
            Labels.texto("El peso debe ser un numero entero");
            return -1;
        }
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

    private int siguienteIdArista() {
        int cantidad = 0;
        for (Vertice v : grafoActual.getVertices()) {
            cantidad += v.getAristas().size();
        }
        return cantidad;
    }

    // Cambia el nombre del vertice escrito en el campo de texto
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
        llenarComboBoxVertices(grafoActual);
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Vertice editado correctamente");
    }

    // Elimina el vertice escrito junto con todas sus conexiones
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
        llenarComboBoxVertices(grafoActual);
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Vertice eliminado correctamente");
    }

    // Cambia el peso de la conexion entre origen y destino
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
        if (!grafoActual.isDirigido()) {
            grafoActual.modificarPeso(destino, origen, peso);
        }

        limpiarCamposArista();
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Peso de la conexion actualizado correctamente");
    }

    // Elimina la conexion entre origen y destino
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

    private void verMatrizAdyacencia() {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo para mostrar");
            return;
        }
        mostrarVentanaTexto("Matriz de adyacencia", grafoActual.matrizAdyacenciaComoTexto());
    }

    private void verListaAdyacencia() {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo para mostrar");
            return;
        }
        mostrarVentanaTexto("Lista de adyacencia", grafoActual.listaAdyacenciaComoTexto());
    }

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

    private void guardarGrafoCsv() {
        String nombre = txtNombreGrafo.getText().trim();
        if (nombre.toLowerCase().endsWith(".csv")) {
            nombre = nombre.substring(0, nombre.length() - 4).trim();
        }

        if (nombre.isEmpty()) {
            Labels.texto("Escribe el nombre del archivo para guardar el grafo");
            return;
        }
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo para guardar");
            return;
        }
        if (nombre.matches(".*[\\\\/:*?\"<>|].*")) {
            Labels.texto("El nombre del archivo tiene caracteres no permitidos");
            return;
        }
        if (Fondo.class.getResource("/com/example/proyecto/" + nombre + ".csv") != null) {
            Labels.texto("Ese nombre lo usa uno de los CSV incluidos, escribe otro");
            return;
        }

        Archivo.guardarGrafoEnArchivo(grafoActual, nombre);

        String archivoConExtension = nombre + ".csv";
        if (new File(archivoConExtension).exists() && !cargaCSV.getItems().contains(archivoConExtension)) {
            cargaCSV.getItems().add(archivoConExtension);
        }
    }

    private void limpiarLienzo() {
        lienzoGrafo.getChildren().clear();
        grafoActual = new Grafo(checkBoxDirigido.isSelected());
        llenarComboBoxVertices(grafoActual);
        Labels.texto("Lienzo limpio. Listo para crear o cargar un grafo.");
    }

    private void cambiarTipoGrafo() {
        if (grafoActual.getVertices().isEmpty()) {
            grafoActual = new Grafo(checkBoxDirigido.isSelected());
        } else {
            checkBoxDirigido.setSelected(grafoActual.isDirigido());
            Labels.texto("Limpia el lienzo para cambiar el tipo de grafo antes de construir o cargar");
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

    public static void añadirValorAlComboBox(String nombre) {
        cargaCSV.getItems().add(nombre);
    }

    public static String valorComboBox() {
        return cargaCSV.getValue();
    }

    public static String valorComboBoxOpModificables() {
        return operacionesModificables.getValue();
    }

    public static String valorComboBoxVerticesGrafo() {
        return verticesDelGrafo.getValue();
    }

    public static String valorComboBoxVerticesDestino() {
        return verticesDestino.getValue();
    }

    public static void llenarComboBoxVertices(Grafo grafo) {
        verticesDelGrafo.getItems().clear();
        verticesDestino.getItems().clear();
        for (Vertice v : grafo.getVertices()) {
            verticesDelGrafo.getItems().add(v.getDato());
            verticesDestino.getItems().add(v.getDato());
        }
        if (!grafo.getVertices().isEmpty()) {
            verticesDelGrafo.getSelectionModel().selectFirst();
            if (grafo.getVertices().size() > 1) {
                verticesDestino.getSelectionModel().select(1);
            } else {
                verticesDestino.getSelectionModel().selectFirst();
            }
        }
    }
}