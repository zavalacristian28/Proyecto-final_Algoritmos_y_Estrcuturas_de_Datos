package com.example.proyecto;

import Logica.AccionesConElGrafo;
import Logica.Archivo;
import Logica.Grafo;
import Logica.Resultados;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// Ventana con tablas, graficas y analisis teorico de complejidad
public class VentanaAnalisis {

    public static void mostrar() {
        GeneradorCSV.asegurarDatasets();

        Stage stage = new Stage();
        stage.setTitle("Analisis Teorico y Empirico de Algoritmos");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Barra superior con boton para recalcular tiempos
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(0, 0, 10, 0));
        Label lblInfo = new Label("Pruebas sobre grafos de 20, 100, 500 (disperso y denso) y 1,000 vertices:");
        lblInfo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        Button btnReejecutar = new Button("Volver a medir tiempos");
        topBar.getChildren().addAll(lblInfo, btnReejecutar);

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Tab 1: Tabla de resultados
        TableView<Resultados> tabla = crearTablaResultados();
        Tab tabTabla = new Tab("1. Tabla de Tiempos", tabla);

        // Tab 2: Grafica de Carga CSV y Algoritmos de Grafos
        BarChart<String, Number> graficaGrafos = crearGrafica("Rendimiento: Carga CSV, BFS, DFS y Dijkstra");
        Tab tabGraficaGrafos = new Tab("2. Grafica Recorridos y Carga", graficaGrafos);

        // Tab 3: Grafica Comparativa QuickSort vs Shell Sort
        BarChart<String, Number> graficaOrdenamiento = crearGrafica("Comparativa: QuickSort vs Shell Sort");
        Tab tabGraficaOrden = new Tab("3. Grafica Ordenamiento", graficaOrdenamiento);

        // Tab 4: Analisis Teorico
        TextArea areaTeoria = new TextArea(obtenerTextoAnalisisTeorico());
        areaTeoria.setEditable(false);
        areaTeoria.setWrapText(true);
        areaTeoria.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
        Tab tabTeoria = new Tab("4. Analisis Teorico (Big-O)", areaTeoria);

        tabs.getTabs().addAll(tabTabla, tabGraficaGrafos, tabGraficaOrden, tabTeoria);

        // Ejecutar mediciones iniciales
        ejecutarPruebas(tabla, graficaGrafos, graficaOrdenamiento);

        btnReejecutar.setOnAction(e -> ejecutarPruebas(tabla, graficaGrafos, graficaOrdenamiento));

        root.setTop(topBar);
        root.setCenter(tabs);

