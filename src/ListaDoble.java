/**
 * Lista doblemente enlazada hecha con nodos que conocen a su anterior y a su
 * siguiente. En el Solitario guarda cada una de las 7 columnas del tablero:
 * permite mover grupos de cartas desde el final y llegar a una posición por
 * el lado más corto.
 *
 * @param <T> tipo de dato que guarda la lista
 */
public class ListaDoble<T> {
    private NodoDoble<T> cabeza;
    private NodoDoble<T> fin;
    private int tamano;

    /** Crea una lista vacía. */
    public ListaDoble() {
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
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            fin = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
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
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            fin = nuevo;
        } else {
            nuevo.anterior = fin;
            fin.siguiente = nuevo;
            fin = nuevo;
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
        NodoDoble<T> anterior = cabeza;
        for (int i = 0; i < posicion - 1; i++) {
            anterior = anterior.siguiente;
        }
        NodoDoble<T> siguiente = anterior.siguiente;
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        nuevo.anterior = anterior;
        nuevo.siguiente = siguiente;
        anterior.siguiente = nuevo;
        siguiente.anterior = nuevo;
        tamano++;
        return true;
    }

    /**
     * Elimina la primera aparición de un dato, comparando con equals.
     * Complejidad O(n), por la búsqueda del nodo; el desenganche es O(1).
     *
     * @param dato dato que se quiere eliminar
     * @return true si lo encontró y lo eliminó, false si no estaba
     */
    public boolean eliminar(T dato) {
        NodoDoble<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                if (actual.anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    actual.anterior.siguiente = actual.siguiente;
                }
                if (actual.siguiente == null) {
                    fin = actual.anterior;
                } else {
                    actual.siguiente.anterior = actual.anterior;
                }
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Saca y devuelve el último dato de la lista. Complejidad O(1), porque el
     * penúltimo nodo se alcanza directo con la referencia anterior. Se usa
     * para sacar cartas del final de una columna.
     *
     * @return el dato del último nodo, o null si la lista está vacía
     */
    public T eliminarFinal() {
        if (estaVacia()) {
            return null;
        }
        T dato = fin.dato;
        if (cabeza == fin) {
            cabeza = null;
            fin = null;
        } else {
            fin = fin.anterior;
            fin.siguiente = null;
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
        NodoDoble<T> actual = cabeza;
        int posicion = 0;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return posicion;
            }
            actual = actual.siguiente;
            posicion++;
        }
        return -1;
    }

    /**
     * Devuelve el dato de una posición sin sacarlo. Empieza por la cabeza o por
     * el fin, según cuál quede más cerca. Complejidad O(n), con un máximo de
     * n/2 pasos.
     *
     * @param posicion posición del dato (la primera es la 0)
     * @return el dato de esa posición, o null si la posición no existe
     */
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamano) {
            return null;
        }
        NodoDoble<T> actual;
        if (posicion < tamano / 2) {
            actual = cabeza;
            for (int i = 0; i < posicion; i++) {
                actual = actual.siguiente;
            }
        } else {
            actual = fin;
            for (int i = tamano - 1; i > posicion; i--) {
                actual = actual.anterior;
            }
        }
        return actual.dato;
    }

    /**
     * Imprime en consola todos los datos, de la cabeza al final.
     * Complejidad O(n).
     */
    public void recorrerAdelante() {
        if (estaVacia()) {
            System.out.println("La lista está vacía");
            return;
        }
        NodoDoble<T> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.dato + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }

    /**
     * Imprime en consola todos los datos, del final a la cabeza.
     * Complejidad O(n). Solo es posible porque la lista es doble.
     */
    public void recorrerAtras() {
        if (estaVacia()) {
            System.out.println("La lista está vacía");
            return;
        }
        NodoDoble<T> actual = fin;
        while (actual != null) {
            System.out.print(actual.dato + " ");
            actual = actual.anterior;
        }
        System.out.println();
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
