package Algo3.Utilidad;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class ErrorEvento extends Event {

    private String texto;

    public static EventType<ErrorEvento> EDITAR = new EventType<ErrorEvento>("Editar");

    public ErrorEvento(EventType<? extends Event> eventType, String texto) {
        super(eventType);
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
