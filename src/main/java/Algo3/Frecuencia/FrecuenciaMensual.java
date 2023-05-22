package Algo3.Frecuencia;

import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Parametros;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDateTime;
import java.util.HashMap;
public class FrecuenciaMensual implements Frecuencia {

    @Override
    public LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        return fechaActual.plusMonths(1);
    }
    @Override
    public FrecuenciaTipo getTipo(){
        return FrecuenciaTipo.MENSUAL;
    }


}
