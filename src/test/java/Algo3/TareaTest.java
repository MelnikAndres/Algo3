package Algo3;

import Algo3.Constantes.ErrorTipo;
import org.junit.Assert;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void tareaValida(){
        var tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        var resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add(LocalDateTime.of(2022,3,3,0,0));
        //act
        var repeticionesObtenidas = tarea.obtenerAparicionesEnMesyAnio(3, 2022);
        assertEquals(resultadoEsperado,repeticionesObtenidas);
    }
    @Test
    public void cambiarEstadoTarea(){
        var tarea = new Tarea("Tarea", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        tarea.cambiarEstadoCompletada();
        assertTrue(tarea.getEsCompletada());
    }
    @Test
    public void crearTareaConTituloInvalido(){
        Assert.assertThrows(ErrorTipo.NO_TITULO.toString(), RuntimeException.class, () -> {
            var tarea = new Tarea("", "Descripcion",
                    LocalDateTime.of(2022, 3, 3, 0, 0),
                    LocalDateTime.of(2022, 3, 3, 12, 0));
        });
    }
    @Test
    public void crearTareaConFechaInicialNula(){

        Assert.assertThrows(ErrorTipo.FECHA_FALTANTE.toString(), RuntimeException.class, () -> {
            var tarea = new Tarea("Prueba", "Descripcion",
                    null,
                    LocalDateTime.of(2022, 3, 3, 12, 0));
        });
    }
    @Test
    public void crearTareaConFechaFinalNula(){

        Assert.assertThrows(ErrorTipo.FECHA_FALTANTE.toString(), RuntimeException.class, () -> {
            var tarea = new Tarea("Prueba", "Descripcion",
                    LocalDateTime.of(2022, 3, 3, 12, 0),
                    null);
        });
    }
    @Test
    public void crearTareaConFechasInvalidas(){
        Assert.assertThrows(ErrorTipo.FECHA_INICIO_INVALIDA.toString(), RuntimeException.class, () -> {
            var tarea = new Tarea("Prueba", "Descripcion",
                    LocalDateTime.of(2023,3,3,0,0),
                    LocalDateTime.of(2022, 3, 3, 12, 0));
        });
    }

    @Test
    public void editarTareaConTituloInvalido(){

        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));

        Assert.assertThrows(ErrorTipo.NO_TITULO.toString(), RuntimeException.class, () -> tarea.editar("", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0)));
    }
    @Test
    public void editarTareaConFechaInicioNula(){

        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));

        Assert.assertThrows(ErrorTipo.FECHA_FALTANTE.toString(), RuntimeException.class, () -> tarea.editar("Prueba", "Descripcion",
                null,
                LocalDateTime.of(2022, 3, 3, 12, 0)));
    }
    @Test
    public void editarTareaConFechaFinalNula(){

        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        Assert.assertThrows(ErrorTipo.FECHA_FALTANTE.toString(), RuntimeException.class, () -> tarea.editar("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 12, 0),
                null));

    }
    @Test
    public void editarTareaConFechasInvalidas(){
        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        Assert.assertThrows(ErrorTipo.FECHA_INICIO_INVALIDA.toString(), RuntimeException.class, () -> tarea.editar("Prueba", "Descripcion",
                LocalDateTime.of(2023,3,3,0,0),
                LocalDateTime.of(2022, 3, 3, 12, 0)));
    }
    @Test
    public void editarTareaConTituloValido(){
        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        String resultadoEsperado = "Otro titulo";
        tarea.editar("Otro titulo", "Descripcion",
                LocalDateTime.of(2022,3,3,0,0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        assertEquals(resultadoEsperado, tarea.getTitulo());
    }
    @Test
    public void editarTareaConDescripcionValida(){
        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        String resultadoEsperado = "nueva descripcion";
        tarea.editar("Prueba", "nueva descripcion",
                LocalDateTime.of(2022,3,3,0,0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        assertEquals(resultadoEsperado, tarea.getDescripcion());
    }

    @Test
    public void editarTareaConFechaInicialValida(){
        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        LocalDateTime resultadoEsperado = LocalDateTime.of(2021,3,3,0,0);
        tarea.editar("Prueba", "Descripcion",
                LocalDateTime.of(2021,3,3,0,0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        assertEquals(resultadoEsperado, tarea.getFechaInicio());
    }
    @Test
    public void editarTareaConFechaFinalValida(){
        var tarea = new Tarea("Prueba", "Descripcion",
                LocalDateTime.of(2022, 3, 3, 0, 0),
                LocalDateTime.of(2022, 3, 3, 12, 0));
        LocalDateTime resultadoEsperado = LocalDateTime.of(2023,3,3,12,0);
        tarea.editar("Prueba", "Descripcion",
                LocalDateTime.of(2022,3,3,0,0),
                LocalDateTime.of(2023, 3, 3, 12, 0));
        assertEquals(resultadoEsperado, tarea.getFechaFinal());
    }
}