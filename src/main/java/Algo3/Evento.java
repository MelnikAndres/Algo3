package Algo3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento extends Asignable {
    /* FALTA TESTEAR EL METODO EDITAR (Quizas se puede separar en distintos métodos que reciban
    argumentos distintos, pero me pareció que quedaba mas claro así) */

    private FrecuenciaTipo frecuencia;
    private Integer intervalo;
    private RepeticionTipo tipo;
    private Integer cantidadDeRepeticiones;
    private LocalDateTime ultimoDiaDeRepeticion;



    public Evento(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, FrecuenciaTipo frecuencia, int intervalo, RepeticionTipo tipo, LocalDateTime ultimoDiaDeRepeticion, int cantidadDeRepeticiones) {
        super(titulo, descripcion, fechaInicio, fechaFinal);
        if(frecuencia == FrecuenciaTipo.SEMANAL)
            if (intervalo < 1 || intervalo > 126)
                throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());

        if (tipo == RepeticionTipo.FECHA_LIMITE)
            if (ultimoDiaDeRepeticion == null || ultimoDiaDeRepeticion.isBefore(fechaFinal))
                throw new RuntimeException(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());

        if (tipo == RepeticionTipo.CANTIDAD_LIMITE)
            if (cantidadDeRepeticiones < 0)
                throw new RuntimeException(ErrorTipo.REPETICIONES_INVALIDAS.toString());
        this.frecuencia = frecuencia;
        this.intervalo = Math.abs(intervalo);
        this.tipo = tipo;
        this.ultimoDiaDeRepeticion = obtenerUltimoDiaDeRepeticion(cantidadDeRepeticiones, ultimoDiaDeRepeticion);
        this.cantidadDeRepeticiones = cantidadDeRepeticiones;
    }

    public FrecuenciaTipo getFrecuencia() {
        return frecuencia;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public RepeticionTipo getTipo() {
        return tipo;
    }

    public Integer getCantidadDeRepeticiones() {
        return cantidadDeRepeticiones;
    }

    public LocalDateTime getUltimoDiaDeRepeticion() {
        return ultimoDiaDeRepeticion;
    }

    public List<LocalDateTime> obtenerRepeticionesEnMesyAnio(int numeroDeMes, int anio) {
        /*  Funcion a ejecutar cuando se carga un mes.
            Devuelve una lista con los dias en los que el evento aparece
            en el mes y año recibidos como parametros   */

       List<LocalDateTime> repeticionesDelEvento = new ArrayList<>();
       LocalDateTime primerDiaDelMes = LocalDateTime.of(anio,numeroDeMes,1,0,0);

       //Verificar que tenga sentido calcular apariciones
       if(ultimoDiaDeRepeticion != null && ultimoDiaDeRepeticion.isBefore(primerDiaDelMes)){
           return repeticionesDelEvento;
       }

       LocalDateTime fechaDeAparicion = iterarHastaMesyAnio(numeroDeMes,anio);

       //Obtener las apariciones del mes actual
       while (fechaDeAparicion != null && (fechaDeAparicion.getYear() == anio
               && fechaDeAparicion.getMonth().getValue() == numeroDeMes)){
           if(ultimoDiaDeRepeticion != null && ultimoDiaDeRepeticion.isBefore(fechaDeAparicion)){
               break;
           }
            repeticionesDelEvento.add(fechaDeAparicion);
            fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
        }
        return repeticionesDelEvento;
    }


    public void editar(String nuevoTitulo, String nuevaDescripcion, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFinal,
                             FrecuenciaTipo nuevaFrecuencia, int nuevoIntervalo, RepeticionTipo nuevoTipo, LocalDateTime nuevaFechaLimite, int nuevaCantidadDeRepeticiones) throws RuntimeException{
        super.editar(nuevoTitulo, nuevaDescripcion, nuevaFechaInicio, nuevaFechaFinal);
        if(nuevaFrecuencia == FrecuenciaTipo.SEMANAL)
            if (nuevoIntervalo < 1 || nuevoIntervalo > 126)
                throw new RuntimeException(ErrorTipo.INTERVALO_INVALIDO.toString());

            if (nuevoTipo == RepeticionTipo.FECHA_LIMITE)
                if (nuevaFechaLimite == null || nuevaFechaLimite.isBefore(nuevaFechaFinal))
                    throw new RuntimeException(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());

            if (nuevoTipo == RepeticionTipo.CANTIDAD_LIMITE)
                if (nuevaCantidadDeRepeticiones < 0)
                    throw new RuntimeException(ErrorTipo.REPETICIONES_INVALIDAS.toString());

            this.frecuencia = nuevaFrecuencia;
            this.intervalo = nuevoIntervalo;
            this.tipo = nuevoTipo;
            this.cantidadDeRepeticiones = (int)Math.abs(nuevaCantidadDeRepeticiones);
            this.ultimoDiaDeRepeticion = obtenerUltimoDiaDeRepeticion(nuevaCantidadDeRepeticiones, nuevaFechaLimite);



    }


    private LocalDateTime obtenerSiguienteAparicion(LocalDateTime fechaActual){
        /*  Devuelve la siguiente aparicion del evento.
            Se toma en cuenta a partir del dia pasado como parmetro */

        if (frecuencia == FrecuenciaTipo.DIARIA){
            return fechaActual.plusDays(intervalo);
        }
        if(frecuencia == FrecuenciaTipo.SEMANAL){
            int exponente = fechaActual.getDayOfWeek().getValue() - 1;
            LocalDateTime diaSiguiente = fechaActual;
            while(exponente < 7){
                exponente = (exponente+1)%7;
                diaSiguiente = diaSiguiente.plusDays(1);
                if((intervalo & (1<<exponente)) == (1<<exponente)){
                    return diaSiguiente;
                }
            }
            /*int indiceDiaActual = intervalo.indexOf(diaActual);
            Integer siguienteDia;
            if(indiceDiaActual == intervalo.size()-1){
                siguienteDia = intervalo.get(0);
                return fechaActual.plusDays(siguienteDia-diaActual+7);
            }
            siguienteDia = intervalo.get(indiceDiaActual+1);
            return fechaActual.plusDays(Math.abs(siguienteDia-diaActual));*/
        }
        if(frecuencia == FrecuenciaTipo.MENSUAL){
            return fechaActual.plusMonths(1);
        }

        if (frecuencia == FrecuenciaTipo.ANUAL){
            return fechaActual.plusYears(1);
        }
        return null;
    }
    private LocalDateTime obtenerUltimoDiaDeRepeticion(int cantidadDeRepeticiones, LocalDateTime fechaLimite){
        if(this.tipo == RepeticionTipo.CANTIDAD_LIMITE) {
            LocalDateTime ultimaRepeticion = getFechaInicio();
            while (cantidadDeRepeticiones > 0) {
                ultimaRepeticion = obtenerSiguienteAparicion(ultimaRepeticion);
                cantidadDeRepeticiones -= 1;
            }
            return ultimaRepeticion;
        }else if(this.tipo == RepeticionTipo.INFINITO)
            return LocalDateTime.MAX;
        else
            return fechaLimite;
    }
    private LocalDateTime iterarHastaMesyAnio(int numeroDeMes, int anio){
        var fechaDeAparicion = getFechaInicio();
        //Llegar al año actual
        while (fechaDeAparicion != null && fechaDeAparicion.getYear() < anio){
            fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
        }
        //llegar al mes actual
        while(fechaDeAparicion != null && fechaDeAparicion.getYear() == anio && fechaDeAparicion.getMonth().getValue() < numeroDeMes){
            fechaDeAparicion = obtenerSiguienteAparicion(fechaDeAparicion);
        }
        //se pueden fusionar los while anteriores,pero la condicion que queda es horrible
        return  fechaDeAparicion;
    }

}
