package Algo3;

import java.time.Duration;
import java.time.LocalDateTime;

public class Alarma {
    /* tiempoAntes marca cuanto tiempo antes del evento/tarea debe sonar la alarma.
    Si la alarma es absoluta, es 0. */
    private LocalDateTime fecha;
    private Duration tiempoAntes;
    private AlarmaTipo tipo;



    public Alarma(LocalDateTime fecha, Duration tiempoAntes, AlarmaTipo tipo) {
        this.fecha = fecha;
        this.tiempoAntes = tiempoAntes;
        this.tipo = tipo;
    }

    public void Disparar(){
       //toDo...
    }
    
}
