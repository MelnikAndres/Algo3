package Algo3.Controlador;

import Algo3.Constantes.VistaTipo;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Vista.MainVista;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainControlador {

    private MainVista mainVista;
    private Calendario calendario = new Calendario();
    private CalendarioControlador calendarioControlador;

    //{anio --> mes -->apariciones}
    public MainControlador(ReadOnlyDoubleProperty widthProperty){
        mainVista = new MainVista(widthProperty);
        calendarioControlador = new CalendarioDiarioControlador(calendario,mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
        mainVista.getContenido().getChildren().add(calendarioControlador.getVista());
        agregarListeners();
        cargarApariciones(mainVista.getFechaActualProperty().get());
    }
    private void agregarListeners(){
        mainVista.getVistaActualProperty().addListener((__,___,nuevoTipo) -> cambiarVistaActual(nuevoTipo));

        mainVista.getFechaActualProperty().addListener((__,___,fechaElegida) -> {
            cargarApariciones(fechaElegida);
        });
        mainVista.addAgregarListener(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoEditarControlador controlador = new DialogoEditarControlador((Stage) getVista().getScene().getWindow());
                Asignable resultado = controlador.abrirYeditar();
                if(resultado!=null){
                    calendario.agregar(resultado);
                    cargarApariciones(mainVista.getFechaActualProperty().get());
                }
            }
        });
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

    public MainVista getVista(){
        return mainVista;
    }

    private void agregarAsignable(Asignable asignable){
        calendario.agregar(asignable);
    }
}

