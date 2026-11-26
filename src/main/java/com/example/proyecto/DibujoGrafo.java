package com.example.proyecto;

import Logica.Arista;
import Logica.Grafo;
import Logica.Vertice;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;

   // Clase encargada de dibujar un grafo dentro del lienzo de la interfaz.
   // Los vertices se acomodan en forma de elipse y las aristas se dibujan como lineas
   // (con flecha si el grafo es dirigido).
public class DibujoGrafo {

    // Si el grafo tiene mas vertices que esto, ya no se escriben los nombres sobre el lienzo
    private static final int MAX_VERTICES_CON_NOMBRE = 30;

    // Si el grafo tiene mas aristas que esto, ya no se escriben los pesos sobre el lienzo
    private static final int MAX_ARISTAS_CON_PESO = 40;

    public static void dibujar(Pane lienzo, Grafo grafo) {

        lienzo.getChildren().clear();

        ArrayList<Vertice> vertices = grafo.getVertices();
        int n = vertices.size();
        if (n == 0) {
            return;
        }

        //Si el lienzo todavia no tiene tamaño se usan valores por defecto
        double ancho = lienzo.getWidth();
        double alto = lienzo.getHeight();
        if (ancho <= 0) {
            ancho = 800;
        }
        if (alto <= 0) {
            alto = 600;
        }

        boolean mostrarNombres = n <= MAX_VERTICES_CON_NOMBRE;

        int totalAristas = 0;
        for (Vertice v : vertices) {
            totalAristas += v.getAristas().size();
        }
        boolean mostrarPesos = totalAristas <= MAX_ARISTAS_CON_PESO;

        //El tamaño de los vertices depende de cuantos haya
        double radioVertice;
        if (n <= 15) {
            radioVertice = 16;
        } else if (n <= MAX_VERTICES_CON_NOMBRE) {
            radioVertice = 12;
        } else {
            radioVertice = 6;
        }

        //Se deja margen para que los nombres no se salgan del lienzo
        double margenX = mostrarNombres ? 140 : 30;
        double margenY = mostrarNombres ? 50 : 30;
        double radioX = Math.max(ancho / 2 - margenX, 50);
        double radioY = Math.max(alto / 2 - margenY, 50);
        double centroX = ancho / 2;
        double centroY = alto / 2;

        //Posicion de cada vertice, se reparten en la elipse empezando desde arriba
        double[] posX = new double[n];
        double[] posY = new double[n];
        for (int i = 0; i < n; i++) {
            double angulo = 2 * Math.PI * i / n - Math.PI / 2;
            posX[i] = centroX + radioX * Math.cos(angulo);
            posY[i] = centroY + radioY * Math.sin(angulo);
        }

        //Primero las aristas, para que los vertices queden encima de ellas
        for (int i = 0; i < n; i++) {
            Vertice origen = vertices.get(i);
            for (Arista a : origen.getAristas()) {
                int j = vertices.indexOf(a.getDestino());
                if (j == -1) {
                    continue;
                }
                //En un grafo no dirigido la conexion existe en los dos sentidos, solo se dibuja una vez
                if (!grafo.isDirigido() && i > j && tieneArista(a.getDestino(), origen)) {
                    continue;
                }
                dibujarArista(lienzo, posX[i], posY[i], posX[j], posY[j], radioVertice,
                        origen.getDato(), a.getDestino().getDato(), a.getPeso(),
                        grafo.isDirigido(), mostrarPesos, n > MAX_VERTICES_CON_NOMBRE);
            }
        }

        //Despues los vertices con su nombre
        for (int i = 0; i < n; i++) {
            double dirX = (posX[i] - centroX) / radioX;
            double dirY = (posY[i] - centroY) / radioY;
            dibujarVertice(lienzo, posX[i], posY[i], radioVertice,
                    vertices.get(i).getDato(), mostrarNombres, dirX, dirY);
        }
    }

    //Indica si el vertice "desde" tiene una arista que llega al vertice "hacia"
    private static boolean tieneArista(Vertice desde, Vertice hacia) {
        for (Arista a : desde.getAristas()) {
            if (a.getDestino() == hacia) {
                return true;
            }
        }
        return false;
    }

