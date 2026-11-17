/**
 * Nodo de una lista enlazada simple. Guarda un dato y una refencia al nodo
 * que viene despues. Es la pieza basica de la pila y de la cola
 *
 * @param <T> tipo de dato que guarda el nodo

*/

public class Nodo<T> {
    T dato;
    Nodo <T> siguiente;

    /**
     * Crea un nodo con el dato dado, sin nodo siquiente
     *
     * @param dato dato que va a guardar el nodo
     */

    public Nodo(T dato){
        this.dato = dato;
        this.siguiente = null;
    }
}

