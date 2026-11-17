public class ListaDoble<T> {
    private NodoDoble<T> cabeza;
    private NodoDoble<T> fin;
    private int tamano;

    public ListaDoble() {
        this.cabeza = null;
        this.fin = null;
        this.tamano = 0;
    }

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


    public void recorrerAdelante() {
        if (estaVacia()) {
            System.out.println("La  lista esta vacia");
            return;
        }
        NodoDoble<T> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.dato + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }

    public void recorrerAtras() {
        if (estaVacia()) {
            System.out.println("la lista esta vacia");
            return;
        }
        NodoDoble<T> actual = fin;
        while (actual != null) {
            System.out.print(actual.dato + " ");
            actual = actual.anterior;
        }
        System.out.println();
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int tamano() {
        return tamano;
    }


}
