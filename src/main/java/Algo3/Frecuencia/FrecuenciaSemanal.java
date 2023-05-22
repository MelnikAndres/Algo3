package Algo3.Frecuencia;

import Algo3.Constantes.Dia;
import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Parametros;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDateTime;
import java.util.List;
public class FrecuenciaSemanal implements Frecuencia {

    private Integer intervalo;
    public FrecuenciaSemanal(List<Dia> diasDeLaSemana){
        int intervalo = 0;
        for(Dia dia: diasDeLaSemana){
            if((intervalo & dia.getValor()) != 0){
                throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());
            }
            intervalo+=dia.getValor();
        }
        if(intervalo == 0){
            throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());
        }
        this.intervalo = intervalo;
    }
    @JsonCreator
    FrecuenciaSemanal(@JsonProperty("intervalo")Integer intervalo){
        this.intervalo = intervalo;
    }

    @Override
    public LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        int exponente = fechaActual.getDayOfWeek().getValue() - 1;
        LocalDateTime diaSiguiente = fechaActual;
        exponente = (exponente+1)%7;
        diaSiguiente = diaSiguiente.plusDays(1);
        if((intervalo & (1<<exponente)) == (1<<exponente)){
            return diaSiguiente;
        }
        return obtenerSiguienteAparicion(diaSiguiente);
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
