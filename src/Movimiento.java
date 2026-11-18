/**
 * Describe una jugada ya hecha, con los datos suficientes para poder
 * deshacerla. Se guarda en la pila del historial de Solitario
 */
public class Movimiento {

    /** Tipos de jugada que se pueden hacer*/
    public enum Tipo {
        ROBAR, COLUMNA_A_COLUMNA, COLUMNA_A_FUNDACION, DESCARTE_A_COLUMNA
    }

    private Tipo tipo;
    private int origen;
    private int destino;
    private int cantidad;
    private boolean volteo;

    /**
     * Crea el registro de una jugada.
     *
     * @param tipo     tipo de jugada
     * @param origen   columna de donde salieron las cartas (si aplica)
     * @param destino  columna o fundación a donde llegaron (si aplica)
     * @param cantidad cuántas cartas se movieron
     * @param volteo   true si la jugada volteó una carta del tablero
     */
    public Movimiento(Tipo tipo, int origen, int destino, int cantidad, boolean volteo) {
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
        this.volteo = volteo;
    }

    /**
     * @return el tipo de jugada
     */

    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @return la columna de origen
     */
    public int getOrigen() {
        return origen;
    }

    /**
     * @return la columna o fundacion de destino
     */
    public int getDestino() {
        return destino;
    }

    /**
     * @return cúantas cartas se movieron
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @return true si la jugada volteó una carta, para taparla al deshacer
     */
    public boolean isVolteo() {
        return volteo;
    }
}
