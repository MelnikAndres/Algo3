package Algo3.Componentes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class Notificacion extends VBox {
    @FXML
    Label titulo;
    @FXML
    Button botonCerrar;

    public Notificacion(String texto, VBox notificacionVista){
        cargarFXML();
        titulo.setText(texto);
        var notificacion = this;

        botonCerrar.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                notificacionVista.getChildren().remove(notificacion);
            }
        });
    }
    public void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Componente/notificacionLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
