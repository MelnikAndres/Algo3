package Algo3;

import java.time.LocalDateTime;
import java.util.List;

public class Calendario {
    private List<Tarea> tareas;
    private List<Evento> eventos;

    public void editar(Tarea tarea, String titulo, String descripcion, LocalDateTime fechaInicioNueva, LocalDateTime fechaFinNueva) {
        tarea.editarTarea(titulo, descripcion, fechaInicioNueva, fechaFinNueva);
    }

    public void editar(Evento evento,String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, FrecuenciaTipo frecuencia, List<Integer> intervalo, int cantidadRepeticiones, LocalDateTime fechaLimite, RepeticionTipo tipo){
        evento.editarEvento(titulo, descripcion, fechaInicio, fechaFin, frecuencia, intervalo, cantidadRepeticiones, fechaLimite, tipo);
    }

    public void eliminar() {
    }
    public void crear(){

    }



}
