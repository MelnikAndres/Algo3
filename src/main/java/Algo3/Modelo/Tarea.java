package Algo3.Modelo;

import Algo3.Constantes.ParametroTipo;
import Algo3.Utilidad.Completador;
import Algo3.Utilidad.Editor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void setCompletada(boolean esCompletada){
        this.esCompletada = esCompletada;
    }
    @Override
    public String toString() {
        return super.toString()+", "+(esCompletada?"esCompletada":"noEsCompletada");
    }
    @Override
    public boolean recibirCompletador(Completador completador){
        completador.completar(this);
        return true;
    }
    @Override
    public void recibirEditor(Editor editor) {
        editor.editar(this);
    }
}
