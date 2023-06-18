package Algo3.Controlador;

import Algo3.Componentes.Menu;
import Algo3.Componentes.Notificacion;
import Algo3.Constantes.ErrorTipo;
import Algo3.Controlador.Calendario.CalendarioControlador;
import Algo3.Controlador.Calendario.CalendarioDiarioControlador;
import Algo3.Controlador.Calendario.CalendarioMensualControlador;
import Algo3.Controlador.Calendario.CalendarioSemanalControlador;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Utilidad.CalendarioEvento;
import Algo3.Utilidad.ErrorEvento;
import Algo3.Vista.MainVista;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class MainControlador {

    private MainVista mainVista;
    private Calendario calendario;
    private CalendarioControlador calendarioControlador;
    private final Timer timer = new Timer(true);
    private TimerTask timerActivo;

    //{anio --> mes -->apariciones}
    public MainControlador(ReadOnlyDoubleProperty widthProperty){
        mainVista = new MainVista(widthProperty);
        try {
            calendario = Calendario.deserializar(new FileInputStream(System.getProperty("user.dir")+"\\calendario.json"));
        } catch (IOException e) {
            Notificacion notificacion = new Notificacion(ErrorTipo.ERROR_DE_CARGA.toString(), true);
            notificacion.addCerrarHandler(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    getVista().getNotificaciones().getChildren().remove(notificacion);
                }
            });
            mainVista.getNotificaciones().getChildren().add(notificacion);
            calendario = new Calendario();
        }
        calendarioControlador = new CalendarioDiarioControlador(calendario,mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
        cargarNuevaVista();
        agregarListenersBasicos();
        ejecutarAlarma();
    }
    private void agregarListenersBasicos(){
        mainVista.getFechaActualProperty().addListener((__,___,fechaElegida) -> {
            cargarApariciones(fechaElegida);
        });
        mainVista.addAgregarListener(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoEditarControlador controlador = new DialogoEditarControlador((Stage) getVista().getScene().getWindow());
                Asignable resultado = null;
                try{
                    resultado = controlador.abrirYeditar();
                }catch (RuntimeException e){
                    calendarioControlador.getVista().fireEvent(new ErrorEvento(ErrorEvento.EDITAR, e.getMessage()));
                }
                if(resultado!=null){
                    calendario.agregar(resultado);
                    var fechaActual = mainVista.getFechaActualProperty().get();
                    var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaActual.getMonthValue(), fechaActual.getYear());
                    calendarioControlador.cargarAsignables(aparicionesActuales);
                    try {
                        calendario.serializar(new FileOutputStream(System.getProperty("user.dir")+"\\calendario.json"));
                    } catch (IOException e) {
                        Notificacion notificacion = new Notificacion(ErrorTipo.ERROR_DE_GUARDADO.toString(), true);
                        notificacion.addCerrarHandler(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                getVista().getNotificaciones().getChildren().remove(notificacion);
                            }
                        });
                        mainVista.getNotificaciones().getChildren().add(notificacion);
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Menu menu = mainVista.getMenu();
        menu.addDiarioListener(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainVista.getContenido().getChildren().remove(calendarioControlador.getVista());
                calendarioControlador = new CalendarioDiarioControlador(calendario, mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
                cargarNuevaVista();
            }
        });
        menu.addSemanalListener(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainVista.getContenido().getChildren().remove(calendarioControlador.getVista());
                calendarioControlador = new CalendarioSemanalControlador(calendario, mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
                cargarNuevaVista();
            }
        });
        menu.addMensualListener(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainVista.getContenido().getChildren().remove(calendarioControlador.getVista());
                calendarioControlador = new CalendarioMensualControlador(calendario, mainVista.prefWidthProperty(), mainVista.heightProperty(),mainVista.getFechaActualProperty());
                cargarNuevaVista();
            }
        });
    }
    private void agregarListenersVistaActual(){
        calendarioControlador.getVista().addEventHandler(CalendarioEvento.NUEVA_ALARMA, calendarioEvento -> ejecutarAlarma());
        calendarioControlador.getVista().addEventHandler(CalendarioEvento.SERIALIZAR, new EventHandler<CalendarioEvento>() {
            @Override
            public void handle(CalendarioEvento calendarioEvento) {
                try {
                    calendario.serializar(new FileOutputStream(System.getProperty("user.dir")+"\\calendario.json"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        calendarioControlador.getVista().addEventHandler(ErrorEvento.EDITAR, new EventHandler<ErrorEvento>() {
            @Override
            public void handle(ErrorEvento errorEvento) {
                Notificacion notificacion = new Notificacion(errorEvento.getTexto(), true);
                notificacion.addCerrarHandler(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        getVista().getNotificaciones().getChildren().remove(notificacion);
                    }
                });
                mainVista.getNotificaciones().getChildren().add(notificacion);
            }
        });
    }

    private void cargarNuevaVista(){
        mainVista.getContenido().getChildren().add(calendarioControlador.getVista());
        cargarApariciones(mainVista.getFechaActualProperty().get());
        agregarListenersVistaActual();
    }
    private void cargarApariciones(LocalDate fechaElegida){
        var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaElegida.getMonthValue(), fechaElegida.getYear());
        calendarioControlador.cargarAsignables(aparicionesActuales);
    }

    public void ejecutarAlarma(){
        if(calendario.proximaAlarma() != null) {
            if(timerActivo != null){
                timerActivo.cancel();
            }
            LocalDateTime fechaEjecucion = calendario.proximaAlarma().getFecha().minus(calendario.proximaAlarma().getTiempoAntes());
            timerActivo = new TimerTask() {
                @Override
                public void run() {
                    if (calendario.proximaAlarma() != null) {
                        Asignable asignable = calendario.asignableConProximaAlarma();
                        asignable.getAlarmas().remove(calendario.proximaAlarma());
                        Platform.runLater(()->{
                            Notificacion notificacion = new Notificacion(asignable.getTitulo(),false);
                            notificacion.addCerrarHandler(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    getVista().getNotificaciones().getChildren().remove(notificacion);
                                }
                            });
                            mainVista.getNotificaciones().getChildren().add(notificacion);
                            ejecutarAlarma();
                        });

                    }
                }
            };
            timer.schedule(timerActivo, Date.from(Instant.from(fechaEjecucion.atZone(ZoneId.systemDefault()).toInstant())));

        }
    }

    public MainVista getVista(){
        return mainVista;
    }
}

