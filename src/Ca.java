
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

/**
 * Calculadora básica con Java y HTML.
 */
public class Ca {

    /** Variables globales. */
    static int a, b;
    // El resultado
    static double r;

    // Los operaciones y los textos
    static String op, tx;

    // Los parametros
    static String[] ps;

    // La respuesta como bytes
    static byte[] bt;

    // El servidor
    static HttpServer sv;

    /**
     * Divide dos números.
     * Comprueba que el divisor no sea cero.
     * @return Resultado de la división.
     */
    static double dv() {
        return (double) a / b;
    }

    /** Suma dos números. */
    static double sm() {
        return (double) a + b;
    }

    /** Resta dos números. */
    static double rs() {
        return (double) a - b;
    }

    /** Multiplica dos números. */
    static double mt() {
        return (double) a * b;
    }

    /**
     * Inicia el servidor de la calculadora.
     * @param ar Argumentos del programa.
     * @throws Exception Si ocurre un error.
     */
    public static void main(String[] ar) throws Exception {

        // Crea el servidor local.
        sv = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        /** Muestra el formulario HTML. */
        sv.createContext("/", ex -> {
            bt = Files.readAllBytes(Path.of("frontend","index.html"));
            ex.sendResponseHeaders(200, bt.length);
            ex.getResponseBody().write(bt);
            ex.close();
        });

        /** Recibe los números y calcula. */
        sv.createContext("/calc", ex -> {

            try {
                ps = ex.getRequestURI()
                        .getQuery().split("&");

                a = Integer.parseInt(ps[0].split("=")[1]);
                op = ps[1].split("=")[1];
                b = Integer.parseInt(ps[2].split("=")[1]);

                /** Comprueba los límites. */
                if (a < -99999 || a > 99999 ||
                        b < -99999 || b > 99999) {

                    tx = "Error: números fuera del rango.";

                } else if (op.equals("dividir") && b == 0) {

                    tx = "Error: no se puede dividir entre cero.";

                } else {

                    /** Realiza la operación. */
                    r = switch (op) {
                        case "sumar" -> sm();
                        case "restar" -> rs();
                        case "multiplicar" -> mt();
                        case "dividir" -> dv();
                        default -> Double.NaN;
                    };

                    tx = "Resultado: " + r;
                }

            } catch (RuntimeException er) {
                tx = "Error: introduce números enteros válidos.";
            }

            /** Envía el resultado al navegador. */

            String html = Files.readString(
                    Path.of("frontend", "index.html"),
                    StandardCharsets.UTF_8
            );

// Replace the placeholder with the calculated result
            html = html.replace(
                    "Aquí aparecerá el resultado.",
                    tx
            );

// Send the HTML page to the browser
            bt = html.getBytes(StandardCharsets.UTF_8);

            ex.getResponseHeaders().set(
                    "Content-Type",
                    "text/html; charset=UTF-8"
            );

            ex.sendResponseHeaders(200, bt.length);
            ex.getResponseBody().write(bt);
            ex.close();

        });

        /** Inicia el servidor. */
        sv.start();
        System.out.println("Servidor iniciado correctamente.");
        System.out.println("Abre http://localhost:8080");
    }
}
