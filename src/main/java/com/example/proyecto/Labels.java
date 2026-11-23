package com.example.proyecto;

public class Labels {
    private String textoError;

    public String getTextoError(){
        return textoError;
    }
    public void setTextoError(String textoError){
        this.textoError=textoError;
    }

    public static void crearLabel(String texto){

    }

    public static void texto(String texto) {
        crearLabel(texto);
    }

    public void mensajeError(){
        String texto=texto();
    }
}
