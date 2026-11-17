import javax.management.openmbean.TabularData;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamano;

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.siguiente = nuevo;
        }
        fin = nuevo;
        tamano++;
    }

    public void encolarFrente(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if(estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            nuevo.siguiente = frente;
            frente = nuevo;
        }
        tamano++;
    }

    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamano--;
        return dato;
    }

    public T verFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }
    public int tamano() {
        return tamano;
    }


}
