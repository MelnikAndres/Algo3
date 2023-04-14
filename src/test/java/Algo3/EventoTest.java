package Algo3;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventoTest {
    @Test
    public void CrearEventoSinRepeticiones() {
        //arrange
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,17,1,0));
        var resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add(LocalDateTime.of(2002,1,17,0,0));
        //act
        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        assertEquals(repeticionesObtenidas, resultadoEsperado);
        //assert
    }
}