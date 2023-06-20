package Algo3.Controlador.Calendario;

import Algo3.Componentes.Casilla;
import Algo3.Componentes.CeldaMensual;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Vista.Calendario.CalendarioMensualVista;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class CalendarioMensualControlador extends CalendarioControlador{

    private HashMap<LocalDate, Casilla> casillas;
    private CalendarioMensualVista vista;
    public CalendarioMensualControlador(Calendario calendario, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                                        ObjectProperty<LocalDate> dateValue){
        this.vista = new CalendarioMensualVista();
        this.fechaActual = dateValue;
        vista.montarVista(widthProperty, heightProperty, dateValue);
        this.casillas = vista.getCasillas();
        this.calendario = calendario;
    }

    void cargarAsignable(Integer asignableId, LocalDateTime fecha) {
        Asignable asignable = calendario.obtenerAsignablePorId(asignableId);
        long duracionDias = asignable.getFechaInicio().toLocalDate().until(asignable.getFechaFinal().toLocalDate(), ChronoUnit.DAYS);
        long duracionMinutos = asignable.getFechaInicio().until(asignable.getFechaFinal(), ChronoUnit.MINUTES);

        if(duracionDias == 0){
            LocalDateTime fechaFinal = fecha.plusMinutes(duracionDias);
            CeldaMensual celdaMensual = new CeldaMensual(asignable.getTitulo(), fecha,fechaFinal,fechaActual.get());
            agregarAcciones(celdaMensual,asignableId);
            casillas.get(fecha.toLocalDate()).agregarNuevo(celdaMensual);
        }else{
            for(int i = 0; i<= duracionDias; i++){
                CeldaMensual celdaMensual = new CeldaMensual(asignable.getTitulo(),fecha,fecha.plusMinutes(duracionMinutos), fecha.plusDays(i).toLocalDate());
                agregarAcciones(celdaMensual,asignableId);
                Casilla casilla = casillas.get(fecha.plusDays(i).toLocalDate());
                if(casilla!= null){
                    casilla.agregarNuevo(celdaMensual);
                }
            }
        }

    }

    @Override
    public Node getVista(){return vista;}

    @Override
    void limpiarVista() {
        vista.cambiarGrilla(fechaActual.getValue());
    }
}
