package Algo3;

import Algo3.Constantes.Dia;
import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Frecuencia.*;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class FrecuenciaTest {


    @Test
    public void frecuenciaAnualTipo(){
        Frecuencia frecuencia = new FrecuenciaAnual();
        Assert.assertEquals(FrecuenciaTipo.ANUAL, frecuencia.getTipo());
    }
    @Test
    public void frecuenciaAnualSiguienteAparicion(){
        Frecuencia frecuencia = new FrecuenciaAnual();
        LocalDateTime fechaInicial = LocalDateTime.of(2002,1,17,0,0);
        LocalDateTime fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaInicial);
        LocalDateTime resultadoEsperado = LocalDateTime.of(2003,1,17,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2004,1,17,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2005,1,17,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
    }
    @Test
    public void frecuenciaMensualTipo(){
        Frecuencia frecuencia = new FrecuenciaMensual();
        Assert.assertEquals(FrecuenciaTipo.MENSUAL, frecuencia.getTipo());
    }
    @Test
    public void frecuenciaMensualSiguienteAparicion(){
        Frecuencia frecuencia = new FrecuenciaMensual();
        LocalDateTime fechaInicial = LocalDateTime.of(2002,1,17,0,0);
        LocalDateTime fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaInicial);
        LocalDateTime resultadoEsperado = LocalDateTime.of(2002,2,17,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2002,3,17,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2002,4,17,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
    }
    @Test
    public void frecuenciaSemanalTipo(){
        Frecuencia frecuencia = new FrecuenciaSemanal(List.of(Dia.LUNES,Dia.MIERCOLES));
        Assert.assertEquals(FrecuenciaTipo.SEMANAL, frecuencia.getTipo());
    }
    @Test
    public void frecuenciaSemanalUnDiaSiguienteAparicion(){
        Frecuencia frecuencia = new FrecuenciaSemanal(List.of(Dia.LUNES));
        LocalDateTime fechaInicial = LocalDateTime.of(2023,4,24,4,4);
        LocalDateTime fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaInicial);
        LocalDateTime resultadoEsperado = LocalDateTime.of(2023,5,1,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2023,5,8,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2023,5,15,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
    }
    @Test
    public void frecuenciaSemanalVariosDiasSiguienteAparicion(){
        Frecuencia frecuencia = new FrecuenciaSemanal(List.of(Dia.LUNES,Dia.MARTES,Dia.VIERNES));
        LocalDateTime fechaInicial = LocalDateTime.of(2023,4,24,4,4);
        LocalDateTime fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaInicial);
        LocalDateTime resultadoEsperado = LocalDateTime.of(2023,4,25,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2023,4,28,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2023,5,1,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2023,5,2,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2023,5,5,4,4);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
    }
    @Test
    public void FrecuenciaSemanalIntervaloMayorA127(){

        Assert.assertThrows(ErrorTipo.INTERVALO_INVALIDO.toString(), RuntimeException.class, () -> new FrecuenciaSemanal(List.of(Dia.DOMINGO,Dia.DOMINGO)));
    }
    @Test
    public void FrecuenciaSemanalIntervaloMenorA1(){


        Assert.assertThrows(ErrorTipo.INTERVALO_INVALIDO.toString(), RuntimeException.class, () -> new FrecuenciaSemanal(new ArrayList<>()));
    }

    @Test
    public void frecuenciaDiariaSiguienteAparicion(){
        Frecuencia frecuencia = new FrecuenciaDiaria(2);
        LocalDateTime fechaInicial = LocalDateTime.of(2002,1,17,0,0);
        LocalDateTime fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaInicial);
        LocalDateTime resultadoEsperado = LocalDateTime.of(2002,1,19,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2002,1,21,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
        fechaCorrida = frecuencia.obtenerSiguienteAparicion(fechaCorrida);
        resultadoEsperado = LocalDateTime.of(2002,1,23,0,0);
        Assert.assertEquals(resultadoEsperado, fechaCorrida);
    }
    @Test
    public void frecuenciaDiariaTipo(){
        Frecuencia frecuencia = new FrecuenciaDiaria(3);
        Assert.assertEquals(FrecuenciaTipo.DIARIA, frecuencia.getTipo());
    }

    @Test
    public void FrecuenciaDiariaIntervaloMenorA1(){


        Assert.assertThrows(ErrorTipo.INTERVALO_INVALIDO.toString(), RuntimeException.class, () -> new FrecuenciaDiaria(0));
    }
}