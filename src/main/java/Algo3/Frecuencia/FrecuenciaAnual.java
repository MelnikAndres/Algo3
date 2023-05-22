package Algo3.Frecuencia;

import Algo3.Constantes.FrecuenciaTipo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDateTime;
public class FrecuenciaAnual implements Frecuencia {

    @Override
    public LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        return fechaActual.plusYears(1);
    }
    public FrecuenciaTipo getTipo(){
        return FrecuenciaTipo.ANUAL;
    }

}
