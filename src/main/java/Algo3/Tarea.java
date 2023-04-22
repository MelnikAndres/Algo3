package Algo3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tarea extends Asignable {
    private boolean esCompletada;
    Tarea(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        this.esCompletada = false;
    }

    @Override
    public List<LocalDateTime> obtenerAparicionesEnMesyAnio(int numeroDeMes, int anio) {
        List<LocalDateTime> aparicion = new ArrayList<>();
        if(this.getFechaInicio().getYear() == anio &&  this.getFechaInicio().getMonth().getValue() == numeroDeMes){
            aparicion.add(this.getFechaInicio());
        }
        return aparicion;
    }

    public void cambiarEstadoCompletada(){
        this.esCompletada = true;
    }
}
