public class Solitario {
    private ListaDoble<Carta>[] columnas;
    private Pila<Carta>[] fundaciones;
    private Cola<Carta> mazo;
    private ListaCircular<Carta> descarte;
    private int puntaje;
    private Pila<Movimiento> historial;

    @SuppressWarnings("unchecked")
    public Solitario() {
        columnas = new ListaDoble[7];
        for (int i = 0; i < 7; i++) {
            columnas [i] = new ListaDoble<>();
        }
        fundaciones = new Pila[4];
        for (int i = 0; i < 4; i++) {
            fundaciones[i] = new Pila<>();
        }
        mazo = new Cola<>();
        descarte = new ListaCircular<>();
        puntaje = 0;
        historial = new Pila<>();
    }

    public void repartir() {
        Carta[] cartas = Baraja.crearBarajada();
        int indice = 0;
        for (int col = 0; col < 7; col++) {
            for (int fila = 0; fila <= col; fila++) {
                Carta carta = cartas[indice];
                indice++;
                if (fila == col) {
                    carta.setBocaArriba(true);
                }
                columnas[col].insertarFinal(carta);
            }
        }
        while (indice < cartas.length) {
            mazo.encolar(cartas[indice]);
            indice++;
        }
    }

    public void mostrar() {
        System.out.println("Puntaje: " + puntaje);

        System.out.print("Fundaciones: ");
        for (int i = 0; i < 4; i++) {
            Carta tope = fundaciones[i].verTope();
            String texto = "--";
            if (tope != null) {
                texto = tope.toString();
            }
            System.out.print("[" + texto + "] ");
        }
        System.out.println();

        Carta visible = descarte.verUltimo();
        String textoDescarte = "--";
        if (visible != null) {
            textoDescarte = visible.toString();
        }
        System.out.println("Mazo: " + mazo.tamano() + " cartas | Descarte: " + descarte.tamano() + " cartas | Carta visible: " + textoDescarte);
        System.out.println();

        for (int c = 0; c < 7; c++) {
            System.out.printf("%-5s", "C" + (c + 1));
        }
        System.out.println();

        int maxFilas = 0;
        for (int c = 0; c < 7; c++) {
            if (columnas[c].tamano() > maxFilas) {
                maxFilas = columnas[c].tamano();
            }
        }

        for (int fila = 0; fila < maxFilas; fila++) {
            for (int c = 0; c < 7; c++) {
                Carta carta = columnas[c].obtener(fila);
                String texto = "";
                if (carta != null) {
                    if (carta.isBocaArriba()) {
                        texto = carta.toString();
                    } else {
                        texto = "##";
                    }
                }
                System.out.printf("%-5s", texto);
            }
            System.out.println();
        }
    }

    public boolean robar() {
        if (mazo.estaVacia()) {
            if (!reciclar()) {
                return false;
            }
            historial = new Pila<>();
        }
        Carta carta = mazo.desencolar();
        carta.setBocaArriba(true);
        descarte.insertarFinal(carta);
        historial.apilar(new Movimiento(Movimiento.Tipo.ROBAR, 0, 0, 1, false));
        return true;
    }

    public boolean reciclar() {
        if (!mazo.estaVacia() || descarte.estaVacia()) {
            return false;
        }
        while (!descarte.estaVacia()) {
            Carta carta = descarte.eliminarInicio();
            carta.setBocaArriba(false);
            mazo.encolar(carta);
        }
        return true;
    }

    private boolean puedeMover(int origen, int posicion, int destino) {
        if (origen < 0 || origen > 6 || destino < 0 || destino > 6 || origen == destino) {
            return false;
        }
        Carta movida = columnas[origen].obtener(posicion);
        if (movida == null || !movida.isBocaArriba()) {
            return false;
        }
        Carta base = columnas[destino].obtener(columnas[destino].tamano() - 1);
        if (base == null) {
            return movida.getValor() == 13;
        }
        return base.esRoja() != movida.esRoja() && base.getValor() == movida.getValor() + 1;
    }

    public boolean mover(int origen, int posicion, int destino) {
        if (!puedeMover(origen, posicion, destino)) {
            return false;
        }
        int cantidad = columnas[origen].tamano() - posicion;
        Pila<Carta> temporal = new Pila<>();
        for (int i = 0; i < cantidad; i++) {
            temporal.apilar(columnas[origen].eliminarFinal());
        }
        while (!temporal.estaVacia()) {
            columnas[destino].insertarFinal(temporal.desapilar());
        }
        int n = columnas[origen].tamano();
        boolean volteo = false;
        if (n > 0) {
            Carta nueva = columnas[origen].obtener(n - 1);
            if (!nueva.isBocaArriba()) {
                nueva.setBocaArriba(true);
                volteo = true;
            }
        }
        historial.apilar(new Movimiento(Movimiento.Tipo.COLUMNA_A_COLUMNA, origen, destino, cantidad, volteo));
        return true;
    }

