package Algo3.Controlador;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class AppControlador {
    @FXML
    VBox appPane;
    private CalendarioControlador calendarioControlador;
    public AppControlador(ReadOnlyDoubleProperty widthProperty){
        cargarFXML();
        appPane.prefWidthProperty().bind(widthProperty);
        calendarioControlador =  new CalendarioControlador(widthProperty,appPane.heightProperty());
        var menuIzquierda = new MenuControlador();
        var hbox = new HBox();
        appPane.getChildren().add(hbox);
        hbox.getChildren().add(menuIzquierda.getRoot());
        hbox.getChildren().add(calendarioControlador.getRoot());
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
