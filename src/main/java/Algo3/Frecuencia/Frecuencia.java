package Algo3.Frecuencia;
import Algo3.Constantes.FrecuenciaTipo;

import Algo3.Modelo.Parametros;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
