package com.example.proyecto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

// Genera los archivos CSV faltantes para las pruebas de rendimiento (1000 vertices y grafo denso)
public class GeneradorCSV {

    public static void asegurarDatasets() {
        generarSiNoExiste("CSV1000.csv", 1000, 1800, 1000L);
        generarSiNoExiste("CSV500_Denso.csv", 500, 4500, 500L);
    }

    private static void generarSiNoExiste(String nombreArchivo, int numVertices, int numAristas, long semilla) {
        // Verificar si ya existe en resources o en la carpeta raiz del proyecto
        if (GeneradorCSV.class.getResource("/com/example/proyecto/" + nombreArchivo) != null) {
            return;
        }
        File archivoLocal = new File(nombreArchivo);
        if (archivoLocal.exists()) {
            return;
        }

        // Si existe la carpeta de recursos en desarrollo, guardarlo tambien ahi
        File dirRecursos = new File("src/main/resources/com/example/proyecto");
        if (dirRecursos.exists() && dirRecursos.isDirectory()) {
            escribirCsv(new File(dirRecursos, nombreArchivo), numVertices, numAristas, semilla);
        }
        escribirCsv(archivoLocal, numVertices, numAristas, semilla);
    }

    private static void escribirCsv(File destino, int numVertices, int numAristas, long semilla) {
        Random random = new Random(semilla);
        HashSet<String> conexiones = new HashSet<>();

        try (FileWriter fw = new FileWriter(destino)) {
            fw.write("origen,destino,peso\n");

            // Primero asegurar conectividad basica entre los vertices
            for (int i = 1; i < numVertices; i++) {
                int padre = random.nextInt(i);
                String origen = "Ubicacion_" + (padre + 1);
                String dest = "Ubicacion_" + (i + 1);
                int peso = 10 + random.nextInt(491);
                conexiones.add(origen + "->" + dest);
                fw.write(origen + "," + dest + "," + peso + "\n");
            }

            // Completar hasta alcanzar la cantidad de aristas solicitada
            int intentos = 0;
            int maxIntentos = numAristas * 10;
            while (conexiones.size() < numAristas && intentos < maxIntentos) {
                intentos++;
                int u = random.nextInt(numVertices) + 1;
                int v = random.nextInt(numVertices) + 1;
                if (u == v) {
                    continue;
                }
                String origen = "Ubicacion_" + u;
                String dest = "Ubicacion_" + v;
                String clave = origen + "->" + dest;
                if (conexiones.contains(clave)) {
                    continue;
                }
                int peso = 10 + random.nextInt(491);
                conexiones.add(clave);
                fw.write(origen + "," + dest + "," + peso + "\n");
            }
        } catch (IOException e) {
            System.out.println("No se pudo generar " + destino.getName() + ": " + e.getMessage());
        }
    }
}
