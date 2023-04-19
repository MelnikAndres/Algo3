package Algo3;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

abstract class Asignable {


    private String titulo;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private List<Alarma> alarmas = new ArrayList<>();

    Asignable(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio =fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public void agregarAlarma(LocalDateTime fecha, Duration tiempoAntes, AlarmaTipo tipo) throws RuntimeException{
        if(fecha == null){
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        }
        if(tipo == null){
            throw new RuntimeException(ErrorTipo.TIPO_FALTANTE.toString());
        }
        alarmas.add(new Alarma(fecha,tiempoAntes,tipo));
    }
    protected void editar(String nuevoTitulo, String nuevaDescripcion,
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

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


}
