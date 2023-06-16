package Algo3.Frecuencia;
import Algo3.Constantes.Dia;
import Algo3.Constantes.FrecuenciaTipo;

import Algo3.Constantes.ParametroTipo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public interface Frecuencia {
    LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual);
    @JsonIgnore
    FrecuenciaTipo getTipo();
    @JsonIgnore
    default String getParams(){return  "null";}
    public static Frecuencia desdeString(String datosJuntos){
        var datosSeparados = datosJuntos.split(";");
        var tipo = FrecuenciaTipo.valueOf(datosSeparados[0]);
        switch (tipo){
            case DIARIA -> {
                return new FrecuenciaDiaria(Integer.parseInt(datosSeparados[1]));
            }
            case SEMANAL -> {
                List<String> diasSplit= List.of(datosSeparados[1].split(","));
                List<Dia> diasDeLaSemana = new ArrayList<>();
                for(String diaStr: diasSplit){
                    Dia dia = Dia.valueOf(diaStr);
                    diasDeLaSemana.add(dia);
                }
                return new FrecuenciaSemanal(diasDeLaSemana);
            }
            case MENSUAL -> {
                return new FrecuenciaMensual();
            }
            case ANUAL -> {
                return new FrecuenciaAnual();
            }
            default -> {
                return null;
            }
        }
    }
}
