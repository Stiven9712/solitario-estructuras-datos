/**
 * Nodo de una lista doblemente enlazada. Guarda un dato y dos referencias:
 * al nodo anterior y al siguiente, lo que le permite recorrer en ambos sentidos.
 *
 * @param <T> tipo de dato que guarda el nodo
 */
public class NodoDoble <T> {
    T dato;
    NodoDoble <T> anterior;
    NodoDoble <T> siguiente;

    /**
     * Crea un nodo con el dato dado, sin anterior ni siguiente.
     *
     * @param dato dato que va a guardar el nodo
     */
    public NodoDoble(T dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
}
