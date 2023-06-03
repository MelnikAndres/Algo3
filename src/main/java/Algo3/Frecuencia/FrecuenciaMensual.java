package Algo3.Frecuencia;

import Algo3.Constantes.FrecuenciaTipo;

import java.time.LocalDateTime;

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
