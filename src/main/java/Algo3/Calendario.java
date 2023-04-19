package Algo3;

import java.time.LocalDateTime;
import java.util.List;

public class Calendario {
    /*FALTA IMPLEMENTAR CREAR Y ELIMINAR*/

    private List<Tarea> tareas;
    private List<Evento> eventos;

    public void editarTarea(Tarea tarea, String titulo, String descripcion, LocalDateTime fechaInicioNueva, LocalDateTime fechaFinNueva){
        if(tarea == null || !tareas.contains(tarea))
            throw new RuntimeException(ErrorTipo.ASIGNABLE_INVALIDO.toString());
        tarea.editarTarea(titulo, descripcion, fechaInicioNueva, fechaFinNueva);
    }

    public void editarEvento(Evento evento,String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, FrecuenciaTipo frecuencia, int intervalo, int cantidadRepeticiones, LocalDateTime fechaLimite, RepeticionTipo tipo){
        evento.editarEvento(titulo, descripcion, fechaInicio, fechaFin, frecuencia, intervalo, tipo, fechaLimite, cantidadRepeticiones);
    }

    public void eliminar() {

    }
    public void crear(){

    }



}
