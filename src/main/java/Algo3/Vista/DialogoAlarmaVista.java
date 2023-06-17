package Algo3.Vista;

import Algo3.Componentes.OpcionesExtra.ExtraFechaHora;
import Algo3.Disparador.Disparador;
import Algo3.Modelo.Alarma;
import Algo3.Utilidad.FechaParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DialogoAlarmaVista extends DialogPane {

    @FXML
    private ScrollPane diasScroll;
    @FXML
    private VBox diasContainer;
    @FXML
    private ScrollPane horasScroll;
    @FXML
    private VBox horasContainer;
    @FXML
    private ScrollPane minutoScroll;
    @FXML
    private VBox minutosContainer;
    @FXML
    private HBox contenedorDuration;
    @FXML
    private ToggleButton toggleAbsoluta;
    @FXML
    private ExtraFechaHora fechaYhora;
    @FXML
    private ListView<Alarma> alarmasList;
    private ToggleGroup grupoDias = new ToggleGroup();
    private ToggleGroup grupoHoras = new ToggleGroup();
    private ToggleGroup grupoMinutos = new ToggleGroup();

    public DialogoAlarmaVista(List<Alarma> alarmas){
        cargarFXML();
        cargarOpciones();
        toggleOpciones();
        alarmasList.setItems(FXCollections.observableArrayList(alarmas));
        alarmasList.setCellFactory(new Callback<ListView<Alarma>, ListCell<Alarma>>() {
            @Override
            public ListCell<Alarma> call(ListView<Alarma> alarmaListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Alarma alarma, boolean empty) {
                        super.updateItem(alarma, empty);
                        if (alarma == null || empty) {
                            setText(null);
                            setBackground(Background.EMPTY);
                        } else {
                            String texto = alarma.getTipo() + "\n" +
                                    alarma.getFecha().minus(alarma.getTiempoAntes()).format(DateTimeFormatter
                                            .ofPattern("dd/MM/yyyy HH:mm:ss")) ;
                            setText(texto);
                        }
                    }
                };
            }
        });
        fechaYhora.setDisable(true);
        fechaYhora.setValor(LocalDateTime.now().toString());
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/dialogoAlarmaVista.css").toUri().toString());
    }

    private void toggleOpciones() {
        toggleAbsoluta.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    fechaYhora.setDisable(false);
                    contenedorDuration.setDisable(true);
                }else{
                    fechaYhora.setDisable(true);
                    contenedorDuration.setDisable(false);
                }
            }
        });
    }

    private void cargarOpciones() {
        for(int i=0; i<24;i++){
            var boton = new ToggleButton(String.valueOf(i));
            boton.setMaxWidth(1000);
            grupoHoras.getToggles().add(boton);
            boton.getStyleClass().add("boton-hora");
            horasContainer.getChildren().add(boton);
        }
        for(int i=0; i<60;i++){
            var boton = new ToggleButton(String.valueOf(i));
            boton.setMaxWidth(1000);
            grupoMinutos.getToggles().add(boton);
            boton.getStyleClass().add("boton-hora");
            minutosContainer.getChildren().add(boton);
        }
        for(int i=0; i<14;i++){
            var boton = new ToggleButton(String.valueOf(i));
            boton.setMaxWidth(1000);
            grupoDias.getToggles().add(boton);
            boton.getStyleClass().add("boton-hora");
            diasContainer.getChildren().add(boton);
        }
    }

    public Duration getDuration(){
        ToggleButton selectedDia = (ToggleButton) grupoDias.getSelectedToggle();
        ToggleButton selectedHoras = (ToggleButton) grupoHoras.getSelectedToggle();
        ToggleButton selectedMinutos = (ToggleButton) grupoMinutos.getSelectedToggle();
        Duration durationDias = selectedDia !=null?Duration.ofDays(Integer.parseInt(selectedDia.getText())):Duration.ZERO;
        Duration durationHoras = selectedHoras !=null?Duration.ofHours(Integer.parseInt(selectedHoras.getText())):Duration.ZERO;
        Duration durationMinutos = selectedMinutos !=null?Duration.ofMinutes(Integer.parseInt(selectedMinutos.getText())):Duration.ZERO;
        return durationDias.plus(durationHoras).plus(durationMinutos);
    }

    public boolean esAbsoluta() {
        return toggleAbsoluta.isSelected();
    }
    public LocalDateTime getFecha(){
        return FechaParser.fromString(fechaYhora.getValor());
    }
    private void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Dialogo/dialogoAlarmaLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
