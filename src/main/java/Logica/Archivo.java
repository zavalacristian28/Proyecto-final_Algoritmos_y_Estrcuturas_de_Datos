package Logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Archivo {
    private String rutaArchivo;

    public void cargarArchivo(int num){
        rutaArchivo="C:/Users/Zaval/IdeaProjects/Proyecto/Proyecto/src/main/resources/com/example/proyecto/CSV"+num+".csv";

        try(BufferedReader bf=new BufferedReader(new FileReader(rutaArchivo))){
            String linea;
            bf.readLine();
            while((linea=bf.readLine())!=null){
                if(linea.trim().isEmpty()){
                    continue;
                }
                String datosArchivo[] = linea.split(",");

                //Validacion que el archivo tenga las columnas requeridas
                //3 columnas: origen, destino y peso
                if(datosArchivo.length != 3){
                    System.out.println("Error en la línea: " + linea);
                    continue;
                }
                //validacion para evitar conexion con vertices vacios
                if(datosArchivo[0].equals(datosArchivo[1])){
                    System.out.println("vertice vacio");
                    continue;
                }



            }


        }catch (IOException e){
            System.out.println("Error al cargar");
        }
    }
}
