package Algo3.Controlador;

import Algo3.Constantes.VistaTipo;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Vista.Calendario.CalendarioDiarioVista;
import Algo3.Vista.Calendario.CalendarioMensualVista;
import Algo3.Vista.MainVista;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MainControlador {

    private MainVista mainVista= new MainVista();
    private Calendario calendario = new Calendario();
    private CalendarioControlador calendarioControlador;

    //{anio --> mes -->apariciones}
    private ObservableMap<Integer,HashMap<Integer, Map<Asignable, List<LocalDateTime>>>> aparicionesPrevias = FXCollections.observableHashMap();
    public MainControlador(ReadOnlyDoubleProperty widthProperty){
        mainVista.prefWidthProperty().bind(widthProperty);
        calendarioControlador = new CalendarioDiarioControlador(mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
        mainVista.getChildren().add(calendarioControlador.getVista());
        agregarListeners();
        agregarApariciones(mainVista.getFechaActualProperty().get());
        cargarApariciones(mainVista.getFechaActualProperty().get());
    }
    private void agregarListeners(){
        mainVista.getVistaActualProperty().addListener((__,___,nuevoTipo) -> cambiarVistaActual(nuevoTipo));

        mainVista.getFechaActualProperty().addListener((__,___,fechaElegida) -> {
            if(!(aparicionesPrevias.containsKey(fechaElegida.getYear()) &&
                    aparicionesPrevias.get(fechaElegida.getYear()).containsKey(fechaElegida.getMonthValue()))){
                agregarApariciones(fechaElegida);
            }
            cargarApariciones(fechaElegida);
        });
    }
    private void cargarApariciones(LocalDate fechaElegida){
        var aparicionesEnMes = aparicionesPrevias.get(fechaElegida.getYear()).get(fechaElegida.getMonthValue());
        calendarioControlador.cargarAsignables(aparicionesEnMes);
    }
    private void agregarApariciones(LocalDate fechaElegida){
        var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaElegida.getMonthValue(), fechaElegida.getYear());
        HashMap<Integer, Map<Asignable, List<LocalDateTime>>> aparicionesEnMes = new HashMap<>();
        aparicionesEnMes.put(fechaElegida.getMonthValue(),aparicionesActuales);
        aparicionesPrevias.put(fechaElegida.getYear(),aparicionesEnMes);
    }

    private void cambiarVistaActual(VistaTipo nuevoTipo){
        mainVista.getChildren().remove(calendarioControlador.getVista());
        switch (nuevoTipo){
            case DIARIA -> calendarioControlador = new CalendarioDiarioControlador(mainVista.prefWidthProperty(),mainVista.heightProperty(),mainVista.getFechaActualProperty());
            case MENSUAL -> System.out.println("SEMANAL");
            case SEMANAL -> System.out.println("SEMANAL");
        }
        mainVista.getChildren().add(calendarioControlador.getVista());
    }

    public MainVista getVista(){
        return mainVista;
    }

    private void agregarAsignable(Asignable asignable){
        calendario.agregar(asignable);
    }
}

