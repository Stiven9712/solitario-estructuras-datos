/*
*Programa principal del Solitario. Muestra el menú, maneja cada
* partida y guarda los puntajes en el árbol de mejores puntajes
*/

/** Ranking de mejores puntajes, un árbol binario de búsqueda que dura mientras el progrmaa esté abierto. */
ArbolBST<Puntaje> ranking = new ArbolBST<>();

/** Muestra el menú principal, en ciclo hasta que el jugador escoja salir. */
void main() {
    int opcion = 0;
    while (opcion != 4) {
        System.out.println();
        System.out.println("=== SOLITARIO ===");
        System.out.println("1. Nueva partida");
        System.out.println("2. Mejores puntajes");
        System.out.println("3. Reglas");
        System.out.println("4. Salir");
        opcion = Entrada.leerEntero("Elige una opción: ", 1, 4);
        switch (opcion) {
            case 1 -> jugarPartida();
            case 2 -> {
                System.out.println("--- Mejores puntajes ---");
                ranking.mostrarMejores(5);
            }
            case 3 -> mostrarReglas();
            case 4 -> System.out.println("¡Hasta pronto!");
        }
    }
}

/** Imprime en pantalla las reglas del juego */
void mostrarReglas() {
    System.out.println("--- REGLAS ---");
    System.out.println("Objetivo: llevar las 52 cartas a las 4 fundaciones, de A a K y por palo.");
    System.out.println("Columnas: solo se apila una carta de color distinto y valor uno menor.");
    System.out.println("Columna vacía: solo acepta un K.");
    System.out.println("Fundaciones: empiezan con el As y suben de uno en uno, del mismo palo.");
    System.out.println("Cada carta que llega a una fundación suma 10 puntos.");
    System.out.println("Puedes robar del mazo, y cuando se acaba, el descarte vuelve al mazo.");
}

/**
 * Maneja una partida completa: reparte, repite el ciclo de jugadas hasta que
 * el jugador gane o abandone, y al final guarda el puntaje en el ranking.
 */
void jugarPartida() {
    Solitario juego = new Solitario();
    juego.repartir();
    boolean abandonar = false;

    while (!juego.haGanado() && !abandonar) {
        System.out.println();
        juego.mostrar();
        System.out.println();
        System.out.println("1. Robar del mazo");
        System.out.println("2. Mover entre columnas");
        System.out.println("3. Mover una carta a la fundación");
        System.out.println("4. Mover la carta del descarte a una columna");
        System.out.println("5. Deshacer la última jugada");
        System.out.println("6. Abandonar la partida");
        int accion = Entrada.leerEntero("Elige una acción: ", 1, 6);
        boolean hecho = true;
        switch (accion) {
            case 1 -> hecho = juego.robar();
            case 2 -> {
                int origen = Entrada.leerEntero("Columna de origen (1-7): ", 1, 7);
                int fila = Entrada.leerEntero("Fila de la carta (1 = la de arriba): ", 1, 52);
                int destino = Entrada.leerEntero("Columna de destino (1-7): ", 1, 7);
                hecho = juego.mover(origen - 1, fila - 1, destino - 1);
            }
            case 3 -> {
                int columna = Entrada.leerEntero("Columna de la carta (1-7): ", 1, 7);
                hecho = juego.moverAFundacion(columna - 1);
            }
            case 4 -> {
                int destino = Entrada.leerEntero("Columna de destino (1-7): ", 1, 7);
                hecho = juego.moverDescarteAColumna(destino - 1);
            }
            case 5 -> hecho = juego.deshacer();
            case 6 -> abandonar = true;
        }
        if (!hecho) {
            System.out.println("No se pudo realizar esa jugada");
        }
    }

    System.out.println();
    juego.mostrar();
    if (juego.haGanado()) {
        System.out.println("¡GANASTE!");
    } else {
        System.out.println("Partida terminada");
    }
    System.out.println("Puntaje final: " + juego.getPuntaje());
    if (juego.getPuntaje() > 0) {
        String nombre = Entrada.leerTexto("Tu nombre para el ranking: ");
        ranking.insertar(new Puntaje(nombre, juego.getPuntaje()));
        System.out.println("Puntaje guardado");
    }
}