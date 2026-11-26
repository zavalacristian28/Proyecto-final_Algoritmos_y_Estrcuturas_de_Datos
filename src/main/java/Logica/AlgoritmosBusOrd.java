package Logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// Algoritmos de busqueda y ordenamiento
public class AlgoritmosBusOrd {

    // Compara dos aristas segun el criterio elegido:
    // porPeso = true - ordena por peso ascendente
    // porPeso = false - ordena alfabeticamente por nombre de verticees
    private static int compararAristas(Arista a1, Arista a2, boolean porPeso) {
        if (porPeso) {
            int compPeso = Integer.compare(a1.getPeso(), a2.getPeso());
            if (compPeso != 0) {
                return compPeso;
            }
            int compOrigen = a1.getOrigen().getDato().compareToIgnoreCase(a2.getOrigen().getDato());
            if (compOrigen != 0) {
                return compOrigen;
            }
            return a1.getDestino().getDato().compareToIgnoreCase(a2.getDestino().getDato());
        } else {
            int compOrigen = a1.getOrigen().getDato().compareToIgnoreCase(a2.getOrigen().getDato());
            if (compOrigen != 0) {
                return compOrigen;
            }
            int compDestino = a1.getDestino().getDato().compareToIgnoreCase(a2.getDestino().getDato());
            if (compDestino != 0) {
                return compDestino;
            }
            return Integer.compare(a1.getPeso(), a2.getPeso());
        }
    }

    // Shell Sort para ordenar conexiones (aristas) por peso o por nombre de vertices
    public static void shellAristas(Arista[] a, int n, boolean porPeso) {
        int inter = n / 2;
        boolean band;
        int i;
        Arista aux;
        while (inter > 0) {
            band = true;
            while (band) {
                band = false;
                i = 0;
                while ((i + inter) < n) {
                    if (compararAristas(a[i], a[i + inter], porPeso) > 0) {
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

    // QuickSort para ordenar conexiones (aristas) por peso o por nombre de vertices
    public static void quickSortAristas(Arista[] A, int ini, int fin, boolean porPeso) {
        int izq = ini;
        int der = fin;
        int pos = ini;
        boolean band = true;

        while (band) {
            band = false;

            while (compararAristas(A[pos], A[der], porPeso) <= 0 && pos != der) {
                der = der - 1;
            }
            if (pos != der) {
                Arista aux = A[pos];
                A[pos] = A[der];
                A[der] = aux;
                pos = der;
                while (compararAristas(A[pos], A[izq], porPeso) >= 0 && pos != izq) {
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
            quickSortAristas(A, ini, pos - 1, porPeso);
        }
        if ((pos + 1) < fin) {
            quickSortAristas(A, pos + 1, fin, porPeso);
        }
    }

    // Shell Sort para arreglo de nombres de vertices
    public static void shell(String[] a, int n) {
        int inter = n / 2;
        boolean band;
        int i;
        String aux;
        while (inter > 0) {
            band = true;
            while (band) {
                band = false;
                i = 0;
                while ((i + inter) < n) {
                    if (a[i].compareToIgnoreCase(a[i + inter]) > 0) {
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

    // QuickSort para arreglo de nombres de vertices
    public static void QuickSort(String[] A, int ini, int fin) {
        int izq = ini;
        int der = fin;
        int pos = ini;
        boolean band = true;

        while (band) {
            band = false;

            while (A[pos].compareToIgnoreCase(A[der]) <= 0 && pos != der) {
                der = der - 1;
            }
            if (pos != der) {
                String aux = A[pos];
                A[pos] = A[der];
                A[der] = aux;
                pos = der;
                while (A[pos].compareToIgnoreCase(A[izq]) >= 0 && pos != izq) {
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

    // Busqueda en anchura (BFS)
    public static String BFS(Grafo grafo, int inicio) {
        if (grafo == null || inicio < 0 || inicio >= grafo.getVertices().size()) {
            return "Vertice inicial invalido";
        }
        int n = grafo.getVertices().size();
        boolean[] visitados = new boolean[n];
        ColaSimple<Integer> cola = new ColaSimple<>(n);
        StringBuilder recorridos = new StringBuilder();

        visitados[inicio] = true;
        cola.insertarDato(inicio);

        while (!cola.estaVacia()) {
            int actual = cola.eliminarDato();
            String nombre = grafo.getVertices().get(actual).getDato();
            recorridos.append(nombre).append(" -> ");

            ArrayList<Arista> aristasActuales = grafo.getVertices().get(actual).getAristas();
            for (Arista arista : aristasActuales) {
                int vecino = arista.getDestino().getId_Vertice();
                if (!visitados[vecino]) {
                    visitados[vecino] = true;
                    cola.insertarDato(vecino);
                }
            }
        }
        if (recorridos.length() >= 4) {
            recorridos.setLength(recorridos.length() - 4);
        }
        return recorridos.toString();
    }

    // Busqueda en profundidad (DFS)
    public static String DFS(Grafo grafo, int inicio) {
        if (grafo == null || inicio < 0 || inicio >= grafo.getVertices().size()) {
            return "Vertice inicial invalido";
        }

        int n = grafo.getVertices().size();
        boolean[] visitados = new boolean[n];
        Pila<Integer> pila = new Pila<>(n * 2);
        StringBuilder recorrido = new StringBuilder();
        pila.push(inicio);

        while (!pila.pilaVacia()) {
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
        if (recorrido.length() >= 4) {
            recorrido.setLength(recorrido.length() - 4);
        }
        return recorrido.toString();
    }

    // Algoritmo de Dijkstra para encontrar la ruta de menor costo entre dos vertices
    public static String dijkstra(Grafo grafo, int inicio, int destino) {
        if (grafo == null) {
            return "No hay un grafo cargado";
        }
        int n = grafo.getVertices().size();
        if (inicio < 0 || inicio >= n || destino < 0 || destino >= n) {
            return "Vertice invalido";
        }

        // Validar que no existan pesos negativos
        for (Vertice v : grafo.getVertices()) {
            for (Arista a : v.getAristas()) {
                if (a.getPeso() < 0) {
                    return "Error: Dijkstra no admite pesos negativos";
                }
            }
        }

        double[] distancias = new double[n];
        boolean[] visitados = new boolean[n];
        int[] anteriores = new int[n];

        Arrays.fill(distancias, Double.POSITIVE_INFINITY);
        Arrays.fill(anteriores, -1);
        distancias[inicio] = 0;

        for (int i = 0; i < n; i++) {
            int menor = -1;
            for (int j = 0; j < n; j++) {
                if (!visitados[j] && (menor == -1 || distancias[j] < distancias[menor])) {
                    menor = j;
                }
            }

            if (menor == -1 || Double.isInfinite(distancias[menor])) {
                break;
            }

            visitados[menor] = true;

            // Se relajan las aristas de la lista de adyacencia (admite aristas de peso 0)
            for (Arista arista : grafo.getVertices().get(menor).getAristas()) {
                int j = arista.getDestino().getId_Vertice();
                if (!visitados[j]) {
                    double nuevaDistancia = distancias[menor] + arista.getPeso();
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
        int actual = destino;
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

        double costo = distancias[destino];
        String costoFormateado = (costo == Math.floor(costo))
                ? String.valueOf((long) costo)
                : String.format("%.2f", costo);

        resultado.append("\nCosto total: ").append(costoFormateado);
        return resultado.toString();
    }
}
