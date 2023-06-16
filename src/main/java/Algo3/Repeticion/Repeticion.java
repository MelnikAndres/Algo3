package Algo3.Repeticion;

import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Utilidad.FechaParser;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.control.ToggleButton;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public interface Repeticion extends Serializable {

    LocalDateTime obtenerUltimoDiaDeRepeticion();

    @JsonIgnore
    RepeticionTipo getTipo();

    @JsonIgnore
    default String getParams(){return "";}

    public static Repeticion desdeString(String datosJuntos){
        var datosSeparados = datosJuntos.split(";");
        var tipo = RepeticionTipo.valueOf(datosSeparados[0]);
        switch (tipo){
            case CANTIDAD_LIMITE -> {
                datosSeparados = datosJuntos.split("R:F");
                Frecuencia frecuencia = Frecuencia.desdeString(datosSeparados[1]);
                LocalDateTime fecha = FechaParser.fromString(datosSeparados[2]);
                return new RepeticionCantidadLimite(frecuencia,fecha,Integer.parseInt(datosSeparados[0].split(";")[1]));
            }
            case INFINITO -> {
                return new RepeticionInfinita();
            }
            case FECHA_LIMITE -> {
                return new RepeticionFechaLimite(FechaParser.fromString(datosSeparados[1]));
            }
            default -> {
                return null;
            }
        }
    }
}