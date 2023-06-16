package Algo3.Componentes;

import Algo3.Constantes.Dia;
import Algo3.Constantes.Mes;
import Algo3.Constantes.VistaTipo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class Menu extends VBox {
    @FXML
    private SelectorCalendario selectorCalendario;
    @FXML
    private Label diaElegido;
    @FXML
    private Label mesElegido;
    @FXML
    private Label anioElegido;
    @FXML
    private VBox selectorRango;
    @FXML
    private ObjectProperty<VistaTipo> vistaSeleccionada = new SimpleObjectProperty<>(VistaTipo.DIARIA);
    @FXML
    private Button botonDiario;
    @FXML
    private Button botonSemanal;
    @FXML
    private Button botonMensual;
    @FXML
    private Button botonAgregar;
    public Menu(){
        cargarFXML();
        agregarListeners();
        asignarFecha(selectorCalendario.getFecha());
        selectorCalendario.setOnAction(actionEvent -> asignarFecha(selectorCalendario.getFecha()));
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/menu.css").toUri().toString());

    }
    public ObjectProperty<VistaTipo> getVistaActualProperty(){
        return vistaSeleccionada;
    }

    public ObjectProperty<LocalDate> getFechaActualProperty(){
        return selectorCalendario.getFechaActualProperty();
    }

    private void agregarListeners(){
        botonDiario.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vistaSeleccionada.set(VistaTipo.DIARIA);
            }
        });
        botonSemanal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vistaSeleccionada.set(VistaTipo.SEMANAL);
            }
        });
        botonMensual.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vistaSeleccionada.set(VistaTipo.MENSUAL);
            }
        });
    }

    public void addAgregarListener(EventHandler<ActionEvent> handler){
        botonAgregar.setOnAction(handler);
    }

    private void asignarFecha(LocalDate fechaActual){
        String diaMayusculas =Dia.values()[fechaActual.getDayOfWeek().getValue()-1].name();
        String dia = diaMayusculas.charAt(0) + diaMayusculas.substring(1).toLowerCase();

        diaElegido.setText(dia + " " + fechaActual.getDayOfMonth());

        String mesMayusculas = Mes.values()[fechaActual.getMonth().getValue()-1].name();
        String mes = mesMayusculas.charAt(0) + mesMayusculas.substring(1).toLowerCase();

        mesElegido.setText(mes);

        anioElegido.setText(String.valueOf(fechaActual.getYear()));
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/Componente/menuLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
