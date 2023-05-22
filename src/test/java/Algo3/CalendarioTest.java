package Algo3;

import Algo3.Constantes.Dia;

import Algo3.Disparador.Mail;
import Algo3.Frecuencia.FrecuenciaDiaria;
import Algo3.Frecuencia.FrecuenciaSemanal;
import Algo3.Repeticion.RepeticionCantidadLimite;
import org.json.simple.parser.ParseException;
import org.junit.Test;


import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarioTest {

    @Test
    public void editar() throws IOException, ParseException, ClassNotFoundException {
        var calendario = new Calendario();
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                new FrecuenciaDiaria(1),
                new RepeticionCantidadLimite(new FrecuenciaDiaria(1), LocalDateTime.of(2022,5,4,6,3), 10));
        calendario.agregar(eventoDePrueba);
        assertEquals(eventoDePrueba, calendario.asignableConClave(0));
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                new FrecuenciaSemanal(List.of(Dia.LUNES)),
                new RepeticionCantidadLimite(new FrecuenciaSemanal(List.of(Dia.LUNES)),LocalDateTime.of(2022,5,4,6,3),10));
        calendario.editar(0, eventoDePrueba);
        assertEquals(eventoDePrueba, calendario.asignableConClave(0));
        FileOutputStream file = new FileOutputStream("calendario.json");
        calendario.serializar(file);
        FileInputStream fs = new FileInputStream("calendario.json");
        assertEquals(calendario.deserializar(fs).getIdIncremental(), calendario.getIdIncremental());


    }
    @Test
    public void reemplazarAsignablePorOtro(){
        var calendario = new Calendario();
        Tarea tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        calendario.agregar(tarea);
        Tarea tarea2 = new Tarea("Tarea 2", "Gran Tarea",LocalDateTime.of(2024, 3, 3, 0, 0),
                LocalDateTime.of(2024, 3, 3, 12, 0));
        calendario.editar(0, tarea2);
        assertEquals(tarea2, calendario.asignableConClave(0));
    }

    @Test
    public void eliminar() {
        var calendario = new Calendario();
        Tarea tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        calendario.agregar(tarea);
        assertTrue(calendario.contiene(tarea));
        calendario.eliminar(0);
        assertFalse(calendario.contiene(tarea));
    }

    @Test
    public void agregarAsignable() throws IOException, ParseException, ClassNotFoundException {
        var calendario = new Calendario();
        Tarea tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        calendario.agregar(tarea);
        assertEquals(true, calendario.contiene(tarea));
    }
    @Test
    public void agregarAlarma(){
        var calendario = new Calendario();
        Tarea tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        calendario.agregar(tarea);
        var alarma = new Alarma(tarea.getFechaInicio(), Duration.ZERO, new Mail());
        calendario.agregarAlarma(0, alarma);
        assertTrue(calendario.asignableConClave(0).getAlarmas().contains(alarma));
    }
}