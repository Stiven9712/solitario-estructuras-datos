import java.util.Scanner;

/**
 * Lee datos del teclado de forma segura: si el usuario escribe algo inválido,
 * vuelve a preguntar en vez de cerrar el programa. El Scanner solo sirve para
 * leer, no guarda datos.
 */
public class Entrada {
    private static final Scanner teclado = new Scanner(System.in);

    /**
     * Pide un número entero dentro de un rango y repite la pregunta hasta que
     * el usuario escriba un valor válido.
     *
     * @param mensaje texto que se muestra al usuario
     * @param min     valor mínimo permitido
     * @param max     valor máximo permitido
     * @return un entero entre min y max
     */
    public static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Escribe un número entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un número válido");
            }
        }
    }

    /**
     * Pide un texto, por ejemplo el nombre del jugador.
     *
     * @param mensaje texto que se le muestra al usuario
     * @return el texto escrito, o "Anónimo" si no escribió nada
     */
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = teclado.nextLine().trim();
        if (texto.isEmpty()) {
            texto = "Anónimo";
        }
        return texto;
    }
}