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
        var diferenciaDeDias = fecha.getDayOfMonth() - fechaActual.get().getDayOfMonth();
        Asignable asignable = calendario.obtenerAsignablePorId(asignableId);
        var nuevaCelda = new CeldaSemanal(asignable.getTitulo(), asignable.getFechaInicio(),asignable.getFechaFinal());
        containers.get(diferenciaDeDias).getChildren().add(nuevaCelda);
        agregarAcciones(nuevaCelda,asignableId);
    }

    @Override
    public Node getVista() {
        return vista;
    }

    @Override
    boolean esAgregable(LocalDateTime fecha) {
        var diferenciaDeDias = fecha.getDayOfMonth() - fechaActual.get().getDayOfMonth();
        return diferenciaDeDias >=0 && diferenciaDeDias<7;
    }

    @Override
    void limpiarVista() {
        for(VBox container: containers){
            container.getChildren().clear();
        }
    }
}
