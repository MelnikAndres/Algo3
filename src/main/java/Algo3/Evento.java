package Algo3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento extends Asignable {
    /* NADA DE ESTE CODIGO ESTA TESTEADO, ESTA CLASE NO ESTA TERMINADA */

    private FrecuenciaTipo frecuencia;
    private List<Integer> intervalo;
    //si el tipo es semanal guarda mas de un valor, para el resto solo tiene un valor
    private RepeticionTipo tipo;
    private LocalDateTime ultimoDiaDeRepeticion;
    //UltimoDiaDeRepeticion hay que calcularlo, puede ser antes de instanciar o puede ser,falta implementar
    //Se calcula sabiendo el tipo de repeticion

    public Evento(FrecuenciaTipo frecuencia, List<Integer> intervalo, RepeticionTipo tipo, LocalDateTime ultimoDiaDeRepeticion) {
        this.frecuencia = frecuencia;
        this.intervalo = intervalo;
        this.tipo = tipo;
        this.ultimoDiaDeRepeticion = ultimoDiaDeRepeticion;
    }

    public List<LocalDateTime> ObtenerRepeticionesEnMesyAnio(int numeroDeMes, int anio) {
        /*  Funcion a ejecutar cuando se carga un mes.
            Devuelve una lista con los dias en los que el evento aparece
            en el mes y año recibidos como parametros   */

       List<LocalDateTime> repeticionesDelEvento = new ArrayList<>();
       LocalDateTime primerDiaDelMes = LocalDateTime.of(anio,numeroDeMes,1,0,0);

       //Verificar que tenga sentido calcular apariciones
       if(this.ultimoDiaDeRepeticion != null && (this.ultimoDiaDeRepeticion.isBefore(primerDiaDelMes) ||
               this.fechaFinal.isBefore(primerDiaDelMes))
       ){
           return repeticionesDelEvento;
       }
       LocalDateTime fechaDeAparicion = this.fechaInicio;

       //Llegar al mes actual
       while (fechaDeAparicion.getYear() != anio && fechaDeAparicion.getMonth().getValue() != numeroDeMes){
           fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
       }
       //Obtener las apariciones del mes actual
       while (fechaDeAparicion.getYear() == anio && fechaDeAparicion.getMonth().getValue() == numeroDeMes){
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
            Integer siguienteDia = intervalo.get(intervalo.indexOf(diaActual)+1);
            return fechaActual.plusDays(Math.abs(siguienteDia-diaActual));
        }
        if(frecuencia == FrecuenciaTipo.MENSUAL){
            return fechaActual.plusMonths(1);
        }

        //if (frecuencia == FrecuenciaTipo.ANUAL)
        return fechaActual.plusYears(1);
    }
}
