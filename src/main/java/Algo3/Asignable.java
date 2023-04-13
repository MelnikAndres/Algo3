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

    public void AgregarAlarma(LocalDateTime fecha, boolean esAbsoluta, AlarmaTipo tipo) throws RuntimeException{
        if(fecha == null){
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        }
        if(tipo == null){
            throw new RuntimeException(ErrorTipo.TIPO_FALTANTE.toString());
        }
        alarmas.add(new Alarma(fecha,esAbsoluta,tipo));
    }
    public void Editar(String nuevoTitulo, String nuevaDescripcion,
                       LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFinal) throws RuntimeException{
        if(nuevoTitulo.isEmpty()){
            throw new RuntimeException(ErrorTipo.NO_TITULO.toString());
        }
        if(nuevaFechaInicio == null || nuevaFechaFinal == null){
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        }
        if(nuevaFechaInicio.isAfter(nuevaFechaFinal)){
            throw new RuntimeException(ErrorTipo.FECHA_INICIO_INVALIDA.toString());
        }
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
        this.fechaInicio = nuevaFechaInicio;
        this.fechaFinal = nuevaFechaFinal;
    }
}
