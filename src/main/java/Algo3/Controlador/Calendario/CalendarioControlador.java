package Algo3.Controlador.Calendario;

import Algo3.Componentes.Celda;
import Algo3.Controlador.DialogoAlarmaControlador;
import Algo3.Controlador.DialogoEditarControlador;
import Algo3.Modelo.Alarma;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Utilidad.CalendarioEvento;
import Algo3.Utilidad.Completador;
import Algo3.Utilidad.ErrorEvento;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public abstract class CalendarioControlador {

    ObjectProperty<LocalDate> fechaActual;
    Calendario calendario;

    public void cargarAsignables(Map<Integer, List<LocalDateTime>> repeticiones){
        limpiarVista();
        for(Integer asignableID: repeticiones.keySet()){
            for(LocalDateTime fecha: repeticiones.get(asignableID)){
                if(esAgregable(fecha)){
                    cargarAsignable(asignableID, fecha);
                }
            }
        }
    }

    abstract void cargarAsignable(Integer asignableId, LocalDateTime fecha);
    public abstract Node getVista();
    void probarEsCompletable(Asignable asignable, Celda celda){
        Completador completador = new Completador(false);
        if(completador.esRecibido(asignable)){
            celda.setEsTarea(true);
            celda.addCheckBoxListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    asignable.recibirCompletador(new Completador(t1));
                    getVista().fireEvent(new CalendarioEvento(CalendarioEvento.SERIALIZAR));
                }
            });
            celda.setCheckBoxEstado(completador.estadoCompletar(asignable));
        }else{
            celda.setEsTarea(false);
        }
    }
    void agregarAcciones(Celda celda, Integer id){
        celda.addBorrarEvent(borrarHandler(calendario,id,fechaActual));
        celda.addEditarEvent(editarHandler(calendario,id,fechaActual));
        celda.addAlarmaEvent(alarmaHandler(calendario,id));
        probarEsCompletable(calendario.obtenerAsignablePorId(id), celda);
    }
    EventHandler<ActionEvent> borrarHandler(Calendario calendario, Integer id, ObjectProperty<LocalDate> fechaActual){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                calendario.eliminar(id);
                var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaActual.get().getMonthValue(), fechaActual.get().getYear());
                cargarAsignables(aparicionesActuales);
                getVista().fireEvent(new CalendarioEvento(CalendarioEvento.SERIALIZAR));
            }
        };
    }
    EventHandler<ActionEvent> editarHandler(Calendario calendario, Integer id,
                                            ObjectProperty<LocalDate> fechaActual){
        Asignable asignable = calendario.asignableConClave(id);
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoEditarControlador controlador = new DialogoEditarControlador((Stage)getVista().getScene().getWindow());
                controlador.cargarValores(asignable);
                Asignable resultado = null;
                try{
                    resultado = controlador.abrirYeditar();
                }catch (RuntimeException e){
                    getVista().fireEvent(new ErrorEvento(ErrorEvento.EDITAR, e.getMessage()));
                }
                if(resultado == null){
                    return;
                }
                calendario.editar(id, resultado);
                var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaActual.get().getMonthValue(), fechaActual.get().getYear());
                cargarAsignables(aparicionesActuales);
                getVista().fireEvent(new CalendarioEvento(CalendarioEvento.SERIALIZAR));
            }
        };
    }
    EventHandler<ActionEvent> alarmaHandler(Calendario calendario, Integer id){
        Asignable asignable = calendario.obtenerAsignablePorId(id);
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoAlarmaControlador dialogoAlarmaControlador = new DialogoAlarmaControlador((Stage)getVista().getScene().getWindow(),asignable.getAlarmas());
                Alarma resultado = dialogoAlarmaControlador.abrirYcrear(asignable.getFechaInicio());
                if(resultado != null){
                    asignable.agregarAlarma(resultado);
                    getVista().fireEvent(new CalendarioEvento(CalendarioEvento.NUEVA_ALARMA));
                    getVista().fireEvent(new CalendarioEvento(CalendarioEvento.SERIALIZAR));
                }
            }
        };
    }

    abstract boolean esAgregable(LocalDateTime fecha);
    abstract void limpiarVista();

}
