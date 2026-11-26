package com.example.proyecto;

import Logica.AccionesConElGrafo;
import Logica.AlgoritmosBusOrd;
import Logica.Archivo;
import Logica.Arista;
import Logica.ColaSimple;
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

    // ComboBox de la interfaz
    private static ComboBox<String> cargaCSV;
    private static ComboBox<String> operacionesModificables;
    private static ComboBox<String> verticesDelGrafo;
    private static ComboBox<String> verticesDestino;
    private ComboBox<String> comboCriterioOrden;

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

        // CONSTRUCCION MANUAL Y EDICION
        Label lblManual = new Label("Construccion manual del grafo");
        lblManual.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        checkBoxDirigido = new CheckBox("Grafo Dirigido");
        checkBoxDirigido.setOnAction(event -> cambiarTipoGrafo());

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

        // OPCIONES DE VISUALIZACION DEL GRAFO
        Label lblOpciones = new Label("Opciones del Grafo");
        lblOpciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Button btnMostrarMatriz = new Button("Ver Matriz de Adyacencia");
        btnMostrarMatriz.setMaxWidth(Double.MAX_VALUE);

        Button btnMostrarLista = new Button("Ver Lista de Adyacencia");
        btnMostrarLista.setMaxWidth(Double.MAX_VALUE);

        Button btnLimpiarGrafo = new Button("Limpiar Lienzo");
        btnLimpiarGrafo.setMaxWidth(Double.MAX_VALUE);

        // GESTION DE ARCHIVOS CSV
        Label lblCsv = new Label("Gestion de Archivos CSV");
        lblCsv.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        cargaCSV = new ComboBox<>();
        cargaCSV.getItems().addAll(
                "CSV20.csv",
                "CSV100.csv",
                "CSV500.csv"
        );
        cargaCSV.setPromptText("Selecciona un archivo CSV");
        cargaCSV.setMaxWidth(Double.MAX_VALUE);

        operacionesModificables = new ComboBox<>();
        operacionesModificables.getItems().addAll(
                "Modificar peso", "Modificar valor del vertice", "Eliminar vertice", "Eliminar arista"
        );

        Button btnCargarCsv = new Button("Cargar CSV");
        btnExportarCsv = new Button("Guardar CSV");

        btnCargarCsv.setPrefWidth(118);
        btnExportarCsv.setPrefWidth(118);

        HBox filaCsv = new HBox(8);
        filaCsv.getChildren().addAll(
                btnCargarCsv,
                btnExportarCsv
        );

        // ALGORITMOS DE BUSQUEDA Y RUTA OPTIMA
        Label lblBusqueda = new Label("Busqueda y Ruta Optima");
        lblBusqueda.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        verticesDelGrafo = new ComboBox<>();
        verticesDelGrafo.setPromptText("Vertice inicial / origen");
        verticesDelGrafo.setMaxWidth(Double.MAX_VALUE);

        verticesDestino = new ComboBox<>();
        verticesDestino.setPromptText("Vertice destino (Dijkstra)");
        verticesDestino.setMaxWidth(Double.MAX_VALUE);

        Button btnBfs = new Button("BFS (Anchura)");
        Button btnDfs = new Button("DFS (Profund.)");
        btnBfs.setPrefWidth(118);
        btnDfs.setPrefWidth(118);

        HBox filaRecorridos = new HBox(8);
        filaRecorridos.getChildren().addAll(btnBfs, btnDfs);

        Button btnDijkstra = new Button("Ruta Optima (Dijkstra)");
        btnDijkstra.setMaxWidth(Double.MAX_VALUE);

        //  ALGORITMOS DE ORDENAMIENTO
        Label lblOrdenamiento = new Label("Ordenar Conexiones");
        lblOrdenamiento.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        comboCriterioOrden = new ComboBox<>();
        comboCriterioOrden.getItems().addAll("Por Nombre de Vertice");
        comboCriterioOrden.setPromptText("Criterio de ordenamiento");
        comboCriterioOrden.setMaxWidth(Double.MAX_VALUE);

        Button btnQuickSort = new Button("QuickSort");
        Button btnShellSort = new Button("Shell Sort");
        btnQuickSort.setPrefWidth(118);
        btnShellSort.setPrefWidth(118);

        HBox filaOrden = new HBox(8);
        filaOrden.getChildren().addAll(btnQuickSort, btnShellSort);

        // EVENTOS DE LOS BOTONES
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

        btnBfs.setOnAction(event -> ejecutarBFS());
        btnDfs.setOnAction(event -> ejecutarDFS());
        btnDijkstra.setOnAction(event -> ejecutarDijkstra());
        btnQuickSort.setOnAction(event -> ejecutarOrdenamiento("QuickSort"));
        btnShellSort.setOnAction(event -> ejecutarOrdenamiento("Shell Sort"));

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
                filaCsv,
                new Separator(),
                lblBusqueda,
                verticesDelGrafo,
                verticesDestino,
                filaRecorridos,
                btnDijkstra,
                new Separator(),
                lblOrdenamiento,
                comboCriterioOrden,
                filaOrden
        );

        ScrollPane scrollIzquierdo = new ScrollPane(panelControladores);
        scrollIzquierdo.setFitToWidth(true);

        lienzoGrafo = new Pane();
        lienzoGrafo.setStyle(
                "-fx-background-color: white; -fx-border-color: #dcdde1;"
        );

        panelPrincipal.setTop(barraSuperior);
        panelPrincipal.setLeft(scrollIzquierdo);
        panelPrincipal.setCenter(lienzoGrafo);
    }

    public BorderPane getPanel() {
        return panelPrincipal;
    }

    private void cargarCsvSeleccionado() {
        String nombreArchivo = cargaCSV.getValue();
        if (nombreArchivo == null) {
            Labels.texto("Selecciona un archivo CSV");
            return;
        }

        grafoActual = Archivo.cargarArchivo(nombreArchivo);
        checkBoxDirigido.setSelected(grafoActual.isDirigido());
        llenarComboBoxVertices(grafoActual);
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("CSV cargado: " + grafoActual.getVertices().size() + " vertices y "
                + AccionesConElGrafo.contarAristas(grafoActual) + " aristas.");
    }

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
        Labels.texto("Vertice agregado correctamente");
    }

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
        if (!grafoActual.isDirigido()) {
            grafoActual.agregarArista(idArista + 1, destino, origen, peso);
        }

        limpiarCamposArista();
        llenarComboBoxVertices(grafoActual);
        DibujoGrafo.dibujar(lienzoGrafo, grafoActual);
        Labels.texto("Conexion agregada correctamente");
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
        Labels.texto("Conexion editada correctamente");
    }

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
            Labels.texto("Escribe el nombre del grafo");
            return;
        }
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("No hay un grafo para guardar");
            return;
        }
        if (nombre.matches(".*[\\\\/:*?\"<>|].*")) {
            Labels.texto("El nombre del grafo tiene caracteres no permitidos");
            return;
        }
        if (Fondo.class.getResource("/com/example/proyecto/" + nombre + ".csv") != null) {
            Labels.texto("Ese nombre lo usa uno de los CSV incluidos, escribe otro");
            return;
        }

        Archivo.guardarGrafoEnArchivo(grafoActual, nombre);

        if (new File(nombre + ".csv").exists() && !cargaCSV.getItems().contains(nombre)) {
            cargaCSV.getItems().add(nombre);
        }
    }

    private void limpiarLienzo() {
        lienzoGrafo.getChildren().clear();
        grafoActual = new Grafo(checkBoxDirigido.isSelected());
        verticesDelGrafo.getItems().clear();
        verticesDestino.getItems().clear();
    }

    private void cambiarTipoGrafo() {
        if (grafoActual.getVertices().isEmpty()) {
            grafoActual = new Grafo(checkBoxDirigido.isSelected());
        } else {
            checkBoxDirigido.setSelected(grafoActual.isDirigido());
            Labels.texto("Limpia el lienzo para cambiar el tipo de grafo");
        }
    }

    // ALGORITMOS DE BUSQUEDA Y RUTA OPTIMA

    private void ejecutarBFS() {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("Primero carga o crea un grafo");
            return;
        }
        String nombre = verticesDelGrafo.getValue();
        if (nombre == null) {
            Labels.texto("Selecciona el vertice inicial");
            return;
        }
        Vertice inicio = grafoActual.buscarVertice(nombre);
        String recorrido = bfsSobreGrafo(grafoActual, inicio.getId_Vertice());
        mostrarVentanaTexto("Recorrido BFS (Anchura)", "Inicio: " + nombre + "\n\nOrden de visita:\n" + recorrido);
    }

    private void ejecutarDFS() {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("Primero carga o crea un grafo");
            return;
        }
        String nombre = verticesDelGrafo.getValue();
        if (nombre == null) {
            Labels.texto("Selecciona el vertice inicial");
            return;
        }
        Vertice inicio = grafoActual.buscarVertice(nombre);
        String recorrido = AlgoritmosBusOrd.DFS(grafoActual, inicio.getId_Vertice());
        mostrarVentanaTexto("Recorrido DFS (Profundidad)", "Inicio: " + nombre + "\n\nOrden de visita:\n" + recorrido);
    }

    private void ejecutarDijkstra() {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("Primero carga o crea un grafo");
            return;
        }
        String nombreOrigen = verticesDelGrafo.getValue();
        String nombreDestino = verticesDestino.getValue();
        if (nombreOrigen == null || nombreDestino == null) {
            Labels.texto("Selecciona el vertice de origen y el de destino");
            return;
        }
        Vertice origen = grafoActual.buscarVertice(nombreOrigen);
        Vertice destino = grafoActual.buscarVertice(nombreDestino);
        String resultado = AlgoritmosBusOrd.dijkstra(grafoActual, origen.getId_Vertice(), destino.getId_Vertice());
        mostrarVentanaTexto("Ruta Optima - Dijkstra", resultado);
    }

    private void ejecutarOrdenamiento(String algoritmo) {
        if (grafoActual.getVertices().isEmpty()) {
            Labels.texto("Primero carga o crea un grafo");
            return;
        }
        String criterio = comboCriterioOrden.getValue();
        if (criterio == null) {
            Labels.texto("Selecciona un criterio de ordenamiento");
            return;
        }

        String resultado = algoritmo.equals("QuickSort")
                ? AccionesConElGrafo.ordenarQuicksort(grafoActual)
                : AccionesConElGrafo.ordenarShell(grafoActual);
        mostrarVentanaTexto("Ordenamiento (" + algoritmo + ")", resultado);
    }

    private String bfsSobreGrafo(Grafo grafo, int inicio) {
        int n = grafo.getVertices().size();
        if (inicio < 0 || inicio >= n) {
            return "Vertice inicial invalido";
        }
        boolean[] visitados = new boolean[n];
        ColaSimple<Integer> cola = new ColaSimple<>(n + 5);
        StringBuilder recorrido = new StringBuilder();

        visitados[inicio] = true;
        cola.insertarDato(inicio);

        while (cola.tamaño() != 0) {
            int actual = cola.eliminarDato();
            recorrido.append(grafo.getVertices().get(actual).getDato()).append(" -> ");

            for (Arista arista : grafo.getVertices().get(actual).getAristas()) {
                int vecino = arista.getDestino().getId_Vertice();
                if (!visitados[vecino]) {
                    visitados[vecino] = true;
                    cola.insertarDato(vecino);
                }
            }
        }
        if (recorrido.length() >= 4) {
            recorrido.setLength(recorrido.length() - 4);
        }
        return recorrido.toString();
    }

    // Getters y metodos estaticos


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
        return instancia.cargaCSV.getValue();
    }

    public static String valorComboBoxOpModificables() {
        return instancia.operacionesModificables.getValue();
    }

    public static String valorComboBoxVerticesGrafo() {
        return instancia.verticesDelGrafo.getValue();
    }

    public static String valorComboBoxVerticesDestino() {
        return instancia.verticesDestino.getValue();
    }

    public static void llenarComboBoxVertices(Grafo grafo) {
        instancia.verticesDelGrafo.getItems().clear();
        instancia.verticesDestino.getItems().clear();
        for (Vertice v : grafo.getVertices()) {
            verticesDelGrafo.getItems().add(v.getDato());
            verticesDestino.getItems().add(v.getDato());
        }
    }
}