package Algo3.Controlador;

import Algo3.Componentes.Notificacion;
import Algo3.Constantes.VistaTipo;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Utilidad.AlarmaEvento;
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

    //{anio --> mes -->apariciones}
    public MainControlador(ReadOnlyDoubleProperty widthProperty){
        try {
            calendario = Calendario.deserializar(new FileInputStream(System.getProperty("user.dir")+"\\calendario.json"));
        } catch (IOException e) {
            calendario = new Calendario();
        }
        mainVista = new MainVista(widthProperty);
        calendarioControlador = new CalendarioDiarioControlador(calendario,mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
        mainVista.getContenido().getChildren().add(calendarioControlador.getVista());
        agregarListeners();
        cargarApariciones(mainVista.getFechaActualProperty().get());
        calendarioControlador.getVista().addEventHandler(AlarmaEvento.NUEVA_ALARMA, alarmaEvento -> ejecutarAlarma());
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
                    try {
                        calendario.serializar(new FileOutputStream(System.getProperty("user.dir")+"\\calendario.json"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
        calendarioControlador.getVista().addEventHandler(AlarmaEvento.NUEVA_ALARMA, alarmaEvento -> ejecutarAlarma());
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

