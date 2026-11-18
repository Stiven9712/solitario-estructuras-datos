/**
 * Estado y reglas del Solitario (Klondike). Aquí se juntan las cinco
 * estructuras de datos propias:
 * - Lista doble: las 7 columnas del tablero.
 * - Pila: las 4 fundaciones, el historial de jugadas (deshacer) y la pila
 *   temporal que conserva el orden al mover grupos de cartas.
 * - Cola: el mazo de robo.
 * - Lista circular: el descarte, que se recicla al mazo cuando este se acaba.
 * (El ranking de puntajes, que usa el árbol, vive en Main.)
 */
public class Solitario {
    private ListaDoble<Carta>[] columnas;
    private Pila<Carta>[] fundaciones;
    private Cola<Carta> mazo;
    private ListaCircular<Carta> descarte;
    private int puntaje;
    private Pila<Movimiento> historial;

    /**
     * Crea un juego vacío, con las 7 columnas, las 4 funciones, el mazo, el
     * descarte y el historial sin cartas. Para empezar a jugar hay que llamar
     * a repartir()
     */
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

    /**
     * Baraja las 52 cartas y las reparte: la comuna 1 recibe 1 carta, la 2
     * recibe 2, y así hasta la 7 (28). Solo la última de cada columna
     * queda boca arriba. Las 24 restantes se encolan en el mazo
     */
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

    /**
     * Dibuja el tablero en consola: puntaje, fundaciones, mazo, descarte con su
     * carta visible y las 7 columnas (las cartas tapadas salen como ##).
     * Usa obtener () de la lista doble para leer cada carta de cada columna
     */
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

    /**
     * Roba la primera carta del mazo (cola), la voltea y la pone en el descarte
     * (lista circular). Si el mazo está vacío, recicla el descarte; en ese caso
     * el historial se vacia y no se puede deshacer más allá del reciclaje
     *
     * @return true si se puede robar, false si no había cartas en el mazo ni en el descarte
     */
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

    /**
     * Pasa todas las cartas del descarte de vuelta al mazo, tapadas y en el
     * mismo orden. Solo se hace cuando el mazo está vacío y el descarte tiene
     * cartas. Complejidad 0(n).
     *
     * @return true si pudo reciclar, false si no se cumplía las condiciones
     */
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

    /**
     * Valida si un movimiento entre columnas está permitido: la carta dede
     * estar boca arriba y llegar sobre una de color distinto y de valor de unidad mayor,
     * o ser k si la columna destino está vacía
     *
     * @param origen columna de donde sale la carta (0 a 6)
     * @param posicion posición de la carta dentro de la columna de origen
     * @param destino columna a donde va (0 a 6)
     * @return true si el movimiento es válido
     */
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

    /**
     * Mueve una carta y todas las que tiene debajo de una columna a otra. Saca
     * las cartas del final de la lista doble hacia una pila temporal y las
     * vuelve a insertar en el destino: Si la pila invierte el orden, así que las
     * cartas llegan igual que estaban. Si la columna de origen queda con una
     * carta tapada al final, la voltea. Guarda la jugada en el historial
     *
     * @param origen columna de donde sale las cartas (0 a6)
     * @param posicion posición de la primera carta que se mueve
     * @param destino columna a donde va (0 a 6)
     * @return true si movió, false si el movimiento no era valido
     */
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

    /**
     * Pone una carta al final de una columna. Solo se usa oara armar tableros
     * conocidos en las pruebas
     *
     * @param columna  columnas destino (0 a 6)
     * @param carta carta que se coloca
     * @param bocaArriba true para dejarla visible, false para taparla
     */
    public void colocarCarta(int columna, Carta carta, boolean bocaArriba) {
        carta.setBocaArriba(bocaArriba);
        columnas[columna].insertarFinal(carta);
    }

    /**
     * Encola una carta tapada en el mazo. Solo se usa para armar mazos
     * conocidos en las pruebas
     *
     * @param carta carta qye entra en el mazo
     */
    public void colocarEnMazo(Carta carta) {
        carta.setBocaArriba(false);
        mazo.encolar(carta);
    }

    /**
     * Sube ka última carta de una columna a su fundación. La fundación de cada
     * palo es una pila: si esta está vacía solo entra el As, y si no, entra la carta
     * que vale una más que el tope. Suma 10 puntos, voltea la carta que puede
     * al final de la columna y guarda la jugada en el historial
     *
     * @param columna columna de donde sale la carta (0 a 6)
     * @return true si la movió, false si no era válido
     */
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

