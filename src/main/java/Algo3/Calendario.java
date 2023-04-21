package Algo3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Calendario {
    /*FALTA IMPLEMENTAR CREAR Y ELIMINAR*/

    private final HashMap<Integer,Tarea> tareas;
    private final HashMap<Integer, Evento> eventos;

    private int IdIncremental;


    public Calendario(){
        IdIncremental = 0;
        this.tareas = new HashMap<>();
        this.eventos = new HashMap<>();
    }
    public void editar(Integer clave, String titulo, String descripcion, LocalDateTime fechaInicioNueva, LocalDateTime fechaFinNueva){
        if(!tareas.containsKey(clave)) {
            throw new RuntimeException(ErrorTipo.ASIGNABLE_INVALIDO.toString());
        }
        tareas.get(clave).editar(titulo, descripcion, fechaInicioNueva, fechaFinNueva);
    }

   public void editar(Integer clave,String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, FrecuenciaTipo frecuencia, int intervalo, int cantidadRepeticiones, LocalDateTime fechaLimite, RepeticionTipo tipo){
        if(!eventos.containsKey(clave)){
            throw new RuntimeException(ErrorTipo.ASIGNABLE_INVALIDO.toString());
        }
        eventos.get(clave).editar(titulo, descripcion, fechaInicio, fechaFin, frecuencia, intervalo, tipo, fechaLimite, cantidadRepeticiones);
    }

    public void eliminar(Integer clave) {
        eventos.remove(clave);
        tareas.remove(clave);
    }
    public void crear(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, FrecuenciaTipo frecuencia, int intervalo, int cantidadRepeticiones, LocalDateTime fechaLimite, RepeticionTipo tipo){
        Evento evento = new Evento(titulo, descripcion,fechaInicio, fechaFin, frecuencia, intervalo, tipo, fechaLimite, cantidadRepeticiones);
        eventos.put(IdIncremental, evento);
        IdIncremental = IdIncremental + 1;
    }
    public void crear(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin){
        Tarea tarea = new Tarea(titulo, descripcion, fechaInicio, fechaFin);
        tareas.put(IdIncremental, tarea);
        IdIncremental = IdIncremental + 1;
    }



}
