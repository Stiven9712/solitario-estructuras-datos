public class ListaCircular <T> {
    private Nodo<T> cabeza;
    private Nodo<T> fin;
    private int tamano;

    public ListaCircular() {
        this.cabeza = null;
        this.fin = null;
        this.tamano = 0;
    }

    public void insertarInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            fin = nuevo;
            nuevo.siguiente = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza =nuevo;
            fin.siguiente = cabeza;
        }
        tamano++;
    }

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
    public T eliminarInicio() {
        if(estaVacia()) {
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

    public T eliminarFinal() {
        if (estaVacia()) {
            return null;
        }
        T dato = fin.dato;
        if (tamano == 1) {
            cabeza = null;
            fin = null;
        } else {
            Nodo <T> actual = cabeza;
            while (actual.siguiente != fin) {
                actual = actual.siguiente;
            }
            fin = actual;
            fin.siguiente = cabeza;
        }
        tamano--;
        return dato;
    }

    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        Nodo <T> anterior = fin;
        Nodo <T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            if (actual.dato.equals(dato)) {
                if ( tamano == 1) {
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
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    public int buscar(T dato) {
        if (estaVacia()) {
            return -1;
        }
        Nodo <T> actual = cabeza;
        for (int i = 0;i < tamano;i++) {
            if (actual.dato.equals(dato)) {
                return i;
            }
            actual = actual.siguiente;
        }
        return -1;
    }

    public void recorrer() {
        if (estaVacia()) {
            System.out.println("La Lista esta Vacia");
            return;
        }
        Nodo <T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            System.out.print(actual.dato + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }

    public T verUltimo() {
        if (estaVacia()) {
            return null;
        }
        return fin.dato;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int tamano() {
        return tamano;
    }
}
