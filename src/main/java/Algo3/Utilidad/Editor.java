package Algo3.Utilidad;

import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Modelo.Evento;
import Algo3.Modelo.Tarea;
import Algo3.Repeticion.Repeticion;
import Algo3.Repeticion.RepeticionFechaLimite;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Editor {

    private HashMap<ParametroTipo, String> valores;
    private List<String> extras;

    public void cargarValores(HashMap<ParametroTipo, String> valoresAcargar){
        valores = valoresAcargar;
    }

    public void cargarExtras(List<String> extrasAcargar){
        extras = extrasAcargar;
    }


    public void editar(Evento evento){
        var repeticionFrecuencia = extras.get(0).split("R:F");
        Frecuencia frecuencia = Frecuencia.desdeString(repeticionFrecuencia[1]);
        Repeticion repeticion;
        if(RepeticionTipo.valueOf(repeticionFrecuencia[0].split(";")[0]).equals(RepeticionTipo.CANTIDAD_LIMITE)){
            repeticion = Repeticion.desdeString(extras.get(0)+ "R:F" + valores.get(ParametroTipo.FECHAINICIAL));
        }else{
            repeticion = Repeticion.desdeString(repeticionFrecuencia[0]);
        }
        evento.editar(valores.get(ParametroTipo.TITULO),valores.get(ParametroTipo.DESCRIPCION),
                FechaParser.fromString(valores.get(ParametroTipo.FECHAINICIAL)),
                FechaParser.fromString(valores.get(ParametroTipo.FECHAFINAL)),
                        frecuencia, repeticion);
    }

    public void editar(Tarea tarea){
        tarea.editar(valores.get(ParametroTipo.TITULO),valores.get(ParametroTipo.DESCRIPCION),
                FechaParser.fromString(valores.get(ParametroTipo.FECHAINICIAL)),
                FechaParser.fromString(valores.get(ParametroTipo.FECHAFINAL)));
    }
}
