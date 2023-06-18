package Algo3.Utilidad;

import javafx.event.Event;
import javafx.event.EventType;

public class CalendarioEvento extends Event {
    public static EventType<CalendarioEvento> NUEVA_ALARMA = new EventType<CalendarioEvento>("Nueva Alarma");
    public static EventType<CalendarioEvento> SERIALIZAR = new EventType<CalendarioEvento>("Serializar");
    public CalendarioEvento(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
