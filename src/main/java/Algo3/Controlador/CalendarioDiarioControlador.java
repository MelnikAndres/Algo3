package Algo3.Controlador;

import Algo3.Componentes.Apilable;
import Algo3.Componentes.ApiladorDeAsignables;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Vista.Calendario.CalendarioDiarioVista;
import Algo3.Vista.DialogoEditarControlador;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CalendarioDiarioControlador extends CalendarioControlador {
    private Integer filaInicioDeArrastre;
    private Apilable actualAgregando;
    private ObjectProperty<LocalDate> fechaActual;
    private CalendarioDiarioVista  vista;
    private ApiladorDeAsignables apiladorDeAsignables;
    private Calendario calendario;

    CalendarioDiarioControlador(Calendario calendario, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                                ObjectProperty<LocalDate> dateValue){
        fechaActual = dateValue;

        vista = new CalendarioDiarioVista();
        vista.montarVista(widthProperty,heightProperty);
        this.calendario = calendario;
        apiladorDeAsignables = vista.getApiladorDeAsignables();
        agregarEventosDelApilador();
    }

    private void agregarEventosDelApilador() {
        apiladorDeAsignables.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)){
                return;
            }
            this.filaInicioDeArrastre = obtenerPosicionEnCalendario(event.getY());
        });
        apiladorDeAsignables.setOnMouseDragged(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)|| this.filaInicioDeArrastre == null){
                return;
            }
            if(this.actualAgregando == null ){
                agregarNuevo();
            }
            expandirNuevo(event.getY());
        });
        apiladorDeAsignables.setOnMouseReleased(event -> {
            if (!event.getButton().equals(MouseButton.PRIMARY)){
                return;
            }
            if(actualAgregando == null){
                this.filaInicioDeArrastre = null;
                return;
            }
            apiladorDeAsignables.agregarComportamiento(actualAgregando);
            apiladorDeAsignables.apilar(actualAgregando);
            this.filaInicioDeArrastre = null;
            this.actualAgregando = null;
        });
    }
    private void agregarAcciones(Apilable apilable, Integer id){
        apilable.addBorrarEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                calendario.eliminar(id);
            }
        });
        apilable.addEditarEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Asignable asignable = calendario.obtenerAsignablePorId(id);
                DialogoEditarControlador controlador = new DialogoEditarControlador((Stage)vista.getScene().getWindow());
                controlador.cargarValores(asignable.obtenerParametros());
                Asignable resultado = controlador.abrirYeditar();
                if(resultado == null){
                    return;
                }
                calendario.editar(id, resultado);
                apilable.editar(resultado.getTitulo(), resultado.getFechaInicio(),resultado.getFechaFinal());
                var listener = new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        apiladorDeAsignables.reordenar();
                        apilable.widthProperty().removeListener(this);
                    }
                };
                apilable.widthProperty().addListener(listener);
            }
        });
    }
    private void agregarNuevo(){
        actualAgregando = new Apilable(filaInicioDeArrastre);
        apiladorDeAsignables.getChildren().add(actualAgregando);
    }
    private void expandirNuevo(double yFinal){
        double altura = yFinal - this.filaInicioDeArrastre*15;
        if(altura>0){
            actualAgregando.setNuevaAltura(Math.max(altura,15));
        }
    }
    private Integer obtenerPosicionEnCalendario(double y){
        return (int) ((24*y*4)/apiladorDeAsignables.getHeight());
    }

    public void cargarAsignables(Map<Integer, List<LocalDateTime>> repeticiones){
        apiladorDeAsignables.desapilarTodo();
        for(Integer asignableID: repeticiones.keySet()){
            for(LocalDateTime fecha: repeticiones.get(asignableID)){
                if(fecha.toLocalDate().equals(fechaActual.get())){
                    Asignable asignable = calendario.obtenerAsignablePorId(asignableID);
                    var nuevoApilable = new Apilable(asignable.getTitulo(), asignable.getFechaInicio(),asignable.getFechaFinal());
                    apiladorDeAsignables.getChildren().add(nuevoApilable);
                    apiladorDeAsignables.agregarComportamiento(nuevoApilable);
                    agregarAcciones(nuevoApilable, asignableID);
                    var listener = new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                            if(t1.doubleValue()>0){
                                apiladorDeAsignables.apilar(nuevoApilable);
                                nuevoApilable.widthProperty().removeListener(this);
                            }
                        }
                    };
                    nuevoApilable.widthProperty().addListener(listener);
                }
            }
        }
    }

    @Override
    public Node getVista() {
        return vista;
    }
}
