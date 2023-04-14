package Algo3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento extends Asignable {
    /* FALTA METODO EDITAR */

    private FrecuenciaTipo frecuencia;
    private List<Integer> intervalo;
    //si el tipo es semanal guarda mas de un valor, para el resto solo tiene un valor
    private RepeticionTipo tipo;
    private Integer cantidadDeRepeticiones;
    private LocalDateTime ultimoDiaDeRepeticion;


    public Evento(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        this.frecuencia = null;
        this.intervalo = null;
        this.tipo = null;
        this.ultimoDiaDeRepeticion = getFechaFinal();
        this.cantidadDeRepeticiones = 0;
    }
    public Evento(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, FrecuenciaTipo frecuencia, List<Integer> intervalo, int cantidadDeRepeticiones) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        this.frecuencia = frecuencia;
        this.intervalo = intervalo;
        this.tipo = RepeticionTipo.CANTIDAD_LIMITE;
        this.ultimoDiaDeRepeticion = obtenerUltimoDiaDeRepeticion(cantidadDeRepeticiones);
        this.cantidadDeRepeticiones = cantidadDeRepeticiones;
    }
    public Evento(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, FrecuenciaTipo frecuencia, List<Integer> intervalo, RepeticionTipo tipo, LocalDateTime ultimoDiaDeRepeticion) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        this.frecuencia = frecuencia;
        this.intervalo = intervalo;
        this.tipo = tipo;
        this.ultimoDiaDeRepeticion = ultimoDiaDeRepeticion;
        this.cantidadDeRepeticiones = 0;
    }
    public List<LocalDateTime> obtenerRepeticionesEnMesyAnio(int numeroDeMes, int anio) {
        /*  Funcion a ejecutar cuando se carga un mes.
            Devuelve una lista con los dias en los que el evento aparece
            en el mes y año recibidos como parametros   */

       List<LocalDateTime> repeticionesDelEvento = new ArrayList<>();
       LocalDateTime primerDiaDelMes = LocalDateTime.of(anio,numeroDeMes,1,0,0);

       //Verificar que tenga sentido calcular apariciones
       if(this.ultimoDiaDeRepeticion != null && this.ultimoDiaDeRepeticion.isBefore(primerDiaDelMes)){
           return repeticionesDelEvento;
       }
       LocalDateTime fechaDeAparicion = getFechaInicio();

       //Llegar al año actual
       while (fechaDeAparicion != null && fechaDeAparicion.getYear() < anio){
           fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
       }
       //llegar al mes actual
       while(fechaDeAparicion != null && fechaDeAparicion.getYear() == anio && fechaDeAparicion.getMonth().getValue() < numeroDeMes){
           fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
       }
       //se pueden fusionar los while anteriores,pero la condicion que queda es horrible

       //Obtener las apariciones del mes actual
       while (fechaDeAparicion != null && (fechaDeAparicion.getYear() == anio
               && fechaDeAparicion.getMonth().getValue() == numeroDeMes)){
           if(this.ultimoDiaDeRepeticion != null && fechaDeAparicion.isAfter(this.ultimoDiaDeRepeticion)){
               break;
           }
            repeticionesDelEvento.add(fechaDeAparicion);
            fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
        }
       return repeticionesDelEvento;
    }
    private LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        /*  Devuelve la siguiente aparicion del evento.
            Se toma en cuenta a partir del dia pasado como parmetro */

        if (frecuencia == FrecuenciaTipo.DIARIA){
            return fechaActual.plusDays(intervalo.get(0));
        }
        if(frecuencia == FrecuenciaTipo.SEMANAL){
            Integer diaActual = fechaActual.getDayOfWeek().getValue();
            int indiceDiaActual = intervalo.indexOf(diaActual);
            Integer siguienteDia;
            if(indiceDiaActual == intervalo.size()-1){
                siguienteDia = intervalo.get(0);
                return fechaActual.plusDays(siguienteDia-diaActual+7);
            }
            siguienteDia = intervalo.get(indiceDiaActual+1);
            return fechaActual.plusDays(Math.abs(siguienteDia-diaActual));
        }
        if(frecuencia == FrecuenciaTipo.MENSUAL){
            return fechaActual.plusMonths(1);
        }

        if (frecuencia == FrecuenciaTipo.ANUAL){
            return fechaActual.plusYears(1);
        }
        return null;
    }
    private LocalDateTime obtenerUltimoDiaDeRepeticion(int cantidadDeRepeticiones){
        LocalDateTime ultimaRepeticion = getFechaInicio();
        while (cantidadDeRepeticiones > 0){
            ultimaRepeticion = obtenerSiguienteAparicion(ultimaRepeticion);
            cantidadDeRepeticiones-=1;
        }
        return ultimaRepeticion;
    }

}
