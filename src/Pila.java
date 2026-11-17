/**
 * Pila (LIFO: el ultimo en entrar es el primero en salir) hecha en nodos.
 * En el Solitario se usa en las fundaciones, el historia de jugadas y para
 * invertir grupos de cartas al moverlas entre columnas
 *
 * @param <T> tipo de dato que guarda la pila
 */
public class Pila<T> {
    private Nodo<T> tope;
    private  int tamano;

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    /**
     * Pone un dato encima de la pila. Complejidad 0(1).
     *
     * @param dato dato que se va apilar
     */

    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    /**
     * Saca y devuelve el dato de arriba. Complejidad 0(1).
     *
     * @return el dato del tope, o null si pila esta vacia
     */
    public T desapilar () {
        if (estaVacia()) {
            return null;
        }
        T dato = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return  dato;
    }

    /**
     * Devuelve el dato de arriba sin sacarlo.  Complejidad 0(1).
     *
     * @return el dato del tope, o null si la pila esta vacia
     */

    public T verTope() {
        if (estaVacia()) {
            return null;
        }
        return tope.dato;
    }

    /**
     * Indica si la pila no tiene elementos.
     *
     * @return true si esta vacia, false si tiene elementos
     */

    public boolean estaVacia() {
        return tope == null;
    }

    /**
     * Devuelve cuántos elementos tiene la pila
     *
     * @return cantidad de elementos
     */

    public int tamano() {
        return tamano;
    }
}
