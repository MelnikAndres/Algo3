package Algo3.Controlador;

import Algo3.Componentes.Apilable;
import Algo3.Componentes.ApiladorDeAsignables;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CalendarioControlador{
    @FXML
    GridPane grillaDiario;
    @FXML
    ScrollPane scrollDiario;
    ApiladorDeAsignables apiladorDeAsignables;
    @FXML
    StackPane container;
    @FXML
    private ColumnConstraints columnaHoras;
    private Integer filaInicioDeArrastre;
    private Integer filaFinDeArrastre;
    private Apilable actualAgregando;
    public CalendarioControlador(double ancho, double alto){
        cargarFXML();
        scrollDiario.setPrefViewportHeight(alto-20);
        scrollDiario.setPrefViewportWidth(ancho);
        apiladorDeAsignables = new ApiladorDeAsignables(24,4);
        container.getChildren().add(apiladorDeAsignables);
        for (int i = 0; i < 24; i++) {
            var constraints = new RowConstraints();
            constraints.setMaxHeight(60);
            constraints.setMinHeight(60);
            constraints.setPrefHeight(60);
            constraints.setValignment(VPos.TOP);
            grillaDiario.getRowConstraints().add(constraints);
            var horaLabel = new Label();
            var separator = new Separator(Orientation.HORIZONTAL);
            var separator2 = new Separator(Orientation.VERTICAL);
            var pane = new StackPane();
            horaLabel.setPrefHeight(60);
            horaLabel.setPrefWidth(60);
            separator2.setPrefHeight(60);
            horaLabel.setTextAlignment(TextAlignment.CENTER);
            horaLabel.setAlignment(Pos.CENTER);
            if(i < 12){
                horaLabel.setText((i==0?12:i) + " AM");
            }else{
                horaLabel.setText((i==12?12:i-12) + " PM");
            }
            pane.setAlignment(Pos.CENTER_RIGHT);
            pane.getChildren().add(separator2);
            pane.getChildren().add(horaLabel);
            if(i>0){
                grillaDiario.addRow(i, pane,separator);
            }else{
                grillaDiario.addRow(i, pane);
            }
        }
        apiladorDeAsignables.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)){
                return;
            }
            this.filaInicioDeArrastre = obtenerPosicionEnCalendario(event.getY(), apiladorDeAsignables.getHeight(),false);
            this.filaFinDeArrastre = obtenerPosicionEnCalendario(event.getY(), apiladorDeAsignables.getHeight(),false);
        });
        apiladorDeAsignables.setOnMouseDragged(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)|| this.filaInicioDeArrastre == null){
                return;
            }
            var letras = List.of("A","b","c","d","e","f","g");
            if(this.actualAgregando == null ){
                var tituloText = "";
                for(int i = 0; i<1+Math.floor(Math.random()*50);i++){
                    tituloText = tituloText.concat(letras.get((int) Math.floor(Math.random()*5)));
                }
                agregarNuevo(tituloText);
            }
            this.filaFinDeArrastre = obtenerPosicionEnCalendario(event.getY(), apiladorDeAsignables.getHeight(),true);
            expandirNuevo();
        });
        apiladorDeAsignables.setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)){
                return;
            }
            if(actualAgregando == null){
                this.filaInicioDeArrastre = null;
                this.filaFinDeArrastre = null;
                return;
            }
            apiladorDeAsignables.apilar(actualAgregando);
            this.filaInicioDeArrastre = null;
            this.filaFinDeArrastre = null;
            this.actualAgregando = null;
        });
        grillaDiario.setPrefHeight(alto);
        grillaDiario.setPrefWidth(ancho-15);
        apiladorDeAsignables.setPrefWidth(ancho - 71);
        StackPane.setMargin(apiladorDeAsignables,new Insets(0,0,0,56));
        apiladorDeAsignables.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                grillaDiario.setPrefWidth(t1.doubleValue() + 56);
            }
        });
    }
    private void agregarNuevo(String titulo){
        var nuevoAgregado= new Apilable(titulo, 15, filaInicioDeArrastre, filaInicioDeArrastre);
        actualAgregando = nuevoAgregado;
        actualAgregando.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.getButton().equals(MouseButton.SECONDARY)){
                    return;
                }
                apiladorDeAsignables.borrar(nuevoAgregado);
            }
        });
        AnchorPane.setTopAnchor(actualAgregando,(double) this.filaInicioDeArrastre*15);
        apiladorDeAsignables.getChildren().add(actualAgregando);
    }
    private void expandirNuevo(){
        int altura = 1 + this.filaFinDeArrastre - this.filaInicioDeArrastre;


        if(altura>0){
            actualAgregando.setNuevaAltura(Math.max(altura*15, 15));
            actualAgregando.setFilaFin(filaFinDeArrastre);
        }
    }
    private Integer obtenerPosicionEnCalendario(double y, double alto, boolean haciaAbajo){
        y = haciaAbajo?y-15:y;
        var fila = (int) ((24*y*4)/alto);
        return fila;
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
