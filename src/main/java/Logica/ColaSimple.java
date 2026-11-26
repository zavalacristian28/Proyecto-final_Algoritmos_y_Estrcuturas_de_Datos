package Logica;

public class ColaSimple<T> {

    private T[] cola;
    private int fin = -1;
    private int inicio = -1;

    @SuppressWarnings("unchecked")
    public ColaSimple() {
        cola = (T[]) new Object[100];
    }

    @SuppressWarnings("unchecked")
    public ColaSimple(int capacidad) {
        int cap = Math.max(10, capacidad);
        cola = (T[]) new Object[cap];
    }

    @SuppressWarnings("unchecked")
    private void ampliarCapacidad() {
        T[] nuevaCola = (T[]) new Object[cola.length * 2];
        int count = tamaño();
        for (int i = 0; i < count; i++) {
            nuevaCola[i] = cola[inicio + i];
        }
        cola = nuevaCola;
        inicio = (count > 0) ? 0 : -1;
        fin = count - 1;
    }

    public void insertarDato(T dato) {
        if (fin == cola.length - 1) {
            ampliarCapacidad();
        }
        if (inicio == -1) {
            inicio = 0;
        }
        fin++;
        cola[fin] = dato;
    }

    public T eliminarDato() {
        if (inicio == -1) {
            System.out.println("Subdesbordamiento");
            return null;
        }

        T dato = cola[inicio];
        cola[inicio] = null;

        // Se avanza el indice de inicio en lugar de recorrer el arreglo: O(1)
        if (inicio == fin) {
            inicio = -1;
            fin = -1;
        } else {
            inicio++;
        }
        return dato;
    }

    public boolean estaVacia() {
        return inicio == -1;
    }

    public int tamaño() {
        if (inicio == -1) {
            return 0;
        }
        return fin - inicio + 1;
    }
}
