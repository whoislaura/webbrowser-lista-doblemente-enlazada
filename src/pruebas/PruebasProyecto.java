package pruebas;

import estructura.LinkedList;
import historial.HistorialNavegacion;
import modelo.PaginaWeb;

public class PruebasProyecto {
    private static int pruebasEjecutadas;

    public static void main(String[] args) {
        probarPaginaWeb();
        probarLinkedList();
        probarHistorialRecienteYBusqueda();
        probarEliminacionPorUrl();
        probarVaciadoPorHora();
        probarCasosNegativos();
        probarHistorialVacio();

        System.out.println("Todas las " + pruebasEjecutadas + " pruebas pasaron.");
    }

    private static void probarPaginaWeb() {
        PaginaWeb pagina = new PaginaWeb(
                "https://ejemplo.com", "Página de ejemplo", "14:35:08");

        verificar(pagina.getHoraVisitaSegundos() == 52508,
                "14:35:08 debe convertirse a 52508 segundos");
        verificar("14:35:08".equals(pagina.getHoraVisita()),
                "La hora debe mostrarse de nuevo en formato HH:mm:ss");

        PaginaWeb urlConEspacios = new PaginaWeb(
                "  https://espacios.example  ", "Página de ejemplo", "14:35:08");
        verificar("https://espacios.example".equals(urlConEspacios.getUrl()),
                "La URL debe guardarse sin espacios externos");
    }

    private static void probarLinkedList() {
        LinkedList<String> lista = new LinkedList<>();
        lista.add("B");
        lista.add("D");
        lista.add("A", 0);
        lista.add("C", 2);
        lista.add("E", 4);

        verificar(lista.size() == 5, "La lista debe tener cinco elementos");
        verificarElemento(lista, 0, "A", "Inserción al principio");
        verificarElemento(lista, 1, "B", "Contenido en índice 1");
        verificarElemento(lista, 2, "C", "Inserción intermedia");
        verificarElemento(lista, 3, "D", "Contenido en índice 3");
        verificarElemento(lista, 4, "E", "Inserción al final");

        verificar("A".equals(lista.remove(0)),
                "Debe eliminar y devolver el primer elemento");
        verificar(lista.size() == 4,
                "El tamaño debe actualizarse después de eliminar el primero");
        verificarElemento(lista, 0, "B", "Enlaces válidos después de eliminar el primero");
        verificarElemento(lista, 1, "C", "Enlaces válidos después de eliminar el primero");
        verificarElemento(lista, 2, "D", "Enlaces válidos después de eliminar el primero");
        verificarElemento(lista, 3, "E", "Enlaces válidos después de eliminar el primero");

        verificar("C".equals(lista.remove(1)),
                "Debe eliminar y devolver un elemento intermedio");
        verificar(lista.size() == 3,
                "El tamaño debe actualizarse después de eliminar el intermedio");
        verificarElemento(lista, 0, "B", "Enlaces válidos después de eliminar el intermedio");
        verificarElemento(lista, 1, "D", "Enlaces válidos después de eliminar el intermedio");
        verificarElemento(lista, 2, "E", "Enlaces válidos después de eliminar el intermedio");

        verificar("E".equals(lista.remove(2)),
                "Debe eliminar y devolver el último elemento");
        verificar(lista.size() == 2,
                "El tamaño debe actualizarse después de eliminar el último");
        verificarElemento(lista, 0, "B", "Enlaces válidos después de eliminar el último");
        verificarElemento(lista, 1, "D", "Enlaces válidos después de eliminar el último");

        lista.add("F");
        verificar(lista.size() == 3, "Debe poder agregarse un elemento al final");
        verificarElemento(lista, 2, "F", "El nuevo último elemento debe ser accesible");

        LinkedList<String> listaUnitaria = new LinkedList<>();
        listaUnitaria.add("único");
        verificar("único".equals(listaUnitaria.remove(0)),
                "Debe poder eliminarse el único elemento");
        verificar(listaUnitaria.isEmpty(),
                "La lista debe quedar vacía al eliminar su único elemento");
        listaUnitaria.add("nuevo");
        verificarElemento(listaUnitaria, 0, "nuevo",
                "La lista debe poder reutilizarse después de quedar vacía");
    }

