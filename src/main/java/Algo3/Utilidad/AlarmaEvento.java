package Algo3.Utilidad;

import javafx.event.Event;
import javafx.event.EventType;

public class AlarmaEvento extends Event {
    public static EventType<AlarmaEvento> NUEVA_ALARMA = new EventType<AlarmaEvento>("Nueva Alarma");
    public AlarmaEvento(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
