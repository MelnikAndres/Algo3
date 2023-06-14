package Algo3.Vista.Calendario;

import Algo3.Componentes.Apilable;
import Algo3.Componentes.ApiladorDeAsignables;
import Algo3.Modelo.Asignable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioDiarioVista extends ScrollPane {
    @FXML
    GridPane grillaDiario;
    @FXML
    ApiladorDeAsignables apiladorDeAsignables;
    @FXML
    StackPane container;
    @FXML
    private ColumnConstraints columnaHoras;
    @FXML
    private ColumnConstraints columnaAsignables;

    public CalendarioDiarioVista(){
        cargarFXML();
        crearGrilla();
    }

    public ApiladorDeAsignables getApiladorDeAsignables(){
        return  apiladorDeAsignables;
    }

    public void montarVista(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        prefWidthProperty().bind(widthProperty.subtract(250));
        maxHeightProperty().bind(heightProperty);
        apiladorDeAsignables.prefWidthProperty().bind(widthProperty.subtract(341));
        apiladorDeAsignables.maxWidthProperty().bind(widthProperty.subtract(341));
        grillaDiario.prefWidthProperty().bind(apiladorDeAsignables.prefWidthProperty().add(60));
        grillaDiario.minWidthProperty().bind(apiladorDeAsignables.widthProperty().add(60));
    }

    private void crearGrilla() {
        grillaDiario.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/Calendario/separator.css").toUri().toString());
        for (int i = 0; i < 24; i++) {
            crearConstraints();
            var filaHora = crearFilaHora(i);
            var separadores = crearSeparadores();
            grillaDiario.addRow(i, filaHora,separadores);
        }
    }

    private StackPane crearFilaHora(int hora) {
        var filaHora = new StackPane(crearHoraLabel(hora));
        filaHora.setAlignment(Pos.CENTER_RIGHT);
        return filaHora;
    }
    private Label crearHoraLabel(int hora) {
        var horaLabel = new Label();
        horaLabel.setPrefHeight(60);
        horaLabel.setPrefWidth(60);
        horaLabel.setTextAlignment(TextAlignment.CENTER);
        horaLabel.setAlignment(Pos.CENTER);
        horaLabel.setStyle("-fx-background-color: #142131;-fx-text-fill: #e9edf6");
        if(hora < 12){
            horaLabel.setText((hora==0?12:hora) + " AM");
        }else{
            horaLabel.setText((hora==12?12:hora-12) + " PM");
        }
        return  horaLabel;
    }

    private VBox crearSeparadores() {
        var separadorHoras = new Separator(Orientation.HORIZONTAL);
        separadorHoras.getStyleClass().add("oscuro");
        var separadores = new VBox(separadorHoras);

        for(int j=0;j<4;j++){
            var separadorCuartos = new Separator(Orientation.HORIZONTAL);
            separadorCuartos.getStyleClass().add("claro");
            separadores.getChildren().add(separadorCuartos);
        }
        separadores.setSpacing(14);
        return separadores;
    }

    private void crearConstraints() {
        var constraints = new RowConstraints();
        constraints.setMaxHeight(60);
        constraints.setMinHeight(60);
        constraints.setPrefHeight(60);
        constraints.setValignment(VPos.TOP);
        grillaDiario.getRowConstraints().add(constraints);
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Diario/diarioLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