    private static void probarHistorialRecienteYBusqueda() {
        HistorialNavegacion historial = new HistorialNavegacion();
        PaginaWeb antigua = new PaginaWeb(
                "https://java.example", "Java básico", "08:00:00");
        PaginaWeb intermedia = new PaginaWeb(
                "https://web.example", "Historia de la web", "10:00:00");
        PaginaWeb reciente = new PaginaWeb(
                "https://java.example", "JAVA avanzado", "14:00:00");

        historial.RegistrarVisita(reciente);
        historial.RegistrarVisita(antigua);
        historial.RegistrarVisita(intermedia);

        LinkedList<PaginaWeb> recientes = historial.MostrarHistorialReciente();
        verificar(recientes.size() == 3,
                "El historial reciente debe contener las tres visitas");
        verificar(recientes.get(0) == reciente,
                "La visita más reciente debe aparecer primero");
        verificar(recientes.get(1) == intermedia,
                "La visita intermedia debe aparecer en segundo lugar");
        verificar(recientes.get(2) == antigua,
                "La visita más antigua debe aparecer al final");

        LinkedList<String> coincidencias =
                historial.BuscarPorPalabraClave("jAvA");
        verificar(coincidencias.size() == 2,
                "La búsqueda debe encontrar coincidencias sin distinguir mayúsculas");
        verificar("https://java.example".equals(coincidencias.get(0)),
                "La búsqueda debe ordenar primero la URL más reciente");
        verificar("https://java.example".equals(coincidencias.get(1)),
                "La búsqueda debe incluir la visita antigua coincidente");

        verificar(historial.BuscarPorPalabraClave("inexistente").isEmpty(),
                "Una búsqueda sin coincidencias debe devolver una lista vacía");
    }

    private static void probarEliminacionPorUrl() {
        HistorialNavegacion historial = new HistorialNavegacion();
        PaginaWeb primeraVisita = new PaginaWeb(
                "https://repetida.example", "Visita antigua", "08:00:00");
        PaginaWeb otraVisita = new PaginaWeb(
                "https://otra.example", "Otra página", "10:00:00");
        PaginaWeb segundaVisita = new PaginaWeb(
                "https://repetida.example", "Visita reciente", "14:00:00");

        historial.RegistrarVisita(segundaVisita);
        historial.RegistrarVisita(primeraVisita);
        historial.RegistrarVisita(otraVisita);

        verificar(historial.EliminarPaginaPorUrl("https://repetida.example"),
                "Debe encontrarse y eliminarse una URL existente");

        LinkedList<PaginaWeb> restantes = historial.MostrarHistorialReciente();
        verificar(restantes.size() == 2,
                "Debe eliminarse solo una visita de la URL repetida");
        verificar(restantes.get(0) == otraVisita,
                "La visita intermedia debe permanecer en el historial");
        verificar(restantes.get(1) == primeraVisita,
                "La visita antigua repetida debe permanecer en el historial");

        verificar(!historial.EliminarPaginaPorUrl("https://no-existe.example"),
                "Eliminar una URL inexistente debe devolver false");

        PaginaWeb urlConEspacios = new PaginaWeb(
                "  https://espacios.example  ", "Página con espacios", "12:00:00");
        historial.RegistrarVisita(urlConEspacios);
        verificar(historial.EliminarPaginaPorUrl("  https://espacios.example  "),
                "Eliminar debe recortar espacios externos de la URL ingresada");
    }

    private static void probarVaciadoPorHora() {
        HistorialNavegacion historial = new HistorialNavegacion();
        PaginaWeb anterior = new PaginaWeb(
                "https://antes.example", "Antes del límite", "09:59:59");
        PaginaWeb igual = new PaginaWeb(
                "https://igual.example", "En el límite", "10:00:00");
        PaginaWeb posterior = new PaginaWeb(
                "https://despues.example", "Después del límite", "10:00:01");

        historial.RegistrarVisita(posterior);
        historial.RegistrarVisita(anterior);
        historial.RegistrarVisita(igual);

        verificar(historial.VaciarHistorialAntiguo("10:00:00") == 1,
                "Debe eliminar solo la visita anterior al límite");

        LinkedList<PaginaWeb> restantes = historial.MostrarHistorialReciente();
        verificar(restantes.size() == 2,
                "Deben conservarse las visitas iguales y posteriores al límite");
        verificar(restantes.get(0) == posterior,
                "La visita posterior al límite debe conservarse");
        verificar(restantes.get(1) == igual,
                "La visita igual al límite debe conservarse");
    }

