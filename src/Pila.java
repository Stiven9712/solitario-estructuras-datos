public class Pila<T> {
    private Nodo<T> tope;
    private  int tamano;

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }
    public T desapilar () {
        if (estaVacia()) {
            return null;
        }
        T dato = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return  dato;
    }

    public T verTope() {
        if (estaVacia()) {
            return null;
        }
        return tope.dato;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int tamano() {
        return tamano;
    }
}
