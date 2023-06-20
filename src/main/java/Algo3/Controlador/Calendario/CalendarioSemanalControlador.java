package Algo3.Controlador.Calendario;

import Algo3.Componentes.CeldaSemanal;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Vista.Calendario.CalendarioSemanalVista;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalendarioSemanalControlador extends CalendarioControlador {

    private CalendarioSemanalVista vista;
    private List<VBox> containers;

    public CalendarioSemanalControlador(Calendario calendario, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                                        ObjectProperty<LocalDate> dateValue){
        fechaActual = dateValue;
        vista = new CalendarioSemanalVista();
        vista.montarVista(widthProperty,heightProperty, dateValue);
        containers = vista.getContainers();
        this.calendario = calendario;
    }

    @Override
    void cargarAsignable(Integer asignableId, LocalDateTime fecha) {
        Asignable asignable = calendario.obtenerAsignablePorId(asignableId);
        long duracionDias = asignable.getFechaInicio().toLocalDate().until(asignable.getFechaFinal().toLocalDate(), ChronoUnit.DAYS);
        long duracionMinutos = asignable.getFechaInicio().until(asignable.getFechaFinal(), ChronoUnit.MINUTES);

        if(duracionDias == 0){
            int diferenciaDeDias = (int) fechaActual.get().until(fecha.toLocalDate(),ChronoUnit.DAYS);
            if(diferenciaDeDias>6 ||diferenciaDeDias<0){
                return;
            }
            LocalDateTime fechaFinal = fecha.plusMinutes(duracionDias);
            var nuevaCelda = new CeldaSemanal(asignable.getTitulo(), fecha,fechaFinal, fechaActual.get());
            containers.get(diferenciaDeDias).getChildren().add(nuevaCelda);
            agregarAcciones(nuevaCelda,asignableId);
        }else{
            for(int i = 0; i<= duracionDias; i++){
                int diferenciaDeDias = (int) fechaActual.get().until(fecha.plusDays(i).toLocalDate(),ChronoUnit.DAYS);
                if(diferenciaDeDias<=6 && diferenciaDeDias>=0){
                    var nuevaCelda = new CeldaSemanal(asignable.getTitulo(), fecha,fecha.plusMinutes(duracionMinutos), fechaActual.get().plusDays(diferenciaDeDias));
                    containers.get(diferenciaDeDias).getChildren().add(nuevaCelda);
                    agregarAcciones(nuevaCelda,asignableId);
                }
            }
        }
    }

    @Override
    public Node getVista() {
        return vista;
    }

    @Override
    void limpiarVista() {
        for(VBox container: containers){
            container.getChildren().clear();
        }
    }
}
