/**
 * Cola (FIFO: el primero en entrar es el primero en salir) hecha con nodos.
 * En el Solitario guarda el mazo de robo: las cartas se roban por el frente
 * y, cuando el mazo se acaba, el descarte se vuelve a encolar.
 *
 * @param <T> tipo de dato que guarda la cola
 */
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamano;

    /** Crea una cola vacia */
    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }

    /**
     * Pone un dato al final de la cola. Complejidad 0(1), por que se guarda
     * una referencia al último nodo y no hay que recorrer la cola
     *
     * @param dato dato que entra en la fila
     */
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

    /**
     * Pone un dato al frente de la cola. No es una operacion de una cola pura:
     * es una extencion que existe para poder deshacer un robo y devolver la
     * carta al mazo. Complejidad 0(1).
     *
     * @param dato dato que se coloca de primero
     */
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

    /**
     * Saca y devuelve el dato del frente. Complejidad 0(1).
     *
     * @return el dato del frente, o null si la cola esta vacia
     */
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

    /**
     * Devuelve el dato del frente sin sacarlo. Complejidad 0(1).
     *
     * @return el dato del frente, o null si la cola esta vacia
     */
    public T verFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.dato;
    }

    /**
     * Indica si la cola no tiene elemtos.
     *
     * @return true si esta vacía, false si tiene datos
     */
    public boolean estaVacia() {
        return frente == null;
    }

    /**
     * Devuelve cuantos elementos tiene la cola.
     *
     * @return cantidad de elementos
     */
    public int tamano() {
        return tamano;
    }


}
