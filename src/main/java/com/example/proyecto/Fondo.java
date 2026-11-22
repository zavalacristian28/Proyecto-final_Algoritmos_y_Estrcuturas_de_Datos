package com.example.proyecto;

import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Fondo {

    //Contenido principal que dividira las zonas de la pantalla
    private BorderPane panelPrincipal;

    //Lienzo donde se dibujara los grafos
    private Pane lienzoGrafo;

    //Panel para los botones
    private VBox panelControladores;

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

    }

    public BorderPane getPanel() {
        return panelPrincipal;
    }



}
