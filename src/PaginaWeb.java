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

        this.url = url.trim();
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

        if (hora.length() != 8
                || hora.charAt(2) != ':'
                || hora.charAt(5) != ':'
                || !esDigitoAscii(hora.charAt(0))
                || !esDigitoAscii(hora.charAt(1))
                || !esDigitoAscii(hora.charAt(3))
                || !esDigitoAscii(hora.charAt(4))
                || !esDigitoAscii(hora.charAt(6))
                || !esDigitoAscii(hora.charAt(7))) {
            throw new IllegalArgumentException("La hora debe tener formato HH:mm:ss");
        }

        int horas = (hora.charAt(0) - '0') * 10 + (hora.charAt(1) - '0');
        int minutos = (hora.charAt(3) - '0') * 10 + (hora.charAt(4) - '0');
        int segundos = (hora.charAt(6) - '0') * 10 + (hora.charAt(7) - '0');

        if (horas > 23 || minutos > 59 || segundos > 59) {
            throw new IllegalArgumentException("La hora está fuera de rango");
        }

        return horas * 3600 + minutos * 60 + segundos;
    }

    private static boolean esDigitoAscii(char caracter) {
        return caracter >= '0' && caracter <= '9';
    }
}
