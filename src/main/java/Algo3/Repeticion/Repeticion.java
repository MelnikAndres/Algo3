package Algo3.Repeticion;

import Algo3.Constantes.RepeticionTipo;
import Algo3.Parametros;

import java.time.LocalDateTime;

public interface Repeticion {

    LocalDateTime obtenerUltimoDiaDeRepeticion();

    RepeticionTipo getTipo();
    default Parametros getParams(){return  new Parametros();}
}