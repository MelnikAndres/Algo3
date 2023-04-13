package Algo3;

import java.time.LocalDateTime;

public class Alarma {
    private LocalDateTime fecha;
    private boolean esAbsoluta;
    private AlarmaTipo tipo;

    public Alarma(LocalDateTime fecha, boolean esAbsoluta, AlarmaTipo tipo) {
        this.fecha = fecha;
        this.esAbsoluta = esAbsoluta;
        this.tipo = tipo;
    }

    public void Disparar(){
        //ToDo...
    }
}
