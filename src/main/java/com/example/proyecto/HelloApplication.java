package com.example.proyecto;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Generar datasets adicionales si aun no existen
        GeneradorCSV.asegurarDatasets();

        Fondo fondo = new Fondo();
        Scene scene = new Scene(fondo.getPanel(), 1200, 760);
        stage.setTitle("Proyecto Final - Algoritmos y Estructuras de Datos (Grafos)");
        stage.setScene(scene);
        stage.show();
    }
}
