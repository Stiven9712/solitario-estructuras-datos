import java.net.PortUnreachableException;

public class ArbolBST <T extends Comparable<T>>{
    private NodoArbol<T> raiz;
    private int tamano;

    public ArbolBST() {
        this.raiz = null;
        this.tamano = 0;
    }
    public boolean insertar(T dato) {
        if (dato == null) {
            return false;
        }
        int tamanoAntes = tamano;
        raiz = insertarRec(raiz, dato);
        return tamano > tamanoAntes;
    }

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

    public void inorden() {
        if (estaVacio()) {
            System.out.println("El arbol esta vacio");
            return;
        }
        inordenRec(raiz);
        System.out.println();
    }
    private void inordenRec(NodoArbol<T> actual) {
        if(actual == null) {
            return;
        }
        inordenRec(actual.izquierdo);
        System.out.print(actual.dato + " ");
        inordenRec(actual.derecho);
    }

    public void preorden() {
        if (estaVacio()) {
            System.out.println("El arbol esta vacio");
            return;
        }
        preordenRec(raiz);
        System.out.println();
    }

    private void preordenRec(NodoArbol<T> actual) {
        if (actual == null) {
            return;
        }
        System.out.print(actual.dato + " ");
        preordenRec(actual.izquierdo);
        preordenRec(actual.derecho);
    }

    public void posorden() {
        if (estaVacio()) {
            System.out.println("El arbol esta vacio");
            return;
        }
        posordenRec(raiz);
        System.out.println();
    }
    private void posordenRec(NodoArbol<T> actual) {
        if (actual == null) {
            return;
        }
        posordenRec(actual.izquierdo);
        posordenRec(actual.derecho);
        System.out.print(actual.dato + " ");
    }

    public boolean eliminar(T dato) {
        if (dato == null) {
            return false;
        }
        int tamanoAntes = tamano;
        raiz = eliminarRec(raiz, dato);
        return tamano < tamanoAntes;
    }

    public void  mostrarMejores(int limite) {
        if (estaVacio()) {
            System.out.println("Aun no hay puntajes");
            return;
        }
        inversoRec(raiz, limite, 0);
    }

    private int inversoRec(NodoArbol<T> actual, int limite, int impresos) {
        if (actual == null ||  impresos >= limite) {
            return impresos;
        }

        impresos = inversoRec(actual.derecho, limite, impresos);
        if (impresos < limite) {
            impresos++;
            System.out.println(impresos + "." + actual.dato);
        }
        return inversoRec(actual.izquierdo, limite, impresos);
    }

    private NodoArbol<T> eliminarRec(NodoArbol<T> actual, T dato) {
        if (actual == null) {
            return null;
        }
        int comparacion = dato.compareTo(actual.dato);
        if(comparacion < 0) {
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
            if(actual.derecho == null) {
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
    public boolean estaVacio() {
        return raiz == null;
    }
    public int tamano() {
        return tamano;
    }


}
