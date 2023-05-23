package Algo3;

import Algo3.Constantes.DisparadorTipo;
import Algo3.Constantes.ErrorTipo;
import Algo3.Disparador.Disparador;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Alarma implements Serializable {
    /* tiempoAntes marca cuanto tiempo antes del evento/tarea debe sonar la alarma.
    Si la alarma es absoluta, es 0. */
    private LocalDateTime fecha;
    private Duration tiempoAntes;
    private Disparador disparador;



    public Alarma(@JsonProperty("fecha") LocalDateTime fecha, @JsonProperty("tiempoAntes") Duration tiempoAntes, @JsonProperty("disparador") Disparador disparador) {
        if (fecha == null) {
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        }
        if (disparador == null) {
            throw new RuntimeException(ErrorTipo.TIPO_FALTANTE.toString());
        }
            this.fecha = fecha;
            this.tiempoAntes = tiempoAntes;
            this.disparador = disparador;

    }
    @JsonIgnore
    public DisparadorTipo getTipo(){
        return this.disparador.getTipoDisparador();
    }

    public void disparar(){
       this.disparador.disparar();
    }

    public boolean comparar(Alarma alarma){
        if(!this.fecha.toString().equals(alarma.fecha.toString())){
            return false;
        }
        if(!this.tiempoAntes.toString().equals(alarma.tiempoAntes.toString())){
            return false;
        }
        if(!this.getTipo().toString().equals(alarma.getTipo().toString())){
            return false;
        }
        return true;
    }
    
}
