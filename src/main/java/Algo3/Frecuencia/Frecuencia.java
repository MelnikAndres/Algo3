package Algo3.Frecuencia;

import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Parametros;

import java.time.LocalDateTime;
import java.util.HashMap;

public interface Frecuencia {
    LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual);
    FrecuenciaTipo getTipo();
    default Parametros getParams(){return  new Parametros();}
}
