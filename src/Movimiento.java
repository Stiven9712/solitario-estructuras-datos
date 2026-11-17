public class Movimiento {

    public enum Tipo {
        ROBAR, COLUMNA_A_COLUMNA, COLUMNA_A_FUNDACION, DESCARTE_A_COLUMNA
    }

    private Tipo tipo;
    private int origen;
    private int destino;
    private int cantidad;
    private boolean volteo;

    public Movimiento(Tipo tipo, int origen, int destino, int cantidad, boolean volteo) {
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
        this.volteo = volteo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean isVolteo() {
        return volteo;
    }
}
