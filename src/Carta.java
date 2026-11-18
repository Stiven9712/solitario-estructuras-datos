/**
 * Una carta de la baraja, con su palo, su valor (1 a 13) y si está boca arriba
 * Es el dato que guarda las extructuras del juego
 */
public class Carta {
    private Palo palo;
    private int valor;
    private boolean bocaArriba;


    /**
     * Crea una carta, que siempre nace boca abajo
     *
     * @param palo palo de la carta
     * @param valor valor de 1(As) a 13(K)
     */
    public Carta(Palo palo, int valor) {
        this.palo = palo;
        this.valor = valor;
        this.bocaArriba = false;
    }

    /**
     *
     * @return el palo de la carta
     */
    public Palo getPalo() {
        return palo;
    }

    /**
     *
     * @return el valor de la carta, de 1 a 13
     */
    public int getValor() {
        return valor;
    }

    /**
     *
     * @return return true si la carta está boca arriba, false si esta tapada
     */
    public boolean isBocaArriba() {
        return bocaArriba;
    }

    /**
     * Voltea la carta o la tapa
     *
     * @param bocaArriba true para dejarla visible, false para taparla
     */
    public void setBocaArriba(boolean bocaArriba) {
        this.bocaArriba = bocaArriba;
    }

    /**
     * Indica si la carta es roja (corazones o diamantes). Se usa en la regla
     * de colores alternados al mover cartas entre columnas
     *
     * @return true si es rojoa, false si es negra
     */
    public boolean esRoja() {
        return palo == Palo.CORAZONES || palo == Palo.DIAMANTES;
    }

    /**
     * Dos cartas son iguales si tienen el mismo palo y el mismo valor, sin
     * importar si está boca arriba. Lo usa eliminar y buscar de kas listas
     *
     * @param otro   objeto con el que compara
     * @return true si es una carta con el mismo palo y valor
     */
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Carta)) {
            return false;
        }
        Carta c = (Carta) otro;
        return palo == c.palo && valor == c.valor;
    }

    /**
     *
     * Texto de la carta para mostrarla en consola, por ejemplo 7♥ o K♠.
     *
     * @return el valor seguido del simbolo del palo
     */
    @Override
    public String toString() {
        String [] nombres = { "", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String simbolo = "";
        switch (palo) {
            case CORAZONES -> simbolo =  "♥";
            case DIAMANTES -> simbolo = "♦";
            case TREBOLES -> simbolo = "♣";
            case PICAS -> simbolo = "♠";
        }
        return nombres [valor] + simbolo;
    }
}
