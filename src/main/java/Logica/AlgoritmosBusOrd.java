package Logica;

import java.util.ArrayList;

//Algoritmos de busqueda y ordenamiento
public class AlgoritmosBusOrd {
    private Grafo grafo;

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
    public void BFS(int inicio) {

        boolean[] visitados = new boolean[grafo.getVertices().size()];

        ColaSimple<Integer> cola = new ColaSimple<>();

        visitados[inicio] = true;
        cola.insertarDato(inicio);

        while (cola.tamaño() != 0) {

            int actual = cola.eliminarDato();

            System.out.print(actual + " ");

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
    }

    public void DFS(int inicio) {

        boolean[] visitados = new boolean[grafo.getVertices().size()];

        Pila<Integer> pila = new Pila<>();

        pila.push(inicio);

        while (pila.longitudPila(pila) != 0) {

            int actual = pila.pop();

            if (!visitados[actual]) {

                visitados[actual] = true;

                System.out.print(actual + " ");

                ArrayList<Arista> aristasActuales = grafo.getVertices().get(actual).getAristas();
                for (int i = aristasActuales.size() - 1; i >= 0; i--) {
                    int vecino = aristasActuales.get(i).getDestino().getId_Vertice();
                    if (!visitados[vecino]) {
                        pila.push(vecino);
                    }
                }
            }
        }
    }

}
