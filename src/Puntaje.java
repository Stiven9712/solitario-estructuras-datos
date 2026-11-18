/**
 * Un puntaje del ranking: el nombre del jugador y sus puntos. Se compara
 * primero por puntos y, si empatan, por nombre, para que el árbol no pierda
 * puntajes iguales de jugadores distintos
 */
public class Puntaje implements Comparable<Puntaje>{
    private String nombre;
    private int puntos;

    /**
     * Crea un puntaje.
     *
     * @param nombre nombre del jugador.
     * @param puntos puntos obtenidos en la partida
     */
    public Puntaje(String nombre,int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    /**
     *
     * @return el nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return los puntos de la partida
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     *Compara este puntaje con otro: primero por puntos y, si son iguales,
     * por nombre. Lo usa el árbol para decidir izquierda o derecha
     *
     * @param otro puntaje con el que se compara
     * @return negativo si este es menor, positivo si es mayor, 0 si son iguales
     */
    @Override
    public int compareTo(Puntaje otro) {
        if (puntos != otro.puntos) {
            return Integer.compare(puntos, otro.puntos);
        }
        return nombre.compareTo(otro.nombre);
    }

    /**
     * @return el puntaje como texto, por ejemplo "Stiven: 120"
     */
    @Override
    public String toString() {
        return nombre + " " + puntos;
    }
}
