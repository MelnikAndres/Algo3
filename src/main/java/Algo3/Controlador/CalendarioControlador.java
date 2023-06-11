package Algo3.Controlador;

import Algo3.Componentes.Apilable;
import Algo3.Componentes.ApiladorDeAsignables;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.nio.file.Path;

public class CalendarioControlador{
    @FXML
    GridPane grillaDiario;
    @FXML
    ScrollPane scrollDiario;
    @FXML
    ApiladorDeAsignables apiladorDeAsignables;
    @FXML
    StackPane container;
    @FXML
    private ColumnConstraints columnaHoras;
    @FXML
    private ColumnConstraints columnaAsignables;
    private Integer filaInicioDeArrastre;
    private Apilable actualAgregando;
    public CalendarioControlador(ReadOnlyDoubleProperty widthProperty,
                                 ReadOnlyDoubleProperty heightProperty){
        cargarFXML();
        definirTamanios(widthProperty, heightProperty);
        crearGrilla();
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
            this.filaInicioDeArrastre = null;
            this.actualAgregando = null;
        });
    }

    private void definirTamanios(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        widthProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrollDiario.setPrefWidth(t1.doubleValue()-250);
                apiladorDeAsignables.setPrefWidth(t1.doubleValue()-341);
                apiladorDeAsignables.setMaxWidth(t1.doubleValue()-341);
            }
        });
        heightProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrollDiario.setMaxHeight(t1.doubleValue());
            }
        });
        grillaDiario.prefWidthProperty().bind(apiladorDeAsignables.prefWidthProperty().add(60));
        grillaDiario.minWidthProperty().bind(apiladorDeAsignables.widthProperty().add(60));
    }

    private void crearGrilla() {
        grillaDiario.getStylesheets().add(Path.of("src/main/java/Algo3/Controlador/separator.css").toUri().toString());
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

    private void agregarNuevo(){
        actualAgregando = new Apilable(filaInicioDeArrastre);
        apiladorDeAsignables.getChildren().add(actualAgregando);
        AnchorPane.setTopAnchor(actualAgregando,(double) this.filaInicioDeArrastre*15);
    }
    private void expandirNuevo(double yFinal){
        double altura = yFinal - this.filaInicioDeArrastre*15;
        if(altura>0){
            actualAgregando.setNuevaAltura(Math.max(altura,15));
        }
    }
    private Integer obtenerPosicionEnCalendario(double y){
        return (int) ((24*y*4)/apiladorDeAsignables.getHeight());
    }
    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Diario/diarioLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Parent getRoot(){
        return scrollDiario;
    }
}