        Scene scene = new Scene(root, 980, 620);
        stage.setScene(scene);
        stage.show();
    }

    private static TableView<Resultados> crearTablaResultados() {
        TableView<Resultados> tabla = new TableView<>();

        TableColumn<Resultados, String> colMetodo = new TableColumn<>("Algoritmo / Operacion");
        colMetodo.setPrefWidth(290);
        colMetodo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMetodo()));

        TableColumn<Resultados, Integer> colVertices = new TableColumn<>("Vertices (V)");
        colVertices.setPrefWidth(130);
        colVertices.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCantidadVertices()).asObject());

        TableColumn<Resultados, Integer> colAristas = new TableColumn<>("Aristas (E)");
        colAristas.setPrefWidth(130);
        colAristas.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCantidadAristas()).asObject());

        TableColumn<Resultados, String> colTiempo = new TableColumn<>("Tiempo (ms)");
        colTiempo.setPrefWidth(160);
        colTiempo.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.4f", d.getValue().getTiempoMs())));

        tabla.getColumns().addAll(colMetodo, colVertices, colAristas, colTiempo);
        return tabla;
    }

    private static BarChart<String, Number> crearGrafica(String titulo) {
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Dataset (Vertices / Aristas)");

        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Tiempo de ejecucion (ms)");

        BarChart<String, Number> chart = new BarChart<>(ejeX, ejeY);
        chart.setTitle(titulo);
        chart.setAnimated(false);
        return chart;
    }

    private static void ejecutarPruebas(TableView<Resultados> tabla,
                                        BarChart<String, Number> graficaGrafos,
                                        BarChart<String, Number> graficaOrdenamiento) {
        String[] archivos = {
                "CSV20.csv",
                "CSV100.csv",
                "CSV500.csv",
                "CSV500_Denso.csv",
                "CSV1000.csv"
        };

        ObservableList<Resultados> listaResultados = FXCollections.observableArrayList();

        XYChart.Series<String, Number> serieCarga = new XYChart.Series<>();
        serieCarga.setName("Carga CSV");

        XYChart.Series<String, Number> serieBFS = new XYChart.Series<>();
        serieBFS.setName("BFS");

        XYChart.Series<String, Number> serieDFS = new XYChart.Series<>();
        serieDFS.setName("DFS");

        XYChart.Series<String, Number> serieDijkstra = new XYChart.Series<>();
        serieDijkstra.setName("Dijkstra");

        XYChart.Series<String, Number> serieQuickPeso = new XYChart.Series<>();
        serieQuickPeso.setName("QuickSort (Peso)");

        XYChart.Series<String, Number> serieShellPeso = new XYChart.Series<>();
        serieShellPeso.setName("Shell Sort (Peso)");

        XYChart.Series<String, Number> serieQuickNombre = new XYChart.Series<>();
        serieQuickNombre.setName("QuickSort (Nombre)");

        XYChart.Series<String, Number> serieShellNombre = new XYChart.Series<>();
        serieShellNombre.setName("Shell Sort (Nombre)");

        Labels.setModoSilencioso(true);
        // Calentamiento breve de la JVM para estabilizar tiempos
        Grafo warmup = Archivo.cargarArchivo("CSV20.csv", true);
        if (!warmup.getVertices().isEmpty()) {
            AccionesConElGrafo.medirTiempo("BFS", warmup, 0, warmup.getVertices().size() - 1);
        }

        for (String archivo : archivos) {
            Resultados rCarga = AccionesConElGrafo.medirCargaArchivo(archivo);
            Labels.setModoSilencioso(true);
            Grafo g = Archivo.cargarArchivo(archivo, true);
            Labels.setModoSilencioso(false);

            int v = g.getVertices().size();
            int e = AccionesConElGrafo.contarAristas(g);
            if (v == 0) {
                continue;
            }

            String etiquetaDataset = archivo.replace(".csv", "") + " (" + v + "V, " + e + "E)";
            int destino = v - 1;

            Resultados rBFS = AccionesConElGrafo.medirTiempo("BFS", g, 0, destino);
            Resultados rDFS = AccionesConElGrafo.medirTiempo("DFS", g, 0, destino);
            Resultados rDijkstra = AccionesConElGrafo.medirTiempo("Dijkstra", g, 0, destino);
            Resultados rQuickPeso = AccionesConElGrafo.medirTiempo("Quicksort Peso", g, 0, destino);
            Resultados rShellPeso = AccionesConElGrafo.medirTiempo("Shell Peso", g, 0, destino);
            Resultados rQuickNombre = AccionesConElGrafo.medirTiempo("Quicksort Nombre", g, 0, destino);
            Resultados rShellNombre = AccionesConElGrafo.medirTiempo("Shell Nombre", g, 0, destino);

            listaResultados.addAll(
                    rCarga, rBFS, rDFS, rDijkstra,
                    rQuickPeso, rShellPeso, rQuickNombre, rShellNombre
            );

            serieCarga.getData().add(new XYChart.Data<>(etiquetaDataset, rCarga.getTiempoMs()));
            serieBFS.getData().add(new XYChart.Data<>(etiquetaDataset, rBFS.getTiempoMs()));
            serieDFS.getData().add(new XYChart.Data<>(etiquetaDataset, rDFS.getTiempoMs()));
            serieDijkstra.getData().add(new XYChart.Data<>(etiquetaDataset, rDijkstra.getTiempoMs()));

            serieQuickPeso.getData().add(new XYChart.Data<>(etiquetaDataset, rQuickPeso.getTiempoMs()));
            serieShellPeso.getData().add(new XYChart.Data<>(etiquetaDataset, rShellPeso.getTiempoMs()));
            serieQuickNombre.getData().add(new XYChart.Data<>(etiquetaDataset, rQuickNombre.getTiempoMs()));
            serieShellNombre.getData().add(new XYChart.Data<>(etiquetaDataset, rShellNombre.getTiempoMs()));
        }

        tabla.setItems(listaResultados);

        graficaGrafos.getData().clear();
        graficaGrafos.getData().addAll(serieCarga, serieBFS, serieDFS, serieDijkstra);

        graficaOrdenamiento.getData().clear();
        graficaOrdenamiento.getData().addAll(serieQuickPeso, serieShellPeso, serieQuickNombre, serieShellNombre);
    }

    private static String obtenerTextoAnalisisTeorico() {
        return "ANALISIS TEORICO DE COMPLEJIDAD TEMPORAL (NOTACION BIG-O)\n"
                + "=========================================================\n\n"
                + "1. Lectura del archivo CSV y construccion del grafo:\n"
                + "   - Lectura de L lineas del archivo: O(L), donde L es el numero de conexiones.\n"
                + "   - Busqueda lineal de vertices en ArrayList (buscarVertice): O(V) por cada insercion.\n"
                + "   - Construccion de la lista de adyacencia: O(L * V) en el peor caso.\n"
                + "   - Construccion de la matriz de adyacencia (crearMatrizAdyacencia): O(V^2 + E).\n\n"
                + "2. Algoritmos de busqueda (BFS y DFS):\n"
                + "   - BFS (Busqueda en anchura con lista de adyacencia y cola con extraccion O(1)): O(V + E).\n"
                + "     Cada vertice se visita una vez y cada arista se explora una vez (dos en no dirigidos).\n"
                + "   - DFS (Busqueda en profundidad con pila y lista de adyacencia): O(V + E).\n"
                + "     Al igual que BFS, recorre todos los vertices alcanzables y sus aristas incidentes.\n\n"
                + "3. Algoritmo de ruta optima (Dijkstra):\n"
                + "   - Implementacion con lista de adyacencia y busqueda lineal del minimo: O(V^2 + E).\n"
                + "     En cada una de las V iteraciones se recorre el arreglo de V distancias para elegir\n"
                + "     el nodo no visitado con menor costo (O(V^2) en total) y se relajan las aristas de\n"
                + "     su lista de adyacencia (O(E) en total).\n"
                + "     (Nota: Con cola de prioridad y lista de adyacencia seria O((V + E) log V)).\n\n"
                + "4. Algoritmos de ordenamiento de conexiones (sobre E aristas):\n"
                + "   - QuickSort:\n"
                + "     * Mejor caso y caso promedio: O(E log E).\n"
                + "     * Peor caso (pivote en extremo con arreglo ya ordenado): O(E^2).\n"
                + "   - Shell Sort (secuencia de brechas n/2, n/4, ..., 1):\n"
                + "     * Mejor caso: O(E log E).\n"
                + "     * Caso promedio / peor caso: la version implementada repite pasadas por cada brecha\n"
                + "       hasta que no hay intercambios, por lo que en el peor caso llega a O(E^2).\n\n"
                + "5. Impacto del numero de aristas (E) en el rendimiento:\n"
                + "   - En grafos densos (como CSV500_Denso vs CSV500), E crece mucho mas rapido que V.\n"
                + "   - BFS, DFS, QuickSort y Shell Sort incrementan su tiempo directamente en funcion de E,\n"
                + "     mientras que Dijkstra mantiene un costo base O(V^2) por la busqueda lineal del\n"
                + "     minimo, al que se suma O(E) por las aristas.\n\n"
                + "6. Tiempos empiricos:\n"
                + "   - Cada tiempo de la tabla es el promedio de 5 ejecuciones.";
    }
}
