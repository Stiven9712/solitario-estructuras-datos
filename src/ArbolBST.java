/**
 * Árbol binario de búsqueda (BST): lo menor va a la izquierda y lo mayor a la
 * derecha. No acepta datos repetidos. En el Solitario guarda el ranking de
 * mejores puntajes. Las operaciones de insertar, buscar y eliminar cuestan
 * O(log n) si el árbol está balanceado y O(n) en el peor caso (cuando los
 * datos entran ya ordenados y el árbol queda como una lista).
 *
 * @param <T> tipo de dato que guarda el árbol; debe poder compararse
 */
public class ArbolBST<T extends Comparable<T>> {
    private NodoArbol<T> raiz;
    private int tamano;

    /** Crea un árbol vacío. */
    public ArbolBST() {
        this.raiz = null;
        this.tamano = 0;
    }

    /**
     * Inserta un dato en el lugar que le corresponde. Si ya existe, no lo
     * repite. Complejidad O(log n) en promedio.
     *
     * @param dato dato que se inserta
     * @return true si se insertó, false si el dato es null o ya existía
     */
    public boolean insertar(T dato) {
        if (dato == null) {
            return false;
        }
        int tamanoAntes = tamano;
        raiz = insertarRec(raiz, dato);
        return tamano > tamanoAntes;
    }

    /**
     * Método recursivo de apoyo de insertar: baja por el árbol hasta encontrar
     * el hueco donde va el dato.
     *
     * @param actual nodo que se está revisando
     * @param dato   dato que se inserta
     * @return el nodo ya actualizado, para enlazarlo con su padre
     */
    private NodoArbol<T> insertarRec(NodoArbol<T> actual, T dato) {
        if (actual == null) {
            tamano++;
            return new NodoArbol<>(dato);
        }
        int comparacion = dato.compareTo(actual.dato);
        if (comparacion < 0) {
            actual.izquierdo = insertarRec(actual.izquierdo, dato);
        } else if (comparacion > 0) {
            actual.derecho = insertarRec(actual.derecho, dato);
        }
        return actual;
    }

