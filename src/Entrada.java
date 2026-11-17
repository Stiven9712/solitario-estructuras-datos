import java.util.Scanner;
public class Entrada {
    private static final Scanner teclado = new Scanner(System.in);

    public static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                return valor;
                }
                System.out.println("Escriba un número entero " + min + " y " + max);
            }catch (NumberFormatException e ) {
                System.out.println("Eso no es un número válido");
            }
        }
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = teclado.nextLine().trim();
        if (texto.isEmpty()) {
            texto = "Anónimo";
        }
        return texto;
    }
}
