package Algo3.Controlador;

import Algo3.Componentes.Notificacion;
import Algo3.Constantes.VistaTipo;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Modelo.Tarea;
import Algo3.Vista.Calendario.CalendarioDiarioVista;
import Algo3.Vista.Calendario.CalendarioMensualVista;
import Algo3.Vista.DialogoEditarControlador;
import Algo3.Vista.MainVista;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class MainControlador {

    private MainVista mainVista;
    private Calendario calendario = new Calendario();
    private CalendarioControlador calendarioControlador;
    private final Timer timer = new Timer();

    //{anio --> mes -->apariciones}
    public MainControlador(ReadOnlyDoubleProperty widthProperty){
        mainVista = new MainVista(widthProperty);
        calendarioControlador = new CalendarioDiarioControlador(calendario,mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
        mainVista.getContenido().getChildren().add(calendarioControlador.getVista());
        agregarListeners();
        cargarApariciones(mainVista.getFechaActualProperty().get());
        ejecutarAlarma();
    }
    private void agregarListeners(){
        mainVista.getVistaActualProperty().addListener((__,___,nuevoTipo) -> cambiarVistaActual(nuevoTipo));

        mainVista.getFechaActualProperty().addListener((__,___,fechaElegida) -> {
            cargarApariciones(fechaElegida);
        });
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoEditarControlador controlador = new DialogoEditarControlador((Stage) getVista().getScene().getWindow());
                Asignable resultado = controlador.abrirYeditar();
                if(resultado!=null){
                    calendario.agregar(resultado);
                }
            }
        };
        mainVista.addAgregarListener(handler);
    }
    private void cargarApariciones(LocalDate fechaElegida){
        var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaElegida.getMonthValue(), fechaElegida.getYear());
        calendarioControlador.cargarAsignables(aparicionesActuales);
    }

    private void cambiarVistaActual(VistaTipo nuevoTipo){
        mainVista.getContenido().getChildren().remove(calendarioControlador.getVista());
        switch (nuevoTipo){
            case DIARIA -> calendarioControlador = new CalendarioDiarioControlador(calendario, mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
            case MENSUAL -> calendarioControlador = new CalendarioMensualControlador(calendario, mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
            case SEMANAL -> calendarioControlador = new CalendarioSemanalControlador(calendario, mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
        }
        mainVista.getContenido().getChildren().add(calendarioControlador.getVista());
        cargarApariciones(mainVista.getFechaActualProperty().get());
    }

    public void ejecutarAlarma(){
        if(calendario.proximaAlarma() != null) {
            LocalDateTime fechaEjecucion = calendario.proximaAlarma().getFecha().minus(calendario.proximaAlarma().getTiempoAntes());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (calendario.proximaAlarma() != null) {
                        Asignable asignable = calendario.asignableConProximaAlarma();
                        asignable.getAlarmas().remove(calendario.proximaAlarma());
                        Platform.runLater(()->{
                            mainVista.getNotificaciones().getChildren().add(new Notificacion(asignable.getTitulo(), mainVista.getNotificaciones()));
                            ejecutarAlarma();

                        });

                    }

                }
            }, Date.from(Instant.from(fechaEjecucion.atZone(ZoneId.systemDefault()).toInstant())));

        }
    }


    public MainVista getVista(){
        return mainVista;
    }

    private void agregarAsignable(Asignable asignable){
        calendario.agregar(asignable);
    }
}

