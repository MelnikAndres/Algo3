package Algo3.Frecuencia;

import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Parametros;

import java.time.LocalDateTime;
import java.util.HashMap;

public class FrecuenciaAnual implements Frecuencia {

    @Override
    public LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        return fechaActual.plusYears(1);
    }
    public FrecuenciaTipo getTipo(){
        return FrecuenciaTipo.ANUAL;
    }

}
