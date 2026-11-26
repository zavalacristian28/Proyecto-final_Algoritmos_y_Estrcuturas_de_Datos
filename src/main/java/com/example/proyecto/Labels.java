package com.example.proyecto;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class Labels {
    private static String ultimoMensaje = "";
    private static TextArea areaSalida;
    private static boolean modoSilencioso = false;

    public static void setAreaSalida(TextArea area) {
        areaSalida = area;
    }

    public static void setModoSilencioso(boolean silencioso) {
        modoSilencioso = silencioso;
    }

    public static String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public static void crearLabel(String texto) {
        ultimoMensaje = texto;
        if (modoSilencioso) {
            return;
        }

        if (areaSalida != null) {
            areaSalida.setText(texto);
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso del Sistema");
        alerta.setHeaderText(null);

        if (texto != null && texto.contains("\n")) {
            TextArea area = new TextArea(texto);
            area.setEditable(false);
            area.setWrapText(true);
            area.setPrefSize(420, 220);
            alerta.getDialogPane().setContent(area);
        } else {
            alerta.setContentText(texto);
        }
        alerta.showAndWait();
    }

    public static void texto(String texto) {
        crearLabel(texto);
    }
}
