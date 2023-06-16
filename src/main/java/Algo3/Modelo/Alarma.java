package Algo3.Modelo;

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
    private LocalDateTime tiempoAntes;
    private Disparador disparador;



    public Alarma(@JsonProperty("fecha") LocalDateTime fecha, @JsonProperty("tiempoAntes") LocalDateTime tiempoAntes, @JsonProperty("disparador") Disparador disparador) {
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

    @Override
    public String toString(){
        return fecha.toString() + ", "+ tiempoAntes.toString()+ ", "+ disparador.getTipoDisparador();
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Duration getTiempoAntes() {
        return tiempoAntes;
    }

    public void disparar(){
       this.disparador.disparar();
    }
    
}
