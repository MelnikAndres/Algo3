package Algo3;

import Algo3.Constantes.DisparadorTipo;
import Algo3.Constantes.ErrorTipo;
import Algo3.Disparador.Disparador;


import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Alarma implements Serializable {
    /* tiempoAntes marca cuanto tiempo antes del evento/tarea debe sonar la alarma.
    Si la alarma es absoluta, es 0. */
    private LocalDateTime fecha;
    private Duration tiempoAntes;
    private Disparador tipo;



    public Alarma(LocalDateTime fecha, Duration tiempoAntes, Disparador tipo) {
        if (fecha == null) {
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        }
        if (tipo == null) {
            throw new RuntimeException(ErrorTipo.TIPO_FALTANTE.toString());
        }
            this.fecha = fecha;
            this.tiempoAntes = tiempoAntes;
            this.tipo = tipo;

    }

    public DisparadorTipo getTipo(){
        return this.tipo.getTipo();
    }

    public void Disparar(){
       this.tipo.disparar();
    }
    
}
