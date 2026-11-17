import java.util.Random;
public class Baraja {

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
