package Algo3;

import java.time.LocalDateTime;

public class Tarea extends Asignable{
    private boolean esCompletada;
    Tarea(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        this.esCompletada = false;
    }

    public void cambiarEstadoCompletada(){
        this.esCompletada = true;
    }
}
