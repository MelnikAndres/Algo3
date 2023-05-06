package Algo3;

import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Frecuencia.FrecuenciaDiaria;
import Algo3.Repeticion.Repeticion;
import Algo3.Repeticion.RepeticionCantidadLimite;
import Algo3.Repeticion.RepeticionFechaLimite;
import Algo3.Repeticion.RepeticionInfinita;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;


import static org.junit.Assert.*;

public class RepeticionTest {



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

        Assert.assertThrows(ErrorTipo.REPETICIONES_INVALIDAS.toString(), RuntimeException.class, () -> {
            Repeticion repeticion = new RepeticionCantidadLimite(new FrecuenciaDiaria(1),
                    LocalDateTime.of(2023,4,24,4,4),
                    -1);
        });
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

        assertThrows(ErrorTipo.FECHA_FALTANTE.toString(), RuntimeException.class, () -> {

            Repeticion repeticion = new RepeticionFechaLimite(null);
        });

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