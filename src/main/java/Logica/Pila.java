package Logica;

public class Pila<T> {

    private T[] pila;
    private int tope = -1;

    @SuppressWarnings("unchecked")
    public Pila() {
        pila = (T[]) new Object[100];
    }

    @SuppressWarnings("unchecked")
    public Pila(int capacidad) {
        int cap = Math.max(10, capacidad);
        pila = (T[]) new Object[cap];
    }

    @SuppressWarnings("unchecked")
    private void ampliarCapacidad() {
        T[] nuevaPila = (T[]) new Object[pila.length * 2];
        System.arraycopy(pila, 0, nuevaPila, 0, pila.length);
        pila = nuevaPila;
    }

    public int getTope() {
        return tope;
    }

    public boolean pilaLlena() {
        return tope == pila.length - 1;
    }

    public boolean pilaVacia() {
        return tope == -1;
    }

    public void push(T dato) {
        if (pilaLlena()) {
            ampliarCapacidad();
        }
        tope++;
        pila[tope] = dato;
    }

    public T pop() {
        if (pilaVacia()) {
            System.out.println("Subdesbordamiento");
            return null;
        }
        T dato = pila[tope];
        pila[tope] = null;
        tope--;
        return dato;
    }

    public static String invierteCadena(String cadena) {
        if (cadena == null) {
            return null;
        }
        Pila<Character> p = new Pila<>(cadena.length());
        StringBuilder nuevaCadena = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            p.push(cadena.charAt(i));
        }
        while (!p.pilaVacia()) {
            nuevaCadena.append(p.pop());
        }
        return nuevaCadena.toString();
    }

    public int longitudPila(Pila<T> pila) {
        return pila.tope + 1;
    }
}
