package Algo3.Repeticion;

import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Parametros;

import java.time.LocalDateTime;

public class RepeticionCantidadLimite implements Repeticion{

    private LocalDateTime fechaInicio;
    private Frecuencia frecuencia;
    private Integer cantidadDeRepeticiones;
    public RepeticionCantidadLimite(Frecuencia frecuencia, LocalDateTime fechaInicio, Integer cantidadDeRepeticiones){
        if (cantidadDeRepeticiones < 0)
            throw new RuntimeException(ErrorTipo.REPETICIONES_INVALIDAS.toString());

        if(fechaInicio == null){
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        }
        if(frecuencia == null){
            throw new RuntimeException(ErrorTipo.REPETICION_SIN_FRECUENCIA.toString());
        }

        this.frecuencia = frecuencia;
        this.fechaInicio = fechaInicio;
        this.cantidadDeRepeticiones = cantidadDeRepeticiones;
    }

    @Override
    public LocalDateTime obtenerUltimoDiaDeRepeticion(){
        LocalDateTime ultimaRepeticion = fechaInicio;
        for(var i=0; i<cantidadDeRepeticiones; i++){
            ultimaRepeticion = frecuencia.obtenerSiguienteAparicion(ultimaRepeticion);
        }
        return ultimaRepeticion;
    }

    @Override
    public RepeticionTipo getTipo() {
        return RepeticionTipo.CANTIDAD_LIMITE;
    }

    public Parametros getParams() {
        Parametros parametros = new Parametros();
        parametros.agregarParametro("Fecha Inicial", ParametroTipo.FECHA,fechaInicio.toString());
        parametros.agregarParametro("Repeticiones",ParametroTipo.ENTERO, cantidadDeRepeticiones.toString());
        return parametros;
    }

}



