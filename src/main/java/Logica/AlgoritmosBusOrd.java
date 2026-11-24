package Logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//Algoritmos de busqueda y ordenamiento
public class AlgoritmosBusOrd {

    //posiblemente esto cause errores por ser static
    private static Grafo grafo;

    public AlgoritmosBusOrd(){
        grafo=new Grafo();
    }
    /*
    Algoritmos de ordenamiento solicitados:
    Shell sort y QuickSort, implementaciones
    en base a los algoritmos vistos en la clase
     */
    public static void shell(int a[], int n) {
        int inter = n / 2;
        boolean band;
        int i;
        int aux;
        while (inter > 0) {
            band = true;
            while (band) {
                band = false;
                i = 0;
                while ((i + inter) < n) {
                    if (a[i] > a[i + inter]) {
                        aux = a[i];
                        a[i] = a[i + inter];
                        a[i + inter] = aux;
                        band = true;
                    }
                    i = i + 1;
                }
            }
            inter = inter / 2;
        }
    }

    public void QuickSort(int[] A, int ini, int fin) {
        int izq = ini;
        int der = fin;
        int pos = ini;
        boolean band = true;

        while (band) {
            band = false;

            while (A[pos] <= A[der] && pos != der) {
                der = der - 1;
            }
            if (pos != der) {
                int aux = A[pos];
                A[pos] = A[der];
                A[der] = aux;
                pos = der;
                while (A[pos] >= A[izq] && pos != izq) {
                    izq = izq + 1;
                }
                if (pos != izq) {
                    band = true;
                    aux = A[pos];
                    A[pos] = A[izq];
                    A[izq] = aux;
                    pos = izq;
                }
            }
        }

        if (ini < (pos - 1)) {
            QuickSort(A, ini, pos - 1);
        }
        if ((pos + 1) < fin) {
            QuickSort(A, pos + 1, fin);
        }
    }

    /*
        Metodos de busqueda BFS y DFS
     */
    public static String BFS(int inicio) {
        if (inicio < 0 || inicio >= grafo.getVertices().size()) {
            return "Vertice inicial invalido";
        }
        boolean[] visitados = new boolean[grafo.getVertices().size()];
        ColaSimple<Integer> cola = new ColaSimple<>();
        StringBuilder recorridos=new StringBuilder();
        visitados[inicio] = true;
        cola.insertarDato(inicio);

        while (cola.tamaño() != 0) {
            int actual = cola.eliminarDato();
            String nombre = grafo.getVertices().get(actual).getDato();
            recorridos.append(nombre).append(" -> ");

            ArrayList<Arista> aristasActuales =
                    grafo.getVertices().get(actual).getAristas();
            for (Arista arista : aristasActuales) {
                int vecino = arista.getDestino().getId_Vertice();
                if (!visitados[vecino]) {
                    visitados[vecino] = true;
                    cola.insertarDato(vecino);
                }
            }
        }
        if (recorridos.length() > 0) {
            recorridos.setLength(recorridos.length() - 4);
        }
        return recorridos.toString();
    }

    /*
        Adaptacion a este programa, principales cambios fueron que es metodo de tipo String, recibe un
        grafo y se utilizo el StringBuilder
     */

    public static String DFS(Grafo grafo,int inicio) {
        if (inicio < 0 || inicio >= grafo.getVertices().size()) {
            return "Vertice inicial invalido";
        }

        boolean[] visitados = new boolean[grafo.getVertices().size()];
        Pila<Integer> pila = new Pila<>();
        StringBuilder recorrido = new StringBuilder();
        pila.push(inicio);

        while (pila.longitudPila(pila) != 0) {

            int actual = pila.pop();
            if (!visitados[actual]) {
                visitados[actual] = true;

                String nombre = grafo.getVertices().get(actual).getDato();
                recorrido.append(nombre).append(" -> ");
                ArrayList<Arista> aristasActuales = grafo.getVertices().get(actual).getAristas();

                for (int i = aristasActuales.size() - 1; i >= 0; i--) {
                    int vecino = aristasActuales.get(i).getDestino().getId_Vertice();
                    if (!visitados[vecino]) {
                        pila.push(vecino);
                    }
                }
            }
        }
        if (recorrido.length() > 0) {
            recorrido.setLength(recorrido.length() - 4);
        }
        return recorrido.toString();
    }

    public static String dijkstra(Grafo grafo, int inicio,int destino) {
        grafo.crearMatrizAdyacencia();
        double[][] matriz = grafo.getMatrizAdyacencia();
        int n = matriz.length;
        if (inicio < 0 || inicio >= n || destino < 0 || destino >= n) {
            return "Vertice invalido";
        }

        double[] distancias = new double[n];
        boolean[] visitados = new boolean[n];
        int[] anteriores = new int[n];

        //El Double Positive infinity genera un valor infinito para los demas vertices a excepcion del inicial

        Arrays.fill(distancias, Double.POSITIVE_INFINITY);
        Arrays.fill(anteriores, -1);
        distancias[inicio] = 0;

        for (int i = 0; i < n; i++) {

            int menor = -1;
            // Buscar el nodo con menor distancia
            for (int j = 0; j < n; j++) {
                if (!visitados[j] &&
                        (menor == -1 || distancias[j] < distancias[menor])) {
                    menor = j;
                }
            }
        //Double.isInfinite verifica si un valor es infinito todavia
            if (menor == -1 || Double.isInfinite(distancias[menor])) {
                break;
            }

            visitados[menor] = true;

            // Actualizar las distancias de sus vecinos
            for (int j = 0; j < n; j++) {

                if (matriz[menor][j] > 0 && !visitados[j]) {

                    int nuevaDistancia =
                            (int) (distancias[menor] + matriz[menor][j]);

                    if (nuevaDistancia < distancias[j]) {
                        distancias[j] = nuevaDistancia;
                        anteriores[j] = menor;
                    }
                }
            }
        }
        if (Double.isInfinite(distancias[destino])) {
            return "No existe una ruta entre los vertices seleccionados";
        }
        ArrayList<Integer> ruta = new ArrayList<>();
        int actual=destino;
        while (actual != -1) {
            ruta.add(actual);
            if (actual == inicio) {
                break;
            }
            actual = anteriores[actual];
        }
        if (ruta.get(ruta.size() - 1) != inicio) {
            return "No se pudo reconstruir la ruta";
        }
        Collections.reverse(ruta);

        StringBuilder resultado = new StringBuilder();

        resultado.append("Vertice de origen: ")
                .append(grafo.getVertices().get(inicio).getDato())
                .append("\n");

        resultado.append("Vertice de destino: ")
                .append(grafo.getVertices().get(destino).getDato())
                .append("\n");

        resultado.append("Ruta: ");

        for (int i = 0; i < ruta.size(); i++) {

            int id = ruta.get(i);

            resultado.append(grafo.getVertices().get(id).getDato());

            if (i < ruta.size() - 1) {
                resultado.append(" -> ");
            }
        }

        resultado.append("\nCosto total: ")
                .append((long) distancias[destino]);

        return resultado.toString();
    }

}
