package Algo3;

import Algo3.Constantes.Dia;
import Algo3.Frecuencia.FrecuenciaDiaria;
import Algo3.Frecuencia.FrecuenciaSemanal;
import Algo3.Repeticion.RepeticionCantidadLimite;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarioTest {

    @Test
    public void editar() {
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
        assertEquals(true, calendario.contiene(tarea));
        calendario.eliminar(0);
        assertEquals(false, calendario.contiene(tarea));
    }

    @Test
    public void agregar() {
        var calendario = new Calendario();
        Tarea tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        calendario.agregar(tarea);
        assertEquals(true, calendario.contiene(tarea));
    }
}