package Algo3.Controlador;

import Algo3.Componentes.Apilable;
import Algo3.Componentes.CeldaSemanal;
import Algo3.Modelo.Alarma;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Utilidad.AlarmaEvento;
import Algo3.Utilidad.Completador;
import Algo3.Vista.Calendario.CalendarioDiarioVista;
import Algo3.Vista.Calendario.CalendarioSemanalVista;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
                    agregarAcciones(nuevaCelda,asignableId);
                }
            }
        }
    }

    private void agregarAcciones(CeldaSemanal celdaSemanal, Integer id){
        Asignable asignable = calendario.obtenerAsignablePorId(id);
        celdaSemanal.addBorrarEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                calendario.eliminar(id);
                var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaActual.get().getMonthValue(), fechaActual.get().getYear());
                cargarAsignables(aparicionesActuales);
            }
        });
        celdaSemanal.addEditarEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoEditarControlador controlador = new DialogoEditarControlador((Stage)vista.getScene().getWindow());
                controlador.cargarValores(asignable.obtenerParametros());
                Asignable resultado = controlador.abrirYeditar();
                if(resultado == null){
                    return;
                }
                calendario.editar(id, resultado);
                var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaActual.get().getMonthValue(), fechaActual.get().getYear());
                cargarAsignables(aparicionesActuales);
            }
        });
        celdaSemanal.addAlarmaEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoAlarmaControlador dialogoAlarmaControlador = new DialogoAlarmaControlador((Stage)vista.getScene().getWindow(),asignable.getAlarmas());
                Alarma resultado = dialogoAlarmaControlador.abrirYcrear(asignable.getFechaInicio());
                if(resultado != null){
                    asignable.agregarAlarma(resultado);
                    vista.fireEvent(new AlarmaEvento(AlarmaEvento.NUEVA_ALARMA));
                }
            }
        });
        probarEsCompletable(asignable, celdaSemanal);
    }

    private void probarEsCompletable(Asignable asignable, CeldaSemanal celdaSemanal){
        Completador completador = new Completador(false);
        if(completador.esRecibido(asignable)){
            celdaSemanal.setEsTarea(true);
            celdaSemanal.addCheckBoxListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    asignable.recibirCompletador(new Completador(t1));
                }
            });
            celdaSemanal.setCheckBoxEstado(completador.estadoCompletar(asignable));
        }else{
            celdaSemanal.setEsTarea(false);
        }
    }

    @Override
    public Node getVista() {
        return vista;
    }
}
