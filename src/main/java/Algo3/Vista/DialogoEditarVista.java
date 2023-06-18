package Algo3.Vista;

import Algo3.Componentes.OpcionesExtra.ExtraEvento;
import Algo3.Componentes.OpcionesExtra.ExtraFechaHora;
import Algo3.Componentes.OpcionesExtra.OpcionExtra;
import Algo3.Componentes.OpcionesExtra.OpcionesExtra;
import Algo3.Constantes.ParametroTipo;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogoEditarVista extends DialogPane {
    @FXML
    private ChoiceBox<String> selectorAsignableTipo;
    @FXML
    private VBox contenedor;
    @FXML
    private TextField tituloField;
    @FXML
    private TextArea descripcionArea;
    @FXML
    private ToggleButton diaCompletoToggle;
    @FXML
    private HBox fechasContainer;
    @FXML
    private ExtraFechaHora fechaInicial;
    @FXML
    private ExtraFechaHora fechaFinal;
    private ObservableList<String> tipos = FXCollections.observableArrayList("Evento","Tarea");

    private Node extra;

    public DialogoEditarVista(){
        cargarFXML();
        selectorAsignableTipo.setItems(tipos);
        contenedor.setSpacing(10);
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/dialogoEditarVista.css").toUri().toString());
        getStyleClass().add("contenedor");
        bindDiaCompleto();
        fechaInicial.setValor(LocalDateTime.now().toString());
        fechaFinal.setValor(LocalDateTime.now().toString());
        diaCompletoToggle.setSelected(true);
        contenedor.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                getScene().getWindow().sizeToScene();
            }
        });
    }

    public void addTipoListener(ChangeListener<String> listener){
        selectorAsignableTipo.valueProperty().addListener(listener);
        selectorAsignableTipo.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {

            }
        });
    }
    public void agregarExtra(Node extra){
        this.extra = extra;
        contenedor.getChildren().add(extra);
    }
    public void removerExtra(){
        if(extra != null){
            contenedor.getChildren().remove(extra);
        }
    }

    private void bindDiaCompleto(){
        diaCompletoToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                fechaFinal.setDisableHoras(t1);
                fechaInicial.setDisableHoras(t1);
            }
        });
    }

    public void setFechaInicial(LocalDateTime fechaInicial){
        this.fechaInicial.setFecha(fechaInicial);
    }
    public void setFechaFinal(LocalDateTime fechaFinal){
        this.fechaFinal.setFecha(fechaFinal);
    }
    public void setTitulo(String titulo){
        tituloField.setText(titulo);
    }
    public void setDescripcion(String descripcion){
        descripcionArea.setText(descripcion);
    }
    public void setTipo(String tipo){
        selectorAsignableTipo.setValue(tipo);
    }

    private void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Dialogo/dialogoEditarLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDateTime getFechaInicial() {
        if(diaCompletoToggle.isSelected()){
            return LocalDateTime.of(fechaInicial.getFecha().toLocalDate(), LocalTime.of(0,0,0));
        }
        return fechaInicial.getFecha();
    }
    public LocalDateTime getFechaFinal() {
        if(diaCompletoToggle.isSelected()){
            return LocalDateTime.of(fechaFinal.getFecha().toLocalDate(), LocalTime.of(0,0,0));
        }
        return fechaFinal.getFecha();
    }
    public boolean esDiaCompleto(){
        return diaCompletoToggle.isSelected();
    }
    public void setDiaCompleto(boolean nuevoValor){
        diaCompletoToggle.setSelected(nuevoValor);
    }

    public String getTitulo() {
        return tituloField.getText();
    }
    public String getDescripcion(){
        return descripcionArea.getText();
    }
}
