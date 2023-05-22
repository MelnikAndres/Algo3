package Algo3.Repeticion;

import Algo3.Constantes.RepeticionTipo;
import Algo3.Parametros;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Repeticion extends Serializable {

    LocalDateTime obtenerUltimoDiaDeRepeticion();

    RepeticionTipo getTipo();
    default Parametros getParams(){return  new Parametros();}
}