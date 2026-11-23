package com.example.proyecto;

import Logica.Archivo;
import Logica.Grafo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Fondo fondo= new Fondo();
        Archivo archivo=new Archivo();
        Grafo grafo=archivo.cargarArchivo(20);
       // grafo.mostrarGrafo();
        grafo.crearMatrizAdyacencia();
        grafo.mostrarMatrizAdyacencia();
        grafo.mostrarListaAdyacencia();
        Scene scene = new Scene(fondo.getPanel(), 820, 740);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
