package com.example.proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Fondo fondo= new Fondo();

        Scene scene = new Scene(fondo.getPanel(), 1100, 700);
        stage.setTitle("Proyecto Final de Grafos");
        stage.setScene(scene);
        stage.show();
    }
}