    private static void dibujarArista(Pane lienzo, double x1, double y1, double x2, double y2,
                                      double radioVertice, String origen, String destino, int peso,
                                      boolean dirigido, boolean mostrarPeso, boolean grafoGrande) {

        double dx = x2 - x1;
        double dy = y2 - y1;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        if (distancia == 0) {
            return;
        }

        //Vector unitario, se usa para que la linea empiece y termine en el borde de los circulos
        double ux = dx / distancia;
        double uy = dy / distancia;
        double inicioX = x1 + ux * radioVertice;
        double inicioY = y1 + uy * radioVertice;
        double finX = x2 - ux * radioVertice;
        double finY = y2 - uy * radioVertice;

        Line linea = new Line(inicioX, inicioY, finX, finY);
        if (grafoGrande) {
            linea.setStroke(Color.web("#7f8c8d", 0.35));
            linea.setStrokeWidth(0.8);
        } else {
            linea.setStroke(Color.web("#7f8c8d"));
            linea.setStrokeWidth(1.5);
        }
        lienzo.getChildren().add(linea);
        String separador = dirigido ? " -> " : " - ";
        Tooltip.install(linea, new Tooltip(origen + separador + destino + " (" + peso + ")"));

        if (dirigido) {
            double largo = grafoGrande ? 6 : 11;
            double mitadAncho = grafoGrande ? 3 : 5;

            //Centro de la base de la flecha y vector perpendicular a la linea
            double baseX = finX - ux * largo;
            double baseY = finY - uy * largo;
            double perpX = -uy;
            double perpY = ux;

            Polygon flecha = new Polygon(
                    finX, finY,
                    baseX + perpX * mitadAncho, baseY + perpY * mitadAncho,
                    baseX - perpX * mitadAncho, baseY - perpY * mitadAncho
            );
            flecha.setFill(Color.web("#7f8c8d"));
            lienzo.getChildren().add(flecha);
        }

        if (mostrarPeso) {
            //En grafos dirigidos el peso se pone mas cerca del origen, asi no se encima con la arista contraria
            double t = dirigido ? 0.35 : 0.5;
            Text textoPeso = new Text(String.valueOf(peso));
            textoPeso.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
            textoPeso.setFill(Color.web("#c0392b"));
            //El + uy*6 y - ux*6 mueven el texto un poco hacia un lado de la linea
            textoPeso.setX(inicioX + (finX - inicioX) * t + uy * 6);
            textoPeso.setY(inicioY + (finY - inicioY) * t - ux * 6);
            lienzo.getChildren().add(textoPeso);
        }
    }

    private static void dibujarVertice(Pane lienzo, double x, double y, double radio,
                                       String nombre, boolean mostrarNombre, double dirX, double dirY) {

        Circle circulo = new Circle(x, y, radio);
        circulo.setFill(Color.web("#3498db"));
        circulo.setStroke(Color.web("#2c3e50"));
        circulo.setStrokeWidth(1.5);
        lienzo.getChildren().add(circulo);

        //Siempre se puede ver el nombre pasando el mouse sobre el vertice
        Tooltip.install(circulo, new Tooltip(nombre));

        if (mostrarNombre) {
            Text etiqueta = new Text(nombre);
            etiqueta.setStyle("-fx-font-size: 12px;");
            etiqueta.setFill(Color.web("#2c3e50"));
            double anchoTexto = etiqueta.getLayoutBounds().getWidth();

            //El nombre se coloca hacia afuera del grafo, segun el lado donde este el vertice
            double etiquetaX = x + dirX * (radio + 6);
            double etiquetaY = y + dirY * (radio + 6) + 4;
            if (dirX > 0.3) {
                //lado derecho, el texto empieza en el vertice
            } else if (dirX < -0.3) {
                //lado izquierdo, el texto termina en el vertice
                etiquetaX = etiquetaX - anchoTexto;
            } else {
                //arriba o abajo, el texto se centra
                etiquetaX = etiquetaX - anchoTexto / 2;
            }
            etiqueta.setX(etiquetaX);
            etiqueta.setY(etiquetaY);
            etiqueta.setMouseTransparent(true);
            lienzo.getChildren().add(etiqueta);
        }
    }
}
