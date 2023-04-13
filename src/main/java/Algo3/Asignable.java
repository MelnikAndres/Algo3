package Algo3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

abstract class Asignable {
    private String titulo;
    private String descripcion;
    protected LocalDateTime fechaInicio;
    protected LocalDateTime fechaFinal;
    private List<Alarma> alarmas = new ArrayList<>();

    public ErrorTipo AgregarAlarma(LocalDateTime fecha, boolean esAbsoluta, AlarmaTipo tipo){
        if(fecha == null){
            return ErrorTipo.FECHA_FALTANTE;
        }
        if(tipo == null){
            return ErrorTipo.TIPO_FALTANTE;
        }
        alarmas.add(new Alarma(fecha,esAbsoluta,tipo));
        return null;
    }
    public ErrorTipo Editar(String nuevoTitulo, String nuevaDescripcion,
                       LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFinal){
        if(nuevoTitulo.isEmpty()){
            return ErrorTipo.NO_TITULO;
        }
        if(nuevaFechaInicio == null || nuevaFechaFinal == null){
            return ErrorTipo.FECHA_FALTANTE;
        }
        if(nuevaFechaInicio.isAfter(nuevaFechaFinal)){
            return ErrorTipo.FECHA_INICIO_INVALIDA;
        }
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
        this.fechaInicio = nuevaFechaInicio;
        this.fechaFinal = nuevaFechaFinal;
        return null;
    }

}
