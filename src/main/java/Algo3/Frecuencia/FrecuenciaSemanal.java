package Algo3.Frecuencia;

import Algo3.Constantes.Dia;
import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Constantes.ParametroTipo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FrecuenciaSemanal implements Frecuencia {

    private List<Integer> diasDeLaSemana;
    public FrecuenciaSemanal(@JsonProperty("diasDeLaSemana")List<Dia> diasDeLaSemana){
        List<Integer> diasComoValores = new ArrayList<>();
        for(Dia dia: diasDeLaSemana){
            if(diasComoValores.contains(dia.ordinal())){
                throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());
            }
            diasComoValores.add(dia.ordinal());
        }
        if(diasDeLaSemana.size()==0){
            throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());
        }
        this.diasDeLaSemana = diasComoValores;
    }

    @Override
    public LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        int dia = fechaActual.getDayOfWeek().getValue()-1;
        int diaActual = diasDeLaSemana.indexOf(dia);
        LocalDateTime diaSiguiente;
        if(diaActual == -1){
            int minimaDiferencia = 8;
            for(int i = diasDeLaSemana.size()-1;i>=0;i--){
                int differencia = Dia.differencia(
                        Dia.values()[dia],
                        Dia.values()[diasDeLaSemana.get(i)]);
                if(differencia<minimaDiferencia){
                    minimaDiferencia = differencia;
                }
            }
            diaSiguiente = fechaActual.plusDays(minimaDiferencia);
        }else if(diaActual == diasDeLaSemana.size()-1){
            diaSiguiente = fechaActual.plusDays(
                    Dia.differencia(
                            Dia.values()[dia],
                            Dia.values()[diasDeLaSemana.get(0)]));
        }else{
            diaSiguiente = fechaActual.plusDays(
                    Dia.differencia(
                            Dia.values()[dia],
                            Dia.values()[diasDeLaSemana.get(diaActual+1)]));
        }
        return diaSiguiente;
    }
    public FrecuenciaTipo getTipo(){
        return FrecuenciaTipo.SEMANAL;
    }
    public String getParams() {
        return  diasDeLaSemana.stream().map(ordinal->Dia.values()[ordinal].name()).collect(Collectors.joining(","));
    }
}
