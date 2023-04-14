package Algo3;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventoTest {
    @Test
    public void EventoSinRepeticiones() {
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
    @Test
    public void EventoQueDuraDosDias(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,18,3,0));
        var resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add(LocalDateTime.of(2002,1,17,0,0));
        //act
        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        assertEquals(repeticionesObtenidas, resultadoEsperado);
    }

    @Test
    public void EventoDiarioUnaRepeticion(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,17,3,0),
                FrecuenciaTipo.DIARIA, List.of(3),1);
        var resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add(LocalDateTime.of(2002,1,17,0,0));
        resultadoEsperado.add(LocalDateTime.of(2002,1,20,0,0));

        //act
        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        assertEquals(resultadoEsperado, repeticionesObtenidas);
    }
    @Test
    public void EventoDiarioCeroRepeticiones(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,17,3,0),
                FrecuenciaTipo.DIARIA, List.of(3),0);
        var resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add(LocalDateTime.of(2002,1,17,0,0));

        //act
        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        assertEquals(resultadoEsperado, repeticionesObtenidas);
    }
    @Test
    public void EventoSemanalHastaFechaLimite(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,17,3,0),
                FrecuenciaTipo.SEMANAL, List.of(Dia.JUEVES.getValor()),RepeticionTipo.FECHA_LIMITE,
                LocalDateTime.of(2002,2,17,3,0));
        var resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add(LocalDateTime.of(2002,1,17,0,0));
        resultadoEsperado.add(LocalDateTime.of(2002,1,24,0,0));
        resultadoEsperado.add(LocalDateTime.of(2002,1,31,0,0));
        resultadoEsperado.add(LocalDateTime.of(2002,2,7,0,0));
        resultadoEsperado.add(LocalDateTime.of(2002,2,14,0,0));


        //act
        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(2, 2002));
        assertEquals(resultadoEsperado, repeticionesObtenidas);
    }
    @Test
    public void EventoSemanalTripleHastaFechaLimite(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,17,3,0),
                FrecuenciaTipo.SEMANAL, List.of(Dia.MARTES.getValor(),Dia.JUEVES.getValor(),Dia.VIERNES.getValor()),RepeticionTipo.FECHA_LIMITE,
                LocalDateTime.of(2002,3,5,3,0));
        var resultadoEsperado = new ArrayList<>();
        for(int dia: List.of(17,18,22,24,25,29,31)){
            resultadoEsperado.add(LocalDateTime.of(2002,1,dia,0,0));
        }
        for(int dia: List.of(1,5,7,8,12,14,15,19,21,22,26,28)){
            resultadoEsperado.add(LocalDateTime.of(2002,2,dia,0,0));
        }
        resultadoEsperado.add(LocalDateTime.of(2002,3,1,0,0));
        resultadoEsperado.add(LocalDateTime.of(2002,3,5,0,0));


        //act
        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(2, 2002));
        repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(3, 2002));
        assertEquals(resultadoEsperado, repeticionesObtenidas);
    }
    @Test
    public void EventoMensualInfinito(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,17,3,0),
                FrecuenciaTipo.MENSUAL, List.of(1),RepeticionTipo.INFINITO, null);
        var resultadoEsperado = new ArrayList<>();
        for(int mes: List.of(1,2,3,4,5,6,7,8,9,10,11,12)){
            resultadoEsperado.add(LocalDateTime.of(2002,mes,17,0,0));
        }
        for(int mes: List.of(1,2,3,4,5,6,7,8,9,10,11,12)){
            resultadoEsperado.add(LocalDateTime.of(2003,mes,17,0,0));
        }
        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        for(int mes: List.of(2,3,4,5,6,7,8,9,10,11,12)){
            repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(mes, 2002));
        }
        for(int mes: List.of(1,2,3,4,5,6,7,8,9,10,11,12)){
            repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(mes, 2003));
        }
        //act
        assertEquals(resultadoEsperado, repeticionesObtenidas);
    }

    @Test
    public void EventoAnualConCantidadyFechasSinAparicion(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2002,1,17,0,0),
                LocalDateTime.of(2002,1,17,3,0),
                FrecuenciaTipo.ANUAL, List.of(1),7);
        var resultadoEsperado = new ArrayList<>();
        for(int anio: List.of(2002,2003,2004,2005,2006,2007,2008)){
            resultadoEsperado.add(LocalDateTime.of(anio,1,17,0,0));
        }

        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, 2002);
        for(int anio: List.of(2003,2004,2005,2006,2007,2008)){
            repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, anio));
        }
        for(int mes: List.of(3,4,5,6)){
            repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(mes, 2003));
        }
        for(int anio: List.of(2000,2001,2010,2014)){
            repeticionesObtenidas.addAll(eventoDePrueba.obtenerRepeticionesEnMesyAnio(1, anio));
        }
        //act
        assertEquals(resultadoEsperado, repeticionesObtenidas);
    }
    @Test
    public void EventoDiarioInfinitoYFechaMuyLejana(){
        var eventoDePrueba = new Evento("Prueba","Esto es una preueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, List.of(1),RepeticionTipo.INFINITO, null);
        var resultadoEsperado = new ArrayList<>();
        var inicio = LocalDateTime.of(9999,5,1,6,3);
        for(int i = 0;i < 31;i++){
            resultadoEsperado.add(inicio.plusDays(i));
        }

        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(5, 9999);
        assertEquals(resultadoEsperado, repeticionesObtenidas);

    }
}