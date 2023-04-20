package Algo3;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
                LocalDateTime.of(2002,1,17,1,0), FrecuenciaTipo.NINGUNA, 0, null, null, 0);
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
                LocalDateTime.of(2002,1,18,3,0), FrecuenciaTipo.NINGUNA, 0, null, null, 0);
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
                FrecuenciaTipo.DIARIA, 3,RepeticionTipo.CANTIDAD_LIMITE, null, 1);
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
                FrecuenciaTipo.DIARIA, 3,RepeticionTipo.CANTIDAD_LIMITE, null, 0);
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
                FrecuenciaTipo.SEMANAL, Dia.JUEVES.getValorBinario(), RepeticionTipo.FECHA_LIMITE,
                LocalDateTime.of(2002,2,17,3,0), 0);
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
                FrecuenciaTipo.SEMANAL, Dia.MARTES.getValorBinario()+Dia.JUEVES.getValorBinario()+Dia.VIERNES.getValorBinario(), RepeticionTipo.FECHA_LIMITE,
                LocalDateTime.of(2002,3,5,3,0), 0);
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
                FrecuenciaTipo.MENSUAL, 1,RepeticionTipo.INFINITO, null, 0);
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
                FrecuenciaTipo.ANUAL, 1,RepeticionTipo.CANTIDAD_LIMITE, null, 7);
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
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.INFINITO, null, 0);
        var resultadoEsperado = new ArrayList<>();
        var inicio = LocalDateTime.of(9999,5,1,6,3);
        for(int i = 0;i < 31;i++){
            resultadoEsperado.add(inicio.plusDays(i));
        }

        var repeticionesObtenidas = eventoDePrueba.obtenerRepeticionesEnMesyAnio(5, 9999);
        assertEquals(resultadoEsperado, repeticionesObtenidas);

    }
    @Test
    public void editarNombreEvento(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.INFINITO, null, 0);
        String resultadoEsperado = "Nuevo nombre";
        eventoDePrueba.editar("Nuevo nombre","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.INFINITO, null, 0);
        assertEquals(resultadoEsperado, eventoDePrueba.getTitulo());
    }
    @Test
    public void editarDescripcionEvento(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        String resultadoEsperado = "Nueva descripcion";
        eventoDePrueba.editar("Prueba","Nueva descripcion",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE,LocalDateTime.of(2023, 4, 21, 12, 30),0 );
        assertEquals(resultadoEsperado, eventoDePrueba.getDescripcion());
    }

    @Test
    public void editarFechas(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        LocalDateTime resultadoFechaInicialEsperado = LocalDateTime.of(2022, 5, 3, 0, 0);
        LocalDateTime resultadoFechaFinalEsperado = LocalDateTime.of(2022, 5, 4, 0, 0);
        LocalDateTime resultadoUltimaRepeticionEsperado = LocalDateTime.of(2023, 2, 4, 0, 0 );
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,3,0,0),
                LocalDateTime.of(2022,5,4,0,0),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE,LocalDateTime.of(2023, 2, 4, 0, 0),0 );
        assertEquals(resultadoFechaInicialEsperado, eventoDePrueba.getFechaInicio());
        assertEquals(resultadoFechaFinalEsperado, eventoDePrueba.getFechaFinal());
        assertEquals(resultadoUltimaRepeticionEsperado, eventoDePrueba.getUltimoDiaDeRepeticion());
    }
    @Test
    public void editarFrecuenciaDiaraASemanalConRepeticionTipoFechaLimite(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        FrecuenciaTipo frecuenciaEsperada = FrecuenciaTipo.SEMANAL;
        LocalDateTime fechaEsperada = LocalDateTime.of(2023, 4, 21, 12, 30);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        assertEquals(frecuenciaEsperada, eventoDePrueba.getFrecuencia());
        assertEquals(fechaEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
    }

    @Test
    public void editarFrecuenciaDiaraASemanalConRepeticionTipoCantidad(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.CANTIDAD_LIMITE, null, 10);
        LocalDateTime ultimaRepeticionEsperada = LocalDateTime.of(2022, 7, 11, 6, 3);
        FrecuenciaTipo frecuenciaEsperada = FrecuenciaTipo.SEMANAL;
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, Dia.LUNES.getValorBinario(),RepeticionTipo.CANTIDAD_LIMITE, null, 10);
        assertEquals(ultimaRepeticionEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
        assertEquals(frecuenciaEsperada, eventoDePrueba.getFrecuencia());
    }
    @Test
    public void editarFrecuenciaSemanalAMensualConRepeticionTipoCantidad(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, Dia.LUNES.getValorBinario() + Dia.MIERCOLES.getValorBinario(), RepeticionTipo.CANTIDAD_LIMITE,
                null, 10);
        LocalDateTime ultimaRepeticionEsperada = LocalDateTime.of(2023, 3, 4, 6, 3);
        FrecuenciaTipo frecuenciaEsperada = FrecuenciaTipo.MENSUAL;
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.MENSUAL, 0,RepeticionTipo.CANTIDAD_LIMITE, null, 10);
        assertEquals(ultimaRepeticionEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
        assertEquals(frecuenciaEsperada, eventoDePrueba.getFrecuencia());
    }

    @Test
    public void editarIntervaloSemanal(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, Dia.LUNES.getValorBinario() + Dia.MIERCOLES.getValorBinario(), RepeticionTipo.CANTIDAD_LIMITE,
                null, 10);
        Integer resultadoEsperado = 74;
        LocalDateTime ultimaRepeticionEsperada = LocalDateTime.of(2022, 5, 26, 6, 3);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, Dia.MARTES.getValorBinario()+Dia.JUEVES.getValorBinario()+Dia.DOMINGO.getValorBinario(), RepeticionTipo.CANTIDAD_LIMITE,
                LocalDateTime.of(2023, 4, 21, 12, 30), 10);
        assertEquals(resultadoEsperado, eventoDePrueba.getIntervalo());
        assertEquals(ultimaRepeticionEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
    }
    @Test
    public void editarIntervaloDiario(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1, RepeticionTipo.CANTIDAD_LIMITE,
                null, 10);
        Integer resultadoEsperado = 3;
        LocalDateTime ultimaRepeticionEsperada = LocalDateTime.of(2022, 6, 3, 6, 3);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA,3 , RepeticionTipo.CANTIDAD_LIMITE,
                null, 10);
        assertEquals(resultadoEsperado, eventoDePrueba.getIntervalo());
        assertEquals(ultimaRepeticionEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
    }

    @Test
    public void editarTipoRepeticionDeCantidadAFechaLimite(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1, RepeticionTipo.CANTIDAD_LIMITE,
                null, 10);
        LocalDateTime ultimaRepeticionEsperada = LocalDateTime.of(2023, 4, 21, 12, 30);
        RepeticionTipo tipoEsperado = RepeticionTipo.FECHA_LIMITE;
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA,1 , RepeticionTipo.FECHA_LIMITE,
                LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        assertEquals(ultimaRepeticionEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
        assertEquals(tipoEsperado, eventoDePrueba.getTipo());
    }

    @Test
    public void editarTipoRepeticionDeFechaLimiteAInfinito(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1, RepeticionTipo.FECHA_LIMITE,
                LocalDateTime.of(2023,4,4,4,4), 10);
        LocalDateTime ultimaRepeticionEsperada = LocalDateTime.MAX;
        RepeticionTipo tipoEsperado = RepeticionTipo.INFINITO;
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA,1 , RepeticionTipo.INFINITO,
                null, 0);
        assertEquals(ultimaRepeticionEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
        assertEquals(tipoEsperado, eventoDePrueba.getTipo());
    }
    @Test
    public void editarCantidadDeRepeticiones(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1, RepeticionTipo.CANTIDAD_LIMITE,
                null, 10);
        Integer resultadoEsperado = 1;
        LocalDateTime ultimaRepeticionEsperada = LocalDateTime.of(2022,5,5,6,3);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA,1 , RepeticionTipo.CANTIDAD_LIMITE,
                null, 1);
        assertEquals(resultadoEsperado, eventoDePrueba.getCantidadDeRepeticiones());
        assertEquals(ultimaRepeticionEsperada, eventoDePrueba.getUltimoDiaDeRepeticion());
    }
    @Rule
    public ExpectedException excepcion = ExpectedException.none();
    @Test
    public void editarConNuevaFechaLimiteAnteriorANuevaFechaFinal() throws RuntimeException{
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(201, 4, 21, 12, 30), 0);


    }
    @Test
    public void editarEventoConNombreInvalido(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.NO_TITULO.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        eventoDePrueba.editar(null,"Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void editarEventoConFechaInicialInvalida(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_FALTANTE.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022, 5, 3, 0,0), LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                null, LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void editarEventoConFechaFinallInvalida(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_FALTANTE.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                null, FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void editarEventoConFrecuenciaSemanalEIntervaloMayorA126(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.INTERVALO_INVALIDO.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 13,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 127,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
    }
    @Test
    public void editarEventoConFrecuenciaSemanalEIntervaloMenorA1(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.INTERVALO_INVALIDO.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 34,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 0,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void editarEventoConFechaLimiteInexistente(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.FECHA_LIMITE, null, 0);

    }
    @Test
    public void editarEventoConFechaLimiteAnteriorAFechaFinal(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2021, 4, 21, 12, 30), 0);

    }
    @Test
    public void editarEventoConRepeticionTipoCantidadLimiteConCantidadInvalida(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.REPETICIONES_INVALIDAS.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.CANTIDAD_LIMITE, null, 2);
        eventoDePrueba.editar("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.CANTIDAD_LIMITE, null, -1);
    }
    @Test
    public void eventoConNombreInvalido(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.NO_TITULO.toString());
        var eventoDePrueba = new Evento("","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void eventoConFechaInicialInvalida(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_FALTANTE.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                null, LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void eventoConFechaFinallInvalida(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_FALTANTE.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,1,1,1,1), null,
                FrecuenciaTipo.DIARIA, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void eventoConFrecuenciaSemanalEIntervaloMayorA126(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.INTERVALO_INVALIDO.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 127,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void eventoConFrecuenciaSemanalEIntervaloMenorA1(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.INTERVALO_INVALIDO.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 0,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2023, 4, 21, 12, 30), 0);

    }
    @Test
    public void eventoConFechaLimiteInexistente(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.DIARIA, 0,RepeticionTipo.FECHA_LIMITE, null, 0);

    }
    @Test
    public void eventoConFechaLimiteAnteriorAFechaFinal(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.FECHA_LIMITE, LocalDateTime.of(2021, 4, 21, 12, 30), 0);

    }
    @Test
    public void eventoConRepeticionTipoCantidadLimiteConCantidadInvalida(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.REPETICIONES_INVALIDAS.toString());
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.of(2022,5,4,6,3),
                LocalDateTime.of(2022,5,4,12,45),
                FrecuenciaTipo.SEMANAL, 1,RepeticionTipo.CANTIDAD_LIMITE, null, -2);
    }
}