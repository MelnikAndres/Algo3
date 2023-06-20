package Algo3.Controlador.Calendario;

import Algo3.Componentes.Apilable;
import Algo3.Componentes.ApiladorDeAsignables;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Calendario;
import Algo3.Modelo.Tarea;
import Algo3.Utilidad.CalendarioEvento;
import Algo3.Vista.Calendario.CalendarioDiarioVista;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalendarioDiarioControlador extends CalendarioControlador {
    private Integer filaInicioDeArrastre;
    private Apilable actualAgregando;
    private CalendarioDiarioVista  vista;
    private ApiladorDeAsignables apiladorDeAsignables;

    public CalendarioDiarioControlador(Calendario calendario, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
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
            calendario.agregar(crearAsignable());
            getVista().fireEvent(new CalendarioEvento(CalendarioEvento.SERIALIZAR));
            var aparicionesActuales = calendario.obtenerAparicionesEnMesyAnio(fechaActual.get().getMonthValue(), fechaActual.get().getYear());
            cargarAsignables(aparicionesActuales);
            this.filaInicioDeArrastre = null;
            this.actualAgregando = null;
        });
    }
    private Asignable crearAsignable(){
        LocalTime tiempoInicial = LocalTime.of(filaInicioDeArrastre/4,filaInicioDeArrastre%4 * 15,0);
        LocalDateTime fechaInicial = LocalDateTime.of(fechaActual.get(),tiempoInicial);
        LocalTime tiempoFinal = actualAgregando.getHoraFinal();
        LocalDateTime fechaFinal = LocalDateTime.of(fechaActual.get(),tiempoFinal);
        return new Tarea("(Sin Titulo)","(Sin Descripcion)",fechaInicial,fechaFinal);
    }
    private void agregarNuevo(){
        actualAgregando = new Apilable(filaInicioDeArrastre);
        apiladorDeAsignables.getChildren().add(actualAgregando);
    }
    private void expandirNuevo(double yFinal){
        double altura = yFinal - this.filaInicioDeArrastre*15;
        if(altura>0){
            actualAgregando.setNuevaAltura(altura);
        }
    }
    private Integer obtenerPosicionEnCalendario(double y){
        return (int) ((24*y*4)/apiladorDeAsignables.getHeight());
    }
    void limpiarVista() {
        apiladorDeAsignables.desapilarTodo();
        vista.limpiarDiaCompleto();
    }
    void cargarAsignable(Integer asignableId, LocalDateTime fecha){
        Asignable asignable = calendario.obtenerAsignablePorId(asignableId);

        long duracionMinutos = asignable.getFechaInicio().until(asignable.getFechaFinal(), ChronoUnit.MINUTES);
        LocalDateTime fechaFinal = fecha.plusMinutes(duracionMinutos);
        if(fecha.toLocalDate().isAfter(fechaActual.get()) || fechaFinal.toLocalDate().isBefore(fechaActual.get())){
            return;
        }

        var nuevoApilable = new Apilable(asignable.getTitulo(), fecha,fechaFinal,fechaActual.get());
        if(asignable.getFechaInicio().equals(asignable.getFechaFinal())){
            vista.agregarDiaCompleto(nuevoApilable);
        }else{

            apiladorDeAsignables.getChildren().add(nuevoApilable);
            apiladorDeAsignables.agregarComportamiento(nuevoApilable);
            //el listener espera a que se calcule el ancho del apilable nuevo antes de apilar
            //apilar usa el ancho para acomodar a los apilados, el ancho se calcula en el siguiente frame
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
        agregarAcciones(nuevoApilable, asignableId);
    }
    @Override
    public Node getVista() {
        return vista;
    }
}
