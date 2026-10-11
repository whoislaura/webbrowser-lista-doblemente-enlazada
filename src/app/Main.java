package app;

import estructura.LinkedList;
import historial.HistorialNavegacion;
import modelo.PaginaWeb;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HistorialNavegacion historial = new HistorialNavegacion();

        try (Scanner entrada = new Scanner(System.in)) {
            boolean salir = false;

            while (!salir) {
                mostrarMenu();
                int opcion = leerOpcion(entrada);

                switch (opcion) {
                    case 1:
                        registrarVisita(historial, entrada);
                        break;
                    case 2:
                        mostrarHistorial(historial);
                        break;
                    case 3:
                        buscarPagina(historial, entrada);
                        break;
                    case 4:
                        eliminarPagina(historial, entrada);
                        break;
                    case 5:
                        vaciarHistorialAntiguo(historial, entrada);
                        break;
                    case 6:
                        cargarDatosDemostracion(historial);
                        break;
                    case 0:
                        salir = true;
                        System.out.println("Saliendo del navegador...");
                        break;
                    default:
                        System.out.println("Opción inválida. Elige una opción del menú.");
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== HISTORIAL DEL NAVEGADOR ===");
        System.out.println("1. Registrar visita");
        System.out.println("2. Mostrar historial reciente");
        System.out.println("3. Buscar por palabra clave");
        System.out.println("4. Eliminar página por URL");
        System.out.println("5. Vaciar historial anterior a una hora");
        System.out.println("6. Cargar datos de demostración");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static int leerOpcion(Scanner entrada) {
        String texto = entrada.nextLine().trim();

        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarVisita(
            HistorialNavegacion historial, Scanner entrada) {
        try {
            System.out.print("URL: ");
            String url = entrada.nextLine();

            System.out.print("Título de la página: ");
            String titulo = entrada.nextLine();

            System.out.print("Hora de visita (HH:mm:ss): ");
            String hora = entrada.nextLine();

            PaginaWeb pagina = new PaginaWeb(url, titulo, hora);
            historial.RegistrarVisita(pagina);

            System.out.println("Visita registrada.");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar la visita: " + e.getMessage());
        }
    }

    private static void mostrarHistorial(HistorialNavegacion historial) {
        LinkedList<PaginaWeb> paginas = historial.MostrarHistorialReciente();

        if (paginas.isEmpty()) {
            System.out.println("El historial está vacío.");
            return;
        }

        System.out.println("--- De la más reciente a la más antigua ---");
        for (int i = 0; i < paginas.size(); i++) {
            imprimirPagina(paginas.get(i));
        }
    }

    private static void buscarPagina(
            HistorialNavegacion historial, Scanner entrada) {
        System.out.print("Palabra clave del título: ");
        String palabra = entrada.nextLine();

        try {
            LinkedList<String> urls =
                    historial.BuscarPorPalabraClave(palabra);

            if (urls.isEmpty()) {
                System.out.println("No se encontraron páginas con esa palabra.");
                return;
            }

            System.out.println("URL encontradas:");
            for (int i = 0; i < urls.size(); i++) {
                System.out.println("- " + urls.get(i));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Búsqueda inválida: " + e.getMessage());
        }
    }

    private static void eliminarPagina(
            HistorialNavegacion historial, Scanner entrada) {
        System.out.print("URL que deseas eliminar: ");
        String url = entrada.nextLine();

        try {
            if (historial.EliminarPaginaPorUrl(url)) {
                System.out.println("Se eliminó la visita más reciente con esa URL.");
            } else {
                System.out.println("No se encontró esa URL en el historial.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("URL inválida: " + e.getMessage());
        }
    }

    private static void vaciarHistorialAntiguo(
            HistorialNavegacion historial, Scanner entrada) {
        System.out.print("Hora límite (HH:mm:ss): ");
        String horaLimite = entrada.nextLine();

        try {
            int eliminadas =
                    historial.VaciarHistorialAntiguo(horaLimite);

            System.out.println("Visitas eliminadas: " + eliminadas);
        } catch (IllegalArgumentException e) {
            System.out.println("Hora inválida: " + e.getMessage());
        }
    }

    private static void cargarDatosDemostracion(
            HistorialNavegacion historial) {
        historial.RegistrarVisita(new PaginaWeb(
                "https://www.java.com",
                "Información oficial de Java",
                "08:30:00"));

        historial.RegistrarVisita(new PaginaWeb(
                "https://docs.oracle.com/javase/tutorial/",
                "Tutorial de Java",
                "10:15:00"));

        historial.RegistrarVisita(new PaginaWeb(
                "https://es.wikipedia.org/wiki/Internet",
                "Historia de la web",
                "11:45:00"));

        System.out.println("Se añadieron tres visitas de demostración.");
    }

    private static void imprimirPagina(PaginaWeb pagina) {
        System.out.println("URL: " + pagina.getUrl());
        System.out.println("Título: " + pagina.getTituloPagina());
        System.out.println("Hora: " + pagina.getHoraVisita());
        System.out.println();
    }
}
