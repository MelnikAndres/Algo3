package Algo3.Frecuencia;

import Algo3.Constantes.Dia;
import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Parametros;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrecuenciaSemanal implements Frecuencia {

    private Integer intervalo;

    public FrecuenciaSemanal(List<Dia> diasDeLaSemana){
        final int[] intervalo = {0};
        // No se puede usar una variable que no sea final en una expresion anonima, por eso es una lista de un elemento
        diasDeLaSemana.forEach(dia -> intervalo[0]+=dia.getValor());
        if (intervalo[0] < 1 || intervalo[0] > 126){
            throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());
        }
        this.intervalo = intervalo[0];
    }

    @Override
    public LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        int exponente = fechaActual.getDayOfWeek().getValue() - 1;
        LocalDateTime diaSiguiente = fechaActual;
        while(exponente < 7){
            exponente = (exponente+1)%7;
            diaSiguiente = diaSiguiente.plusDays(1);
            if((intervalo & (1<<exponente)) == (1<<exponente)){
                return diaSiguiente;
            }
        }
        return LocalDateTime.MAX;
    }
    public FrecuenciaTipo getTipo(){
        return FrecuenciaTipo.SEMANAL;
    }
    public Parametros getParams() {
        Parametros parametros = new Parametros();
        parametros.agregarParametro("Intervalo", ParametroTipo.DIASDESEMANA, intervalo.toString());
        return  parametros;
    }
}
