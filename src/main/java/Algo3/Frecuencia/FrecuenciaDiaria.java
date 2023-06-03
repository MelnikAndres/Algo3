package Algo3.Frecuencia;

import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Modelo.Parametros;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FrecuenciaDiaria implements Frecuencia {

    private Integer intervalo;

    public FrecuenciaDiaria(@JsonProperty("intervalo")Integer intervalo){
        if (intervalo < 1){
            throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());
        }

        this.intervalo = intervalo;
    }

    @Override
    public LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        return fechaActual.plusDays(intervalo);
    }
    public FrecuenciaTipo getTipo(){
        return FrecuenciaTipo.DIARIA;
    }

    @Override
    public Parametros getParams() {
        Parametros parametros = new Parametros();
        parametros.agregarParametro("Intervalo", ParametroTipo.ENTERO, intervalo.toString());
        return parametros;
    }

}
