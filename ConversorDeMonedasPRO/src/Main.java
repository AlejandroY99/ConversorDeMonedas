import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            ApiMoneda api = new ApiMoneda();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Bienvenido al conversor de monedas");

            System.out.print("Ingresa la moneda original (ej. USD, MXN, EUR): ");
            String monedaOrigen = scanner.next().toUpperCase();

            System.out.print("Ingresa el monto a convertir: ");
            double monto = 0.0;
            try {
                monto = Double.parseDouble(scanner.next());
            } catch (NumberFormatException e) {
                throw new MonedaInvalidaException("Por favor, ingresa un valor numérico válido para el monto.");
            }

            System.out.print("Ingresa la moneda a convertir (ej. USD, MXN, EUR): ");
            String monedaDestino = scanner.next().toUpperCase();

            double resultado = api.convert(monedaOrigen, monedaDestino, monto);
            System.out.printf("El resultado de la conversión es: %.2f %s\n", resultado, monedaDestino);

        } catch (MonedaInvalidaException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
