package Algo3.Utilidad;

import Algo3.Componentes.OpcionesExtra.ExtraEvento;
import Algo3.Componentes.OpcionesExtra.OpcionExtra;
import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Evento;
import Algo3.Modelo.Tarea;
import Algo3.Repeticion.Repeticion;
import Algo3.Vista.DialogoEditarVista;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Supplier;

public class Editor {
    private DialogoEditarVista dialogoEditarVista;
    private Supplier<Asignable> obtenerResultado;
    public Editor(DialogoEditarVista dialogoEditarVista){
        this.dialogoEditarVista = dialogoEditarVista;
    }
    public void editar(Evento evento){
        agregarBasicos(evento);
        dialogoEditarVista.setTipo("Evento");
        dialogoEditarVista.removerExtra();
        ExtraEvento extra = new ExtraEvento();
        extra.setFrecuencia(evento.getFrecuenciaTipo()+ ";" +evento.getParametrosFrecuencia());
        extra.setRepeticion(evento.getRepeticionTipo()+ ";" +evento.getParametrosRepeticion());
        dialogoEditarVista.agregarExtra(extra);
        setObtenerEvento(extra);
    }
    public void editar(Tarea tarea){
        agregarBasicos(tarea);
        dialogoEditarVista.setTipo("Tarea");
        setObtenerTarea();
    }
    private void agregarBasicos(Asignable asignable){
        dialogoEditarVista.setTitulo(asignable.getTitulo());
        dialogoEditarVista.setDescripcion(asignable.getDescripcion());
        dialogoEditarVista.setFechaFinal(asignable.getFechaFinal());
        dialogoEditarVista.setFechaInicial(asignable.getFechaInicio());
        dialogoEditarVista.setDiaCompleto(asignable.getFechaInicio().equals(asignable.getFechaFinal()));
    }
    public void setObtenerEvento(ExtraEvento extra){
        obtenerResultado = new Supplier<Asignable>() {
            @Override
            public Asignable get() {
                Frecuencia frecuencia = extra.getFrecuencia();
                String repeticionComoString = extra.getRepeticionString();
                RepeticionTipo repeticionTipo = RepeticionTipo.valueOf(repeticionComoString.split(";")[0]);
                Repeticion repeticion;
                if(frecuencia == null){
                    return new Evento(dialogoEditarVista.getTitulo(),dialogoEditarVista.getDescripcion(),
                            dialogoEditarVista.getFechaInicial(),
                            dialogoEditarVista.getFechaFinal(),
                            null, null);
                }
                if(repeticionTipo.equals(RepeticionTipo.CANTIDAD_LIMITE)){
                    repeticion = Repeticion.desdeString(repeticionComoString + "R:F"+frecuencia.getTipo() + ";" +frecuencia.getParams()+ "R:F" + dialogoEditarVista.getFechaInicial().toString());
                } else{
                    repeticion = Repeticion.desdeString(repeticionComoString);
                }
                LocalDateTime fechaInicial = dialogoEditarVista.getFechaInicial();
                LocalDateTime fechaFinal = dialogoEditarVista.getFechaFinal();
                return new Evento(dialogoEditarVista.getTitulo(),dialogoEditarVista.getDescripcion(), fechaInicial,fechaFinal, frecuencia, repeticion);
            }
        };
    }
    public void setObtenerTarea(){
        obtenerResultado = new Supplier<Asignable>() {
            @Override
            public Asignable get() {
                LocalDateTime fechaInicial = dialogoEditarVista.getFechaInicial();
                LocalDateTime fechaFinal = dialogoEditarVista.getFechaFinal();
                return new Tarea(dialogoEditarVista.getTitulo(), dialogoEditarVista.getDescripcion(),
                        fechaInicial, fechaFinal);
            }
        };
    }
    public Asignable obtenerAsignable(){
        if(obtenerResultado == null){
            throw new RuntimeException(ErrorTipo.TIPO_FALTANTE.toString());
        }
        return obtenerResultado.get();
    }

}
