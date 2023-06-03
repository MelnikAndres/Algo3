package Algo3;

import Algo3.Constantes.Dia;
import Algo3.Disparador.Mail;
import Algo3.Disparador.Notificacion;
import Algo3.Disparador.Sonido;
import Algo3.Frecuencia.FrecuenciaDiaria;
import Algo3.Frecuencia.FrecuenciaMensual;
import Algo3.Frecuencia.FrecuenciaSemanal;
import Algo3.Modelo.Alarma;
import Algo3.Modelo.Calendario;
import Algo3.Modelo.Evento;
import Algo3.Modelo.Tarea;
import Algo3.Repeticion.RepeticionCantidadLimite;
import Algo3.Repeticion.RepeticionFechaLimite;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarioTest {

    @Test
    public void editar(){
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
    @Test
    public void agregarMultiplesAlarmas(){
        var calendario = new Calendario();
        Tarea tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        calendario.agregar(tarea);
        var alarma = new Alarma(tarea.getFechaInicio(), Duration.ZERO, new Mail());
        calendario.agregarAlarma(0, alarma);
        var alarma2 = new Alarma(tarea.getFechaFinal(), Duration.of(30, ChronoUnit.MINUTES), new Notificacion());
        calendario.agregarAlarma(0, alarma2);
        var alarma3 = new Alarma(tarea.getFechaFinal().plusDays(10), Duration.ZERO, new Sonido());
        calendario.agregarAlarma(0, alarma3);
        List<Alarma> alarmas = new ArrayList<>();
        alarmas.add(alarma);
        alarmas.add(alarma2);
        alarmas.add(alarma3);
        assertTrue(calendario.asignableConClave(0).getAlarmas().containsAll(alarmas));
    }
    @Test
    public void serializarSinAsignables() throws IOException{
        var calendario = new Calendario();
        ByteArrayOutputStream primerSerializado = new ByteArrayOutputStream();
        calendario.serializar(primerSerializado);
        InputStream stream = new ByteArrayInputStream(primerSerializado.toByteArray());
        Calendario calendarioDeserealizado = Calendario.deserializar(stream);
        ByteArrayOutputStream segundoSerializado = new ByteArrayOutputStream();
        calendarioDeserealizado.serializar(segundoSerializado);
        assertEquals(primerSerializado.toString(), segundoSerializado.toString());
    }
    @Test
    public void serializarConEvento() throws IOException{
        var calendario = new Calendario();
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                new FrecuenciaDiaria(1),
                new RepeticionCantidadLimite(new FrecuenciaDiaria(1), LocalDateTime.of(2022,5,4,6,3), 10));
        calendario.agregar(eventoDePrueba);
        ByteArrayOutputStream serializado = new ByteArrayOutputStream();
        calendario.serializar(serializado);
        InputStream stream = new ByteArrayInputStream(serializado.toByteArray());
        Calendario calendarioDeserealizado = Calendario.deserializar(stream);
        assertTrue(calendario.obtenerAsignablePorId(0).comparar(calendarioDeserealizado.obtenerAsignablePorId(0)));
    }
    @Test
    public void serializarConTarea() throws IOException{
        var calendario = new Calendario();
        var tareaDePrueba = new Tarea("Tarea","This is a test",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45));
        calendario.agregar(tareaDePrueba);
        ByteArrayOutputStream serializado = new ByteArrayOutputStream();
        calendario.serializar(serializado);
        InputStream stream = new ByteArrayInputStream(serializado.toByteArray());
        Calendario calendarioDeserealizado = Calendario.deserializar(stream);
        assertTrue(calendario.obtenerAsignablePorId(0).comparar(calendarioDeserealizado.obtenerAsignablePorId(0)));
    }
    @Test
    public void serializarConMultiplesAsignables() throws IOException{
        var calendario = new Calendario();
        var tareaDePrueba = new Tarea("Tarea","This is a test",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45));
        calendario.agregar(tareaDePrueba);
        var eventoDePrueba = new Evento("Prueba numero 1","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2023,4,4,12,45),
                new FrecuenciaMensual(),
                new RepeticionCantidadLimite(new FrecuenciaMensual(), LocalDateTime.of(2022,5,4,6,3), 10));
        calendario.agregar(eventoDePrueba);
        var eventoDePrueba2 = new Evento("Prueba numero 2","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                new FrecuenciaDiaria(1),
                new RepeticionFechaLimite(LocalDateTime.of(2022,5,4,6,3)));
        calendario.agregar(eventoDePrueba2);
        var eventoDePrueba3 = new Evento("Prueba numero 3","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2023,5,4,12,45),
                new FrecuenciaSemanal(List.of(Dia.LUNES,Dia.DOMINGO)),
                new RepeticionCantidadLimite(new FrecuenciaSemanal(List.of(Dia.LUNES, Dia.DOMINGO)), LocalDateTime.of(2022,5,4,6,3), 10));
        calendario.agregar(eventoDePrueba3);
        ByteArrayOutputStream serializado = new ByteArrayOutputStream();
        calendario.serializar(serializado);
        InputStream stream = new ByteArrayInputStream(serializado.toByteArray());
        Calendario calendarioDeserealizado = Calendario.deserializar(stream);
        assertTrue(calendario.obtenerAsignablePorId(0).comparar(calendarioDeserealizado.obtenerAsignablePorId(0)));
        assertTrue(calendario.obtenerAsignablePorId(1).comparar(calendarioDeserealizado.obtenerAsignablePorId(1)));
        assertTrue(calendario.obtenerAsignablePorId(2).comparar(calendarioDeserealizado.obtenerAsignablePorId(2)));
        assertTrue(calendario.obtenerAsignablePorId(3).comparar(calendarioDeserealizado.obtenerAsignablePorId(3)));
    }
    @Test
    public void serializarConMultiplesAsignablesConAlarmas() throws IOException{
        var calendario = new Calendario();
        var tareaDePrueba = new Tarea("Tarea","This is a test",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45));
        calendario.agregar(tareaDePrueba);
        var eventoDePrueba = new Evento("Prueba numero 1","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2023,4,4,12,45),
                new FrecuenciaMensual(),
                new RepeticionCantidadLimite(new FrecuenciaMensual(), LocalDateTime.of(2022,5,4,6,3), 10));
        calendario.agregar(eventoDePrueba);
        var eventoDePrueba2 = new Evento("Prueba numero 2","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                new FrecuenciaDiaria(1),
                new RepeticionFechaLimite(LocalDateTime.of(2022,5,4,6,3)));
        calendario.agregar(eventoDePrueba2);
        var eventoDePrueba3 = new Evento("Prueba numero 3","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2023,5,4,12,45),
                new FrecuenciaSemanal(List.of(Dia.LUNES,Dia.DOMINGO)),
                new RepeticionCantidadLimite(new FrecuenciaSemanal(List.of(Dia.LUNES, Dia.DOMINGO)), LocalDateTime.of(2022,5,4,6,3), 10));
        calendario.agregar(eventoDePrueba3);
        eventoDePrueba.agregarAlarma(new Alarma(LocalDateTime.of(2022,5,4,12,45), Duration.ZERO, new Notificacion()));
        eventoDePrueba3.agregarAlarma(new Alarma(LocalDateTime.of(2024,5,4,12,45), Duration.ZERO, new Mail()));
        ByteArrayOutputStream serializado = new ByteArrayOutputStream();
        calendario.serializar(serializado);
        InputStream stream = new ByteArrayInputStream(serializado.toByteArray());
        Calendario calendarioDeserealizado = Calendario.deserializar(stream);
        assertTrue(calendario.obtenerAsignablePorId(0).comparar(calendarioDeserealizado.obtenerAsignablePorId(0)));
        assertTrue(calendario.obtenerAsignablePorId(1).comparar(calendarioDeserealizado.obtenerAsignablePorId(1)));
        assertTrue(calendario.obtenerAsignablePorId(2).comparar(calendarioDeserealizado.obtenerAsignablePorId(2)));

    }
}