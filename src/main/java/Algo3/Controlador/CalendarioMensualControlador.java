package Algo3.Controlador;

import Algo3.Componentes.Casilla;
import Algo3.Modelo.Asignable;
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
    CalendarioMensualControlador(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                                 ObjectProperty<LocalDate> dateValue){
        this.vista = new CalendarioMensualVista();
        vista.montarVista(widthProperty, heightProperty, dateValue);
        this.casillas = vista.getCasillas();


    }
    public void cargarAsignables(Map<Asignable, List<LocalDateTime>> repeticiones){
        if(repeticiones.isEmpty()) return;
        for(Asignable asignable: repeticiones.keySet()){
            for(LocalDateTime fecha: repeticiones.get(asignable)){
                casillas.get(fecha.toLocalDate()).getBase().getChildren().add(vista.crearAsignable(asignable));
            }
        }
    }

    @Override
    public Node getVista(){return vista;}
}
