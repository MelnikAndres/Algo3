package Algo3.Modelo;

import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Repeticion.Repeticion;
import Algo3.Utilidad.Completador;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Evento extends Asignable {


    private Frecuencia frecuencia;
    private Repeticion repeticion;
    private LocalDateTime ultimoDiaDeRepeticion;

    public Evento(    @JsonProperty("titulo") String titulo,  @JsonProperty("descripcion") String descripcion,
               @JsonProperty("fechaInicio") LocalDateTime fechaInicio,  @JsonProperty("fechaFinal") LocalDateTime fechaFinal,
               @JsonProperty("frecuencia") Frecuencia frecuencia, @JsonProperty("repeticion")Repeticion repeticion) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        this.frecuencia = frecuencia;
        this.repeticion = repeticion;
        if(obtenerUltimoDiaDeRepeticion().isBefore(fechaInicio)){
            throw new RuntimeException(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        }
        this.ultimoDiaDeRepeticion = obtenerUltimoDiaDeRepeticion();
    }

    @JsonIgnore
    public FrecuenciaTipo getFrecuenciaTipo() {
        return frecuencia.getTipo();
    }
    @JsonIgnore
    public RepeticionTipo getRepeticionTipo() {
        return repeticion.getTipo();
    }
    @JsonIgnore
    public String getParametrosFrecuencia() {
        return frecuencia.getParams();
    }
    @JsonIgnore
    public String getParametrosRepeticion() {
        return repeticion.getParams();
    }
    public LocalDateTime getUltimoDiaDeRepeticion(){return ultimoDiaDeRepeticion;}

    public List<LocalDateTime> obtenerAparicionesEnMesyAnio(int numeroDeMes, int anio) {
        /*  Funcion a ejecutar cuando se carga un mes.
            Devuelve una lista con los dias en los que el evento aparece
            en el mes y año recibidos como parametros   */

       List<LocalDateTime> repeticionesDelEvento = new ArrayList<>();
       LocalDateTime primerDiaDelMes = LocalDateTime.of(anio,numeroDeMes,1,0,0);

       //Verificar que tenga sentido calcular apariciones
       if(ultimoDiaDeRepeticion.isBefore(primerDiaDelMes)){
           return repeticionesDelEvento;
       }

       LocalDateTime fechaDeAparicion = iterarHastaMesyAnio(numeroDeMes,anio);

       //Obtener las apariciones del mes actual
       while (
               fechaDeAparicion != null
               && (fechaDeAparicion.getYear() == anio
               && fechaDeAparicion.getMonth().getValue() == numeroDeMes)
               && !ultimoDiaDeRepeticion.isBefore(fechaDeAparicion)){
            repeticionesDelEvento.add(fechaDeAparicion);
            fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);

        }
        return repeticionesDelEvento;
    }


    public void editar(String nuevoTitulo, String nuevaDescripcion, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFinal,
                       Frecuencia nuevaFrecuencia, Repeticion nuevaRepeticion) throws RuntimeException{
        super.editar(nuevoTitulo, nuevaDescripcion, nuevaFechaInicio, nuevaFechaFinal);

        this.frecuencia = nuevaFrecuencia;
        this.repeticion = nuevaRepeticion;

        if(obtenerUltimoDiaDeRepeticion().isBefore(nuevaFechaInicio)){
            throw new RuntimeException(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        }

        this.ultimoDiaDeRepeticion = obtenerUltimoDiaDeRepeticion();

    }


    private LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        /*  Devuelve la siguiente aparicion del evento.
            Se toma en cuenta a partir del dia pasado como parametro */
        if(frecuencia == null){
            return LocalDateTime.MAX;
        }
        return frecuencia.obtenerSiguienteAparicion(fechaActual);
    }
    private LocalDateTime obtenerUltimoDiaDeRepeticion(){
        if(repeticion == null){
            return getFechaInicio();
        }
        return repeticion.obtenerUltimoDiaDeRepeticion();

    }
    private LocalDateTime iterarHastaMesyAnio(int numeroDeMes, int anio){
        var fechaDeAparicion = getFechaInicio();
        if(frecuencia == null){
            return fechaDeAparicion;
        }
        //Llegar al año actual
        while (fechaDeAparicion.getYear() < anio){
            fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
        }
        //llegar al mes actual
        while(fechaDeAparicion.getYear() == anio && fechaDeAparicion.getMonth().getValue() < numeroDeMes){
            fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
        }
        //se pueden fusionar los while anteriores,pero la condicion que queda es horrible
        return  fechaDeAparicion;
    }
    @Override
    public String toString(){
        String data = super.toString();
        data = data.concat(", " + ultimoDiaDeRepeticion.toString());
        data = data.concat(", "+getParametrosFrecuencia());
        data = data.concat(", "+getParametrosRepeticion());
        return data;
    }
    @Override
    public Map<ParametroTipo,String> obtenerParametros(){
        var parametros = super.obtenerParametros();
        parametros.put(ParametroTipo.TIPO,"Evento");
        var repeticionFrecuencia =
                getRepeticionTipo().name()+";"+getParametrosRepeticion()+
                "R:F"+
                getFrecuenciaTipo().name()+";"+getParametrosFrecuencia();
        parametros.put(ParametroTipo.REPETICION_FRECUENCIA,repeticionFrecuencia);
        return parametros;
    }
}