    public void colocarCarta(int columna, Carta carta, boolean bocaArriba) {
        carta.setBocaArriba(bocaArriba);
        columnas[columna].insertarFinal(carta);
    }

    public void colocarEnMazo(Carta carta) {
        carta.setBocaArriba(false);
        mazo.encolar(carta);
    }

    public boolean moverAFundacion(int columna) {
        if (columna < 0 || columna > 6) {
            return false;
        }
        int n = columnas[columna].tamano();
        if (n == 0){
            return false;
        }
        Carta carta = columnas[columna].obtener(n-1);
        Pila <Carta> pila = fundaciones[carta.getPalo().ordinal()];
        Carta tope = pila.verTope();
        boolean valida;
        if (tope == null) {
            valida = carta.getValor() == 1;
        } else {
            valida = carta.getValor() == tope.getValor() +1;
        }
        if (!valida) {
            return false;
        }
        columnas[columna].eliminarFinal();
        pila.apilar(carta);
        int restantes = columnas[columna].tamano();
        boolean volteo = false;
        if (restantes > 0) {
            Carta nueva = columnas[columna].obtener(restantes - 1);
            if (!nueva.isBocaArriba()) {
                nueva.setBocaArriba(true);
                volteo = true;
            }
        }
        puntaje += 10;
        historial.apilar(new Movimiento(Movimiento.Tipo.COLUMNA_A_FUNDACION, columna, carta.getPalo().ordinal(), 1, volteo));
        return true;
    }
    public boolean moverDescarteAColumna(int destino) {
        if (destino < 0|| destino > 6) {
            return false;
        }
        Carta carta = descarte.verUltimo();
        if (carta == null) {
            return false;
        }
        Carta base = columnas[destino].obtener(columnas[destino].tamano()-1);
        boolean valida;
        if (base == null) {
            valida = carta.getValor() == 13;
        } else {
            valida = base.esRoja() != carta.esRoja() && base.getValor() == carta.getValor() + 1;
        }
        if (!valida) {
            return false;
        }
        descarte.eliminar(carta);
        columnas[destino].insertarFinal(carta);
        historial.apilar(new Movimiento(Movimiento.Tipo.DESCARTE_A_COLUMNA, 0, destino, 1, false));
        return true;
    }

    public boolean deshacer() {
        if (historial.estaVacia()) {
            return false;
        }
        Movimiento m = historial.desapilar();
        switch (m.getTipo()) {
            case ROBAR -> deshacerRobar();
            case DESCARTE_A_COLUMNA ->  deshacerDesacarteAColumna(m);
            case COLUMNA_A_FUNDACION -> deshacerColumnaAFunfacion(m);
            case COLUMNA_A_COLUMNA -> deshacerColumnaAColumna(m);
        }
        return true;
    }
    private void deshacerDesacarteAColumna(Movimiento m) {
        Carta carta = columnas[m.getDestino()].eliminarFinal();
        descarte.insertarFinal(carta);
    }

    private void deshacerRobar() {
        Carta carta = descarte.eliminarFinal();
        carta.setBocaArriba(false);
        mazo.encolarFrente(carta);
    }
    private void deshacerColumnaAFunfacion(Movimiento m) {
        Carta carta = fundaciones[m.getDestino()].desapilar();
        int n = columnas[m.getOrigen()].tamano();
        if (m.isVolteo() && n > 0) {
            columnas[m.getOrigen()].obtener(n-1).setBocaArriba(false);
        }
        columnas[m.getOrigen()].insertarFinal(carta);
        puntaje -=10;
    }

    private void deshacerColumnaAColumna(Movimiento m) {
        Pila<Carta> temporal = new Pila<>();
        for (int i =0; i < m.getCantidad();i++) {
            temporal.apilar(columnas[m.getDestino()].eliminarFinal());
        }
        int n = columnas[m.getOrigen()].tamano();
        if (m.isVolteo() && n > 0) {
            columnas[m.getOrigen()].obtener(n-1).setBocaArriba(false);
        }
        while (!temporal.estaVacia()) {
            columnas[m.getOrigen()].insertarFinal(temporal.desapilar());
        }
    }

    public boolean haGanado() {
        for (int i = 0; i < 4;i++) {
            if (fundaciones[i].tamano() < 13) {
                return false;
            }
        }
        return true;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
