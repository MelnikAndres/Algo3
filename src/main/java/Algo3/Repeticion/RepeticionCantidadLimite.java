package Algo3.Repeticion;

import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Constantes.RepeticionTipo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class RepeticionCantidadLimite implements Repeticion{

    private LocalDateTime fechaInicio;
    private Frecuencia frecuencia;
    private Integer cantidadDeRepeticiones;
    public RepeticionCantidadLimite(@JsonProperty("frecuencia") Frecuencia frecuencia, @JsonProperty("fechaInicio")LocalDateTime fechaInicio,@JsonProperty("cantidadDeRepeticiones") Integer cantidadDeRepeticiones){
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

    public String getParams() {
        return cantidadDeRepeticiones.toString();
    }
}



