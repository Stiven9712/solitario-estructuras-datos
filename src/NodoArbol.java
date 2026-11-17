/**
 * Nodo de un árbol binario. Guarda un dato y dos referencias a sus hijos:
 * el izquierdo (datos menores) y el derecho (datos mayores).
 *
 * @param <T> tipo de dato que guarda el nodo
 */
public class NodoArbol<T> {
    T dato;
    NodoArbol<T> izquierdo;
    NodoArbol<T> derecho;

    /**
     * Crea un nodo hoja, es decir, sin hijos.
     *
     * @param dato dato que va a guardar el nodo
     */
    public NodoArbol(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }
}