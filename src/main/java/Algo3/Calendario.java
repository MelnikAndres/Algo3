package Algo3;

import Algo3.Constantes.ErrorTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Repeticion.Repeticion;

import java.time.LocalDateTime;
import java.util.HashMap;

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
        tareas.get(clave);
    }
    public void editar(Integer clave, String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, Frecuencia frecuencia, Repeticion repeticion){
        if(!eventos.containsKey(clave)){
            throw new RuntimeException(ErrorTipo.ASIGNABLE_INVALIDO.toString());
        }
        eventos.get(clave).editar(titulo, descripcion, fechaInicio, fechaFin, frecuencia,repeticion);
    }

    public void eliminar(Integer clave) {
        eventos.remove(clave);
        tareas.remove(clave);
    }
    public void crear(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, Frecuencia frecuencia, Repeticion repeticion){
        Evento evento = new Evento(titulo, descripcion, fechaInicio, fechaFin, frecuencia,repeticion);
        eventos.put(IdIncremental, evento);
        IdIncremental = IdIncremental + 1;
    }
    public void crear(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin){
        Tarea tarea = new Tarea(titulo, descripcion, fechaInicio, fechaFin);
        tareas.put(IdIncremental, tarea);
        IdIncremental = IdIncremental + 1;
    }
}