    /**
     * Busca un dato siguiendo un solo camino desde la raíz. Complejidad
     * O(log n) en promedio.
     *
     * @param dato dato que se busca
     * @return true si el dato está en el árbol, false si no
     */
    public boolean buscar(T dato) {
        if (dato == null) {
            return false;
        }
        NodoArbol<T> actual = raiz;
        while (actual != null) {
            int comparacion = dato.compareTo(actual.dato);
            if (comparacion == 0) {
                return true;
            } else if (comparacion < 0) {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
        }
        return false;
    }

    /**
     * Imprime los datos en inorden (izquierda, nodo, derecha), que en un BST
     * sale ordenado de menor a mayor. Complejidad O(n).
     */
    public void inorden() {
        if (estaVacio()) {
            System.out.println("El árbol está vacío");
            return;
        }
        inordenRec(raiz);
        System.out.println();
    }

    /**
     * Método recursivo de apoyo del inorden.
     *
     * @param actual nodo que se está visitando
     */
    private void inordenRec(NodoArbol<T> actual) {
        if (actual == null) {
            return;
        }
        inordenRec(actual.izquierdo);
        System.out.print(actual.dato + " ");
        inordenRec(actual.derecho);
    }

    /**
     * Imprime los datos en preorden (nodo, izquierda, derecha).
     * Complejidad O(n).
     */
    public void preorden() {
        if (estaVacio()) {
            System.out.println("El árbol está vacío");
            return;
        }
        preordenRec(raiz);
        System.out.println();
    }

    /**
     * Método recursivo de apoyo del preorden.
     *
     * @param actual nodo que se está visitando
     */
    private void preordenRec(NodoArbol<T> actual) {
        if (actual == null) {
            return;
        }
        System.out.print(actual.dato + " ");
        preordenRec(actual.izquierdo);
        preordenRec(actual.derecho);
    }

    /**
     * Imprime los datos en posorden (izquierda, derecha, nodo).
     * Complejidad O(n).
     */
    public void posorden() {
        if (estaVacio()) {
            System.out.println("El árbol está vacío");
            return;
        }
        posordenRec(raiz);
        System.out.println();
    }

    /**
     * Método recursivo de apoyo del posorden.
     *
     * @param actual nodo que se está visitando
     */
    private void posordenRec(NodoArbol<T> actual) {
        if (actual == null) {
            return;
        }
        posordenRec(actual.izquierdo);
        posordenRec(actual.derecho);
        System.out.print(actual.dato + " ");
    }

    /**
     * Elimina un dato del árbol, respetando la regla del BST. Maneja tres
     * casos: nodo sin hijos, con un hijo y con dos hijos (se reemplaza por su
     * sucesor). Complejidad O(log n) en promedio.
     *
     * @param dato dato que se quiere eliminar
     * @return true si lo eliminó, false si no estaba o es null
     */
    public boolean eliminar(T dato) {
        if (dato == null) {
            return false;
        }
        int tamanoAntes = tamano;
        raiz = eliminarRec(raiz, dato);
        return tamano < tamanoAntes;
    }

    /**
     * Método recursivo de apoyo de eliminar.
     *
     * @param actual nodo que se está revisando
     * @param dato   dato que se quiere eliminar
     * @return el nodo que debe quedar en ese lugar del árbol
     */
    private NodoArbol<T> eliminarRec(NodoArbol<T> actual, T dato) {
        if (actual == null) {
            return null;
        }
        int comparacion = dato.compareTo(actual.dato);
        if (comparacion < 0) {
            actual.izquierdo = eliminarRec(actual.izquierdo, dato);
        } else if (comparacion > 0) {
            actual.derecho = eliminarRec(actual.derecho, dato);
        } else {
            if (actual.izquierdo == null && actual.derecho == null) {
                tamano--;
                return null;
            }
            if (actual.izquierdo == null) {
                tamano--;
                return actual.derecho;
            }
            if (actual.derecho == null) {
                tamano--;
                return actual.izquierdo;
            }
            NodoArbol<T> sucesor = actual.derecho;
            while (sucesor.izquierdo != null) {
                sucesor = sucesor.izquierdo;
            }
            actual.dato = sucesor.dato;
            actual.derecho = eliminarRec(actual.derecho, sucesor.dato);
        }
        return actual;
    }

    /**
     * Imprime los mejores datos, del mayor al menor, hasta un límite. Se usa
     * para mostrar el ranking de puntajes. Complejidad O(h + k), con h la
     * altura del árbol y k el límite.
     *
     * @param limite cantidad máxima de datos que se imprimen
     */
    public void mostrarMejores(int limite) {
        if (estaVacio()) {
            System.out.println("Aún no hay puntajes");
            return;
        }
        inversoRec(raiz, limite, 0);
    }

    /**
     * Método recursivo de apoyo de mostrarMejores: recorre en orden inverso
     * (derecha, nodo, izquierda) y lleva la cuenta de lo impreso.
     *
     * @param actual   nodo que se está visitando
     * @param limite   cantidad máxima de datos a imprimir
     * @param impresos cuántos datos se han impreso hasta ahora
     * @return cuántos datos van impresos después de visitar este nodo
     */
    private int inversoRec(NodoArbol<T> actual, int limite, int impresos) {
        if (actual == null || impresos >= limite) {
            return impresos;
        }
        impresos = inversoRec(actual.derecho, limite, impresos);
        if (impresos < limite) {
            impresos++;
            System.out.println(impresos + ". " + actual.dato);
        }
        return inversoRec(actual.izquierdo, limite, impresos);
    }

    /**
     * Indica si el árbol no tiene nodos.
     *
     * @return true si está vacío, false si tiene datos
     */
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Devuelve cuántos nodos tiene el árbol.
     *
     * @return cantidad de nodos
     */
    public int tamano() {
        return tamano;
    }
}
