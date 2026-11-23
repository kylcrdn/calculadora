
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/** Servidor sencillo para una calculadora de cuatro operaciones. */
public class Calculadora {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.createContext("/", Calculadora::handleRequest);
        server.start();
        System.out.println("Calculadora disponible en http://localhost:8080");
    }

    /** Entrega el formulario y procesa las operaciones enviadas por el navegador. */
    private static void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String html;
        int status = 200;

        if (path.equals("/")) {
            html = Files.readString(Path.of("frontend", "index.html"));
        } else if (path.equals("/calc")) {
            html = Files.readString(Path.of("frontend", "index.html"));
            try {
                Map<String, String> parameters = readParameters(exchange.getRequestURI().getRawQuery());
                int firstNumber = Integer.parseInt(parameters.get("a"));
                int secondNumber = Integer.parseInt(parameters.get("b"));
                String operation = parameters.get("op");

                if (firstNumber < -99999 || firstNumber > 99999 ||
                        secondNumber < -99999 || secondNumber > 99999) {
                    throw new IllegalArgumentException("Números fuera del rango permitido.");
                }

                double result = calculate(firstNumber, secondNumber, operation);
                if (!Double.isFinite(result)) {
                    throw new IllegalArgumentException("El resultado es demasiado grande.");
                }
                html = html.replace("Aquí aparecerá el resultado.", "Resultado: " + result);
            } catch (RuntimeException exception) {
                html = html.replace("Aquí aparecerá el resultado.",
                        "Error: introduce dos enteros válidos y selecciona una operación permitida.");
            }
        } else {
            status = 404;
            html = "<h1>Página no encontrada</h1>";
        }

        byte[] response = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(status, response.length);
        try (var output = exchange.getResponseBody()) {
            output.write(response);
        }
    }

    /** Lee los tres parámetros enviados por el formulario HTML. */
    private static Map<String, String> readParameters(String query) {
        Map<String, String> parameters = new HashMap<>();
        if (query == null) {
            throw new IllegalArgumentException("Faltan datos.");
        }
        for (String part : query.split("&")) {
            String[] pair = part.split("=", 2);
            if (pair.length == 2) {
                parameters.put(URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(pair[1], StandardCharsets.UTF_8));
            }
        }
        return parameters;
    }

    /** Calcula únicamente las operaciones permitidas. */
    private static double calculate(int firstNumber, int secondNumber, String operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Falta la operación.");
        }
        return switch (operation) {
            case "sumar" -> (double) firstNumber + secondNumber;
            case "restar" -> (double) firstNumber - secondNumber;
            case "multiplicar" -> (double) firstNumber * secondNumber;
            case "potencia" -> Math.pow(firstNumber, secondNumber);
            default -> throw new IllegalArgumentException("Operación no permitida.");
        };
    }
}
