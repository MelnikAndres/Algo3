package Algo3.Vista;

import Algo3.Controlador.CalendarioControlador;
import Algo3.Componentes.Menu;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class MainVista extends HBox {

    public MainVista(ReadOnlyDoubleProperty widthProperty){
        cargarFXML();
        this.prefWidthProperty().bind(widthProperty);
        var calendarioControlador =  new CalendarioControlador(widthProperty,this.heightProperty());
        this.getChildren().add(calendarioControlador.getRoot());
    }
    public void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/mainLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
