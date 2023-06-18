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
        CeldaMensual celdaMensual = new CeldaMensual(asignable.getTitulo(),asignable.getFechaInicio(),asignable.getFechaFinal());
        agregarAcciones(celdaMensual,asignableId);
        casillas.get(fecha.toLocalDate()).agregarNuevo(celdaMensual);
    }

    @Override
    public Node getVista(){return vista;}

    boolean esAgregable(LocalDateTime fecha) {
        return true;
    }

    @Override
    void limpiarVista() {
        vista.cambiarGrilla(fechaActual.getValue());
    }
}
