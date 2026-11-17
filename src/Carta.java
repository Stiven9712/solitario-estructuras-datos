public class Carta {
    private Palo palo;
    private int valor;
    private boolean bocaArriba;

    public Carta(Palo palo, int valor) {
        this.palo = palo;
        this.valor = valor;
        this.bocaArriba = false;
    }

    public Palo getPalo() {
        return palo;
    }

    public int getValor() {
        return valor;
    }

    public boolean isBocaArriba() {
        return bocaArriba;
    }

    public void setBocaArriba(boolean bocaArriba) {
        this.bocaArriba = bocaArriba;
    }

    public boolean esRoja() {
        return palo == Palo.CORAZONES || palo == Palo.DIAMANTES;
    }
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
