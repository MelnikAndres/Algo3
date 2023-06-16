package Algo3.Vista;

import Algo3.Componentes.Menu;
import Algo3.Constantes.VistaTipo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class MainVista extends StackPane {
    @FXML
    private HBox contenido;
    @FXML
    private Menu menu;

    public MainVista(ReadOnlyDoubleProperty widthProperty){
        cargarFXML();
        contenido.prefWidthProperty().bind(widthProperty);
        prefWidthProperty().bind(widthProperty);
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/mainVista.css").toUri().toString());
    }

    public HBox getContenido(){
        return contenido;
    }

    public ObjectProperty<VistaTipo> getVistaActualProperty(){
        return menu.getVistaActualProperty();
    }
    public ObjectProperty<LocalDate> getFechaActualProperty(){
        return menu.getFechaActualProperty();
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

    public void addAgregarListener(EventHandler<ActionEvent> handler) {
        menu.addAgregarListener(handler);
    }
}
