package Algo3;

import Algo3.Constantes.Dia;
import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Frecuencia.FrecuenciaDiaria;
import Algo3.Frecuencia.FrecuenciaSemanal;
import Algo3.Repeticion.Repeticion;
import Algo3.Repeticion.RepeticionCantidadLimite;
import Algo3.Repeticion.RepeticionFechaLimite;
import Algo3.Repeticion.RepeticionInfinita;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class RepeticionTest {

    @Rule
    public ExpectedException excepcion = ExpectedException.none();

    @Test
    public void repeticionCantidadLimiteTipo(){
        Repeticion repeticion = new RepeticionCantidadLimite(new FrecuenciaDiaria(1),
                LocalDateTime.of(2023,4,24,4,4),
                3);
        Assert.assertEquals(RepeticionTipo.CANTIDAD_LIMITE, repeticion.getTipo());
    }
    @Test
    public void repeticionCantidadLimiteObtenerUltimoDia(){
        Repeticion repeticion = new RepeticionCantidadLimite(new FrecuenciaDiaria(1),
                LocalDateTime.of(2023,4,24,4,4),
                3);
        LocalDateTime resultadoEsperado =  LocalDateTime.of(2023,4,27,4,4);
        LocalDateTime resultadoObtenido = repeticion.obtenerUltimoDiaDeRepeticion();
        Assert.assertEquals(resultadoEsperado, resultadoObtenido);
    }
    @Test
    public void repeticionCantidadLimiteMenorA0(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.REPETICIONES_INVALIDAS.toString());
        Repeticion repeticion = new RepeticionCantidadLimite(new FrecuenciaDiaria(1),
                LocalDateTime.of(2023,4,24,4,4),
                -1);
    }

    @Test
    public void repeticionFechaLimiteTipo(){
        Repeticion repeticion = new RepeticionFechaLimite(LocalDateTime.of(2023,4,24,4,4));
        Assert.assertEquals(RepeticionTipo.FECHA_LIMITE, repeticion.getTipo());
    }
    @Test
    public void repeticionFechaLimiteObtenerUltimoDia(){
        Repeticion repeticion = new RepeticionFechaLimite(LocalDateTime.of(2023,4,24,4,4));
        LocalDateTime resultadoEsperado =  LocalDateTime.of(2023,4,24,4,4);
        LocalDateTime resultadoObtenido = repeticion.obtenerUltimoDiaDeRepeticion();
        Assert.assertEquals(resultadoEsperado, resultadoObtenido);
    }
    @Test
    public void repeticionCantidadLimiteNula(){
        excepcion.expect(RuntimeException.class);
        excepcion.expectMessage(ErrorTipo.FECHA_FALTANTE.toString());
        Repeticion repeticion = new RepeticionFechaLimite(null);

    }
    @Test
    public void repeticionIntinitaTipo(){
        Repeticion repeticion = new RepeticionInfinita();
        Assert.assertEquals(RepeticionTipo.INFINITO, repeticion.getTipo());
    }
    @Test
    public void repeticionIntinitaObtenerUltimoDia(){
        Repeticion repeticion = new RepeticionInfinita();
        LocalDateTime resultadoEsperado =  LocalDateTime.MAX;
        LocalDateTime resultadoObtenido = repeticion.obtenerUltimoDiaDeRepeticion();
        Assert.assertEquals(resultadoEsperado, resultadoObtenido);
    }
}