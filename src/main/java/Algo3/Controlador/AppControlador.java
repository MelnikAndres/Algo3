package Algo3.Controlador;

import Algo3.Componentes.MenuBar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class AppControlador {
    @FXML
    VBox appPane;
    public AppControlador(double ancho, double alto){
        cargarFXML();
        var navBar = new MenuBar();
        var calendario = new CalendarioControlador(ancho, alto-17);
        var vbox = new VBox();
        var label = new Label("Calendario");
        var vista = new Button("Modo de Vista");
        vista.setPrefWidth(200);
        label.setPrefWidth(200);
        label.setAlignment(Pos.CENTER);
        vista.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: xx-large");
        vbox.getChildren().add(label);
        vbox.getChildren().add(vista);
        vbox.setPrefWidth(200);
        vbox.setMinWidth(200);
        vbox.setPrefHeight(alto-17);
        var hbox = new HBox();
        appPane.getChildren().add(navBar);
        appPane.getChildren().add(hbox);
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(calendario.getRoot());
    }
    public void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/appLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Pane getRoot(){
        return this.appPane;
    }
}