    private static void probarCasosNegativos() {
        LinkedList<String> lista = new LinkedList<>();

        verificarExcepcion(IndexOutOfBoundsException.class,
                () -> lista.get(-1), "get con índice negativo");
        verificarExcepcion(IndexOutOfBoundsException.class,
                () -> lista.get(0), "get en lista vacía");
        verificarExcepcion(IndexOutOfBoundsException.class,
                () -> lista.remove(0), "remove en lista vacía");
        verificarExcepcion(IndexOutOfBoundsException.class,
                () -> lista.add("inválido", -1), "add con índice negativo");
        verificarExcepcion(IndexOutOfBoundsException.class,
                () -> lista.add("inválido", 1), "add con índice mayor al tamaño");

        lista.add("valor");
        verificarExcepcion(IndexOutOfBoundsException.class,
                () -> lista.get(1), "get en el índice igual al tamaño");
        verificarExcepcion(IndexOutOfBoundsException.class,
                () -> lista.remove(1), "remove en el índice igual al tamaño");

        HistorialNavegacion historial = new HistorialNavegacion();
        verificarExcepcion(IllegalArgumentException.class,
                () -> historial.RegistrarVisita(null), "registrar página nula");
        verificarExcepcion(IllegalArgumentException.class,
                () -> historial.EliminarPaginaPorUrl(null), "eliminar con URL nula");
        verificarExcepcion(IllegalArgumentException.class,
                () -> historial.EliminarPaginaPorUrl(""), "eliminar con URL vacía");
        verificarExcepcion(IllegalArgumentException.class,
                () -> historial.BuscarPorPalabraClave(null), "buscar palabra nula");
        verificarExcepcion(IllegalArgumentException.class,
                () -> historial.BuscarPorPalabraClave(""), "buscar palabra vacía");

        verificarExcepcion(IllegalArgumentException.class,
                () -> new PaginaWeb("https://ejemplo.com", "Ejemplo", "26:00:00"),
                "crear página con hora 26:00:00");
        verificarExcepcion(IllegalArgumentException.class,
                () -> new PaginaWeb("https://ejemplo.com", "Ejemplo", "hora"),
                "crear página con hora mal formada");
        verificarExcepcion(IllegalArgumentException.class,
                () -> PaginaWeb.convertirHoraASegundos("+1:00:00"),
                "rechazar signo más en la hora");
        verificarExcepcion(IllegalArgumentException.class,
                () -> PaginaWeb.convertirHoraASegundos("   "),
                "rechazar hora vacía o con espacios");
        verificarExcepcion(IllegalArgumentException.class,
                () -> PaginaWeb.convertirHoraASegundos("12:60:00"),
                "convertir minutos fuera de rango");
        verificarExcepcion(IllegalArgumentException.class,
                () -> historial.VaciarHistorialAntiguo("26:00:00"),
                "vaciar con hora límite 26:00:00");
    }

    private static void probarHistorialVacio() {
        HistorialNavegacion historial = new HistorialNavegacion();

        verificar(historial.MostrarHistorialReciente().isEmpty(),
                "Mostrar historial vacío debe devolver una lista vacía");
        verificar(historial.BuscarPorPalabraClave("Java").isEmpty(),
                "Buscar en historial vacío debe devolver una lista vacía");
        verificar(!historial.EliminarPaginaPorUrl("https://no-existe.example"),
                "Eliminar en historial vacío debe devolver false");
        verificar(historial.VaciarHistorialAntiguo("12:00:00") == 0,
                "Vaciar historial vacío debe devolver cero");
    }

    private static void verificarElemento(
            LinkedList<String> lista, int indice, String esperado, String caso) {
        verificar(esperado.equals(lista.get(indice)), caso);
    }

    private static void verificarExcepcion(
            Class<? extends RuntimeException> tipoEsperado,
            Runnable accion,
            String caso) {
        try {
            accion.run();
        } catch (RuntimeException excepcion) {
            verificar(tipoEsperado.isInstance(excepcion),
                    caso + " debe lanzar " + tipoEsperado.getSimpleName());
            return;
        }

        verificar(false, caso + " debía lanzar " + tipoEsperado.getSimpleName());
    }

    private static void verificar(boolean condicion, String caso) {
        if (!condicion) {
            throw new AssertionError("Falló la prueba: " + caso);
        }

        pruebasEjecutadas++;
        System.out.println("[OK] " + caso);
    }
}
