package Algo3.Utilidad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FechaParser {


    public static LocalDateTime fromString(String datosJuntos){
        var fechaYhoraSeparados = datosJuntos.split("T");
        var fechaSeparada = fechaYhoraSeparados[0].split("-");
        LocalDate fecha = LocalDate.of(
                Integer.parseInt(fechaSeparada[0]),
                Integer.parseInt(fechaSeparada[1]),
                Integer.parseInt(fechaSeparada[2])
        );
        var horaSeparada = fechaYhoraSeparados[1].split(":");
        LocalTime hora = LocalTime.of(Integer.parseInt(horaSeparada[0]),
                Integer.parseInt(horaSeparada[1]));
        return LocalDateTime.of(fecha,hora);
    }
}
