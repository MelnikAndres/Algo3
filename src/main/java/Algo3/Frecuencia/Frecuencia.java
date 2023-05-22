package Algo3.Frecuencia;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Evento;
import Algo3.Parametros;
import Algo3.Tarea;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FrecuenciaAnual.class, name = "FrecuenciaAnual"),
        @JsonSubTypes.Type(value = FrecuenciaDiaria.class, name = "FrecuenciaDiaria"),
        @JsonSubTypes.Type(value = FrecuenciaMensual.class, name = "FrecuenciaMensual"),
        @JsonSubTypes.Type(value = FrecuenciaSemanal.class, name = "FrecuenciaSemanal"),
})
public interface Frecuencia {
    LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual);
    FrecuenciaTipo getTipo();
    default Parametros getParams(){return  new Parametros();}
}
