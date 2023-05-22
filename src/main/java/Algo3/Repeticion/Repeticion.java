package Algo3.Repeticion;

import Algo3.Constantes.RepeticionTipo;
import Algo3.Parametros;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.time.LocalDateTime;
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public interface Repeticion extends Serializable {

    LocalDateTime obtenerUltimoDiaDeRepeticion();

    @JsonIgnore
    RepeticionTipo getTipo();

    @JsonIgnore
    default Parametros getParams(){return  new Parametros();}
}