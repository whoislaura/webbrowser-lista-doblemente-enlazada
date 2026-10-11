public class HistorialNavegacion {
    private final LinkedList<PaginaWeb> paginas;

    public HistorialNavegacion() {
        paginas = new LinkedList<>();
    }

    public void RegistrarVisita(PaginaWeb pagina) {
        if (pagina == null) {
            throw new IllegalArgumentException("La página no puede ser nula");
        }

        paginas.add(pagina);
    }

    public boolean EliminarPaginaPorUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL no puede estar vacía");
        }

        for (int i = paginas.size() - 1; i >= 0; i--) {
            PaginaWeb pagina = paginas.get(i);

            if (pagina.getUrl().equals(url)) {
                paginas.remove(i);
                return true;
            }
        }

        return false;
    }

    public LinkedList<PaginaWeb> MostrarHistorialReciente() {
        LinkedList<PaginaWeb> historialReciente = new LinkedList<>();

        for (int i = paginas.size() - 1; i >= 0; i--) {
            historialReciente.add(paginas.get(i));
        }

        return historialReciente;
    }

    public LinkedList<String> BuscarPorPalabraClave(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            throw new IllegalArgumentException("La palabra no puede estar vacía");
        }

        String palabraBuscada = palabra.trim();
        LinkedList<String> urlsEncontradas = new LinkedList<>();

        for (int i = paginas.size() - 1; i >= 0; i--) {
            PaginaWeb pagina = paginas.get(i);

            if (contieneSinDistinguirMayusculas(
                    pagina.getTituloPagina(), palabraBuscada)) {
                urlsEncontradas.add(pagina.getUrl());
            }
        }

        return urlsEncontradas;
    }

    public int VaciarHistorialAntiguo(String horaLimite) {
        int limiteEnSegundos = PaginaWeb.convertirHoraASegundos(horaLimite);

        int cantidadEliminada = 0;
        int i = 0;

        while (i < paginas.size()) {
            PaginaWeb pagina = paginas.get(i);

            if (pagina.getHoraVisitaSegundos() < limiteEnSegundos) {
                paginas.remove(i);
                cantidadEliminada++;
            } else {
                i++;
            }
        }

        return cantidadEliminada;
    }

    private boolean contieneSinDistinguirMayusculas(
            String texto, String palabra) {
        if (palabra.length() > texto.length()) {
            return false;
        }

        for (int i = 0; i <= texto.length() - palabra.length(); i++) {
            if (texto.regionMatches(true, i, palabra, 0, palabra.length())) {
                return true;
            }
        }

        return false;
    }
}
