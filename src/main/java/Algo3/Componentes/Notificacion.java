package Algo3.Componentes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Path;

public class Notificacion extends VBox {
    @FXML
    Label titulo;
    @FXML
    Button botonCerrar;

    public Notificacion(String texto, boolean esError){
        cargarFXML();
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/notificacion.css").toUri().toString());
        ImageView imageView;
        if(esError){
            getStyleClass().add("notificacion-error");
            titulo.setText("ERROR \n" + texto);
            imageView = new ImageView(new Image(Path.of("src/main/resources/Imagenes/error.png").toUri().toString()));
        }else{
            getStyleClass().add("notificacion");
            titulo.setText("Recordatorio para\n"+texto);
            imageView = new ImageView(new Image(Path.of("src/main/resources/Imagenes/alarma.png").toUri().toString()));
        }
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        getChildren().add(imageView);
    }
    public void addCerrarHandler(EventHandler<ActionEvent> handler){
        botonCerrar.addEventHandler(ActionEvent.ACTION, handler);
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
