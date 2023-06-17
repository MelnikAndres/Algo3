package Algo3.Controlador;

import Algo3.Componentes.Casilla;
import Algo3.Componentes.CeldaMensual;
import Algo3.Modelo.Alarma;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Utilidad.AlarmaEvento;
import Algo3.Vista.Calendario.CalendarioMensualVista;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
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
        for(Integer asignableId : repeticiones.keySet()){
            for(LocalDateTime fecha: repeticiones.get(asignableId)){
                CeldaMensual celdaMensual = vista.crearAsignable();
                Asignable asignable = calendario.obtenerAsignablePorId(asignableId);
                celdaMensual.getTituloLabel().setText(asignable.getTitulo());
                agregarAcciones(celdaMensual,asignableId);
                casillas.get(fecha.toLocalDate()).getBase().getChildren().add(celdaMensual);
            }
        }
    }

    private void agregarAcciones(CeldaMensual celdaMensual, Integer id){
        Asignable asignable = calendario.obtenerAsignablePorId(id);
        celdaMensual.getBotonBorrar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                calendario.eliminar(id);
                var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fecha.get().getMonthValue(), fecha.get().getYear());
                cargarAsignables(aparicionesActuales);
                try {
                    calendario.serializar(new FileOutputStream(System.getProperty("user.dir")+"\\calendario.json"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        celdaMensual.getBotonEditar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoEditarControlador controlador = new DialogoEditarControlador((Stage)vista.getScene().getWindow());
                controlador.cargarValores(asignable.obtenerParametros());
                Asignable resultado = controlador.abrirYeditar();
                if(resultado == null){
                    return;
                }
                calendario.editar(id, resultado);
                try {
                    calendario.serializar(new FileOutputStream(System.getProperty("user.dir")+"\\calendario.json"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fecha.get().getMonthValue(), fecha.get().getYear());
                cargarAsignables(aparicionesActuales);
            }
        });
        celdaMensual.getBotonAlarma().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoAlarmaControlador dialogoAlarmaControlador = new DialogoAlarmaControlador((Stage)vista.getScene().getWindow(),asignable.getAlarmas());
                Alarma resultado = dialogoAlarmaControlador.abrirYcrear(asignable.getFechaInicio());
                if(resultado != null){
                    asignable.agregarAlarma(resultado);
                    vista.fireEvent(new AlarmaEvento(AlarmaEvento.NUEVA_ALARMA));
                    try {
                        calendario.serializar(new FileOutputStream(System.getProperty("user.dir")+"\\calendario.json"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    public Node getVista(){return vista;}
}
