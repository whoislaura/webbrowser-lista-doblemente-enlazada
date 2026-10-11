public class PaginaWeb {
    private final String url;
    private final String tituloPagina;
    private final int horaVisitaSegundos;

    public PaginaWeb(String url, String tituloPagina, String horaVisita) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL no puede estar vacía");
        }
        if (tituloPagina == null || tituloPagina.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        this.url = url;
        this.tituloPagina = tituloPagina;
        this.horaVisitaSegundos = convertirHoraASegundos(horaVisita);
    }

    public String getUrl() {
        return url;
    }

    public String getTituloPagina() {
        return tituloPagina;
    }

    public int getHoraVisitaSegundos() {
        return horaVisitaSegundos;
    }

    public String getHoraVisita() {
        int horas = horaVisitaSegundos / 3600;
        int minutos = (horaVisitaSegundos % 3600) / 60;
        int segundos = horaVisitaSegundos % 60;

        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    public static int convertirHoraASegundos(String hora) {
        if (hora == null) {
            throw new IllegalArgumentException("La hora no puede ser nula");
        }

        String[] partes = hora.split(":", -1);
        if (partes.length != 3
                || partes[0].length() != 2
                || partes[1].length() != 2
                || partes[2].length() != 2) {
            throw new IllegalArgumentException("La hora debe tener formato HH:mm:ss");
        }

        try {
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);
            int segundos = Integer.parseInt(partes[2]);

            if (horas < 0 || horas > 23
                    || minutos < 0 || minutos > 59
                    || segundos < 0 || segundos > 59) {
                throw new IllegalArgumentException("La hora está fuera de rango");
            }

            return horas * 3600 + minutos * 60 + segundos;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La hora debe contener números", e);
        }
    }
}