    /**
     * Mueve la carta visible del descarte al final de una columna, con la misma
     * regla de colores alternados y valor descendente (o un k en columna vacía).
     * Guarda la jugada en el historial
     *
     * @param destino columna a donde va la carta (0 a 6)
     * @return true si la movió, false si no era válido o el descarte estaba vacío
     */
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

    /**
     * Sube la carta visible del descarte a su fundación, con la misma regla de
     * siempre: si la pila está vacía solo entra el As, y si no, la carta que vale
     * uno más que el tope. Suma 10 puntos y guarda la jugada en el historial
     *
     * @return true si la movió, false si no era válido o el descarte estaba vacío
     */
    public boolean moverDescarteAFundacion() {
        Carta carta = descarte.verUltimo();
        if (carta == null) {
            return false;
        }
        Pila<Carta> pila = fundaciones[carta.getPalo().ordinal()];
        Carta tope = pila.verTope();
        boolean valida;
        if (tope == null) {
            valida = carta.getValor() == 1;
        }else{
            valida = carta.getValor() == tope.getValor() +1;
        }
        if (!valida) {
            return false;
        }
        descarte.eliminarFinal();
        pila.apilar(carta);
        puntaje +=10;
        historial.apilar(new Movimiento(Movimiento.Tipo.DESCARTE_A_FUNDACION, 0, carta.getPalo().ordinal(), 1, false));
        return true;
    }

    /**
     * Deshace la última jugada. Saca el último movimiento de la pila del
     * historial (LIFO) y lo revierte según su tipo
     *
     * @return true si deshizo algo, false si el historial estaba vacío
     */
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
            case DESCARTE_A_FUNDACION -> deshacerDescarteAFundacion(m);
        }
        return true;
    }

    /**
     * Revierte un movimiento del descarte a una columna: devuelve la carta al
     * final del descarte
     *
     * @param m jugada que se deshace
     */
    private void deshacerDesacarteAColumna(Movimiento m) {
        Carta carta = columnas[m.getDestino()].eliminarFinal();
        descarte.insertarFinal(carta);
    }

    /**
     * Revierte un movimiento del descarte a una fundación: saca la carta de la
     * fundación, la devuelve al final del descarte y resta 10 puntos
     *
     * @param m jugada que se deshace
     */
    private void deshacerDescarteAFundacion(Movimiento m) {
        Carta carta = fundaciones[m.getDestino()].desapilar();
        descarte.insertarFinal(carta);
        puntaje -=10;
    }

    /**
     * Revierte un robo: saca la carta visible del descarte, la tapa y la
     * devuelve al frente del mazo para que sea otra vez la próxima en robarse
     */
    private void deshacerRobar() {
        Carta carta = descarte.eliminarFinal();
        carta.setBocaArriba(false);
        mazo.encolarFrente(carta);
    }

    /**
     * Revierte un movimiento a una fundación: saca la carta de la fundación,
     * vuelve a tapar la carta qie se había volteado (si hubo volteo), devuleve
     * la carta a su columna y resta los 10 puntos
     *
     * @param m jugada que se deshace
     */
    private void deshacerColumnaAFunfacion(Movimiento m) {
        Carta carta = fundaciones[m.getDestino()].desapilar();
        int n = columnas[m.getOrigen()].tamano();
        if (m.isVolteo() && n > 0) {
            columnas[m.getOrigen()].obtener(n-1).setBocaArriba(false);
        }
        columnas[m.getOrigen()].insertarFinal(carta);
        puntaje -=10;
    }

    /**
     * Revierte un movimiento entre columnas: saca kas cartas del destino con una
     * pila temporal (para conservar su orden), vuelve a tapar la carta que se
     * había volteado en el origen (si hubo volteo) y las devuelve al origen
     *
     * @param m jugada que se deshace
     */
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

    /**
     * Indica si el jugador ganó: las 4 fundaciones tiene las 13 cartas.
     * Complejidad 0(1), porque tamano() de la pila no recorre nada.
     *
     * @return true si las cuatro fundaciones están completas
     */
    public boolean haGanado() {
        for (int i = 0; i < 4;i++) {
            if (fundaciones[i].tamano() < 13) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return puntos acumulados en la partida
     */
    public int getPuntaje() {
        return puntaje;
    }
}
