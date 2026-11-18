
import java.util.Random;

/**
 * Fabrica la baraja de 52 cartas y la mezcla
 */
public class Baraja {

    /**
     * Crea las 52 cartas (4 palos de 13 valores) y las baraja con el método
     * de Fisher-Yates. Complejidad 0(n). El arreglo es solo un paso
     * intermedio: después las cartas pasan a las extructuras del juego.
     *
     * @return un arreglo con las 52 cartas ya mezcladas
     */
    public static Carta[] crearBarajada() {
        Carta [] cartas = new Carta[52];
        int indice = 0;
        for (Palo palo: Palo.values()) {
            for(int valor =1; valor <= 13; valor++) {
                cartas[indice] = new Carta(palo, valor);
                indice++;
            }
        }
        Random azar = new Random();
        for (int i = cartas.length - 1; i  > 0;i--) {
            int j = azar.nextInt(i+1);
            Carta temporal = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temporal;
        }
        return cartas;
    }

}