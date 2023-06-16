package Algo3.Controlador;

import Algo3.Componentes.Casilla;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Vista.Calendario.CalendarioMensualVista;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioMensualControlador extends CalendarioControlador{

    private HashMap<LocalDate, Casilla> casillas;
    private ObjectProperty<LocalDate> fecha;
    private CalendarioMensualVista vista;
    private Calendario calendario;
    CalendarioMensualControlador(Calendario calendario, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                                 ObjectProperty<LocalDate> dateValue){
        this.vista = new CalendarioMensualVista();
        this.fecha = dateValue;
        vista.montarVista(widthProperty, heightProperty, dateValue);
        this.casillas = vista.getCasillas();
        this.calendario = calendario;
    }

    @Override
    public void cargarAsignables(Map<Integer, List<LocalDateTime>> repeticiones) {
        vista.cambiarGrilla(fecha.getValue());
        for(Integer asidnableId: repeticiones.keySet()){
            for(LocalDateTime fecha: repeticiones.get(asidnableId)){
                casillas.get(fecha.toLocalDate()).getBase().getChildren().add(vista.crearAsignable(calendario.obtenerAsignablePorId(asidnableId)));
            }
        }
    }

    @Override
    public Node getVista(){return vista;}
}
