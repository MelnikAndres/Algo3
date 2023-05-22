package Algo3.Frecuencia;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Evento;
import Algo3.Parametros;
import Algo3.Tarea;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public interface Frecuencia {
    LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual);
    @JsonIgnore
    FrecuenciaTipo getTipo();
    @JsonIgnore
    default Parametros getParams(){return  new Parametros();}
}
