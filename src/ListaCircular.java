/**
 * Lista circular simple hecha con nodos: el último nodo apunta de vuelta al
 * primero, así que se puede dar vueltas sin llegar nunca a null.
 * En el Solitario guarda el descarte: las cartas robadas entran por el final
 * y el último nodo es la carta visible. Al reciclar, se recorre desde el inicio.
 *
 * @param <T> tipo de dato que guarda la lista
 */
public class ListaCircular<T> {
    private Nodo<T> cabeza;
    private Nodo<T> fin;
    private int tamano;

    /** Crea una lista circular vacía. */
    public ListaCircular() {
        this.cabeza = null;
        this.fin = null;
        this.tamano = 0;
    }

    /**
     * Inserta un dato al inicio de la lista. Complejidad O(1).
     *
     * @param dato dato que se inserta de primero
     */
    public void insertarInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            fin = nuevo;
            nuevo.siguiente = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
            fin.siguiente = cabeza;
        }
        tamano++;
    }

    /**
     * Inserta un dato al final de la lista. Complejidad O(1), porque se guarda
     * una referencia al último nodo.
     *
     * @param dato dato que se inserta de último
     */
    public void insertarFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            fin = nuevo;
            nuevo.siguiente = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
            fin.siguiente = cabeza;
        }
        tamano++;
    }

    /**
     * Inserta un dato en una posición dada (la primera es la 0). Complejidad
     * O(n) en el caso general, porque hay que recorrer hasta esa posición.
     *
     * @param posicion lugar donde se inserta, de 0 hasta el tamaño de la lista
     * @param dato     dato que se inserta
     * @return true si se insertó, false si la posición no es válida
     */
    public boolean insertarPosicion(int posicion, T dato) {
        if (posicion < 0 || posicion > tamano) {
            return false;
        }
        if (posicion == 0) {
            insertarInicio(dato);
            return true;
        }
        if (posicion == tamano) {
            insertarFinal(dato);
            return true;
        }
        Nodo<T> anterior = cabeza;
        for (int i = 0; i < posicion - 1; i++) {
            anterior = anterior.siguiente;
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = anterior.siguiente;
        anterior.siguiente = nuevo;
        tamano++;
        return true;
    }

    /**
     * Elimina la primera aparición de un dato, comparando con equals.
     * Complejidad O(n). Da máximo una vuelta, porque en una lista circular
     * no se llega a null.
     *
     * @param dato dato que se quiere eliminar
     * @return true si lo encontró y lo eliminó, false si no estaba
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        Nodo<T> anterior = fin;
        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            if (actual.dato.equals(dato)) {
                if (tamano == 1) {
                    cabeza = null;
                    fin = null;
                } else {
                    anterior.siguiente = actual.siguiente;
                    if (actual == cabeza) {
                        cabeza = actual.siguiente;
                    }
                    if (actual == fin) {
                        fin = anterior;
                    }
                }
                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Saca y devuelve el primer dato de la lista. Complejidad O(1). Se usa al
     * reciclar el descarte para pasar las cartas de vuelta al mazo.
     *
     * @return el dato de la cabeza, o null si la lista está vacía
     */
    public T eliminarInicio() {
        if (estaVacia()) {
            return null;
        }
        T dato = cabeza.dato;
        if (tamano == 1) {
            cabeza = null;
            fin = null;
        } else {
            cabeza = cabeza.siguiente;
            fin.siguiente = cabeza;
        }
        tamano--;
        return dato;
    }

    /**
     * Saca y devuelve el último dato de la lista. Complejidad O(n), porque
     * al ser una lista simple hay que recorrerla hasta el penúltimo nodo.
     * Se usa al deshacer un robo.
     *
     * @return el dato del último nodo, o null si la lista está vacía
     */
    public T eliminarFinal() {
        if (estaVacia()) {
            return null;
        }
        T dato = fin.dato;
        if (tamano == 1) {
            cabeza = null;
            fin = null;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != fin) {
                actual = actual.siguiente;
            }
            fin = actual;
            fin.siguiente = cabeza;
        }
        tamano--;
        return dato;
    }

    /**
     * Busca un dato y devuelve su posición (la primera es la 0), comparando
     * con equals. Complejidad O(n).
     *
     * @param dato dato que se busca
     * @return la posición del dato, o -1 si no está
     */
    public int buscar(T dato) {
        if (estaVacia()) {
            return -1;
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            if (actual.dato.equals(dato)) {
                return i;
            }
            actual = actual.siguiente;
        }
        return -1;
    }

    /**
     * Imprime en consola todos los datos, dando una vuelta completa desde la
     * cabeza. Complejidad O(n).
     */
    public void recorrer() {
        if (estaVacia()) {
            System.out.println("La lista está vacía");
            return;
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            System.out.print(actual.dato + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }

    /**
     * Devuelve el último dato sin sacarlo. Complejidad O(1). En el descarte es
     * la carta visible.
     *
     * @return el dato del último nodo, o null si la lista está vacía
     */
    public T verUltimo() {
        if (estaVacia()) {
            return null;
        }
        return fin.dato;
    }

    /**
     * Indica si la lista no tiene elementos.
     *
     * @return true si está vacía, false si tiene datos
     */
    public boolean estaVacia() {
        return cabeza == null;
    }

    /**
     * Devuelve cuántos elementos tiene la lista.
     *
     * @return cantidad de elementos
     */
    public int tamano() {
        return tamano;
    }
}
