package Algo3.Vista;

import Algo3.Componentes.Menu;
import Algo3.Componentes.Sombreador;
import Algo3.Controlador.CalendarioControlador;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class MainVista extends HBox {

    public MainVista(ReadOnlyDoubleProperty widthProperty){
        cargarFXML();
        this.prefWidthProperty().bind(widthProperty);
        var calendarioControlador =  new CalendarioControlador(widthProperty,this.heightProperty());
        var menu = new Menu();
        var hbox = new HBox();
        hbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        var separator = new Pane();
        separator.setMinWidth(3);
        separator.setPrefWidth(3);
        separator.setMaxWidth(3);
        separator.prefHeightProperty().bind(this.heightProperty());
        separator.minHeightProperty().bind(this.heightProperty());
        separator.maxHeightProperty().bind(this.heightProperty());
        separator.setStyle("-fx-background-color: derive(#142131,45%);-fx-translate-x: -1");
        HBox.setMargin(menu, new Insets(0,0,0,-1));
        Sombreador.sombrear(separator);
        hbox.getChildren().addAll(separator,menu);
        menu.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        this.getChildren().add(hbox);
        this.getChildren().add(calendarioControlador.getRoot());
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/mainVista.css").toUri().toString());
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
