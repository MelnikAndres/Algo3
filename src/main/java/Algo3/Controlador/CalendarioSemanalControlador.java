package Algo3.Controlador;

import Algo3.Componentes.Apilable;
import Algo3.Componentes.CeldaSemanal;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Vista.Calendario.CalendarioDiarioVista;
import Algo3.Vista.Calendario.CalendarioSemanalVista;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CalendarioSemanalControlador extends CalendarioControlador {

    private CalendarioSemanalVista vista;
    private ObjectProperty<LocalDate> fechaActual;
    private List<VBox> containers;
    private Calendario calendario;

    public CalendarioSemanalControlador(Calendario calendario, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                                        ObjectProperty<LocalDate> dateValue){
        fechaActual = dateValue;
        vista = new CalendarioSemanalVista();
        vista.montarVista(widthProperty,heightProperty, dateValue);
        containers = vista.getContainers();
        this.calendario = calendario;
    }

    @Override
    public void cargarAsignables(Map<Integer, List<LocalDateTime>> repeticiones) {
        for(VBox container: containers){
            container.getChildren().clear();
        }
        for(Integer asignableId: repeticiones.keySet()){
            for(LocalDateTime fecha: repeticiones.get(asignableId)){
                var diferenciaDeDias = fecha.getDayOfMonth() - fechaActual.get().getDayOfMonth();
                if(diferenciaDeDias >=0 && diferenciaDeDias<7){
                    Asignable asignable = calendario.obtenerAsignablePorId(asignableId);
                    var nuevaCelda = new CeldaSemanal(asignable.getTitulo(), asignable.getFechaInicio(),asignable.getFechaFinal());
                    containers.get(diferenciaDeDias).getChildren().add(nuevaCelda);
                }
            }
        }
    }

    @Override
    public Node getVista() {
        return vista;
    }
}
