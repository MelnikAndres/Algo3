package Algo3.Modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tarea extends Asignable {
    private boolean esCompletada;
    public Tarea(@JsonProperty("titulo") String titulo, @JsonProperty("descripcion") String descripcion,
          @JsonProperty("fechaInicio") LocalDateTime fechaInicio, @JsonProperty("fechaFinal") LocalDateTime fechaFinal) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        this.esCompletada = false;
    }

    public boolean getEsCompletada(){return this.esCompletada;}

    @Override
    public List<LocalDateTime> obtenerAparicionesEnMesyAnio(int numeroDeMes, int anio) {
        List<LocalDateTime> aparicion = new ArrayList<>();
        if(this.getFechaInicio().getYear() == anio &&  this.getFechaInicio().getMonth().getValue() == numeroDeMes){
            aparicion.add(this.getFechaInicio());
        }
        return aparicion;
    }

    public void cambiarEstadoCompletada(){
        this.esCompletada = !this.esCompletada;
    }
    @Override
    protected String getData() {
        return esCompletada?"esCompletada":"noEsCompletada";
    }
}
