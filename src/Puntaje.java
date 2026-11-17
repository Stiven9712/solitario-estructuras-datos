public class Puntaje implements Comparable<Puntaje>{
    private String nombre;
    private int puntos;

    public Puntaje(String nombre,int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public int compareTo(Puntaje otro) {
        if (puntos != otro.puntos) {
            return Integer.compare(puntos, otro.puntos);
        }
        return nombre.compareTo(otro.nombre);
    }
    @Override
    public String toString() {
        return nombre + " " + puntos;
    }
}
