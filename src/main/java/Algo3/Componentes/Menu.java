package Algo3.Componentes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class Menu extends VBox {
    @FXML
    private SelectorCalendario selectorCalendario;
    @FXML
    private Label fechaElegida;
    @FXML
    private VBox tituloContainer;
    @FXML
    private VBox selectorRango;

    public Menu(){
        cargarFXML();
        fechaElegida.setText(selectorCalendario.getFecha());
        selectorCalendario.setOnAction(actionEvent -> fechaElegida.setText(selectorCalendario.getFecha()));
        Sombreador.sombrear(tituloContainer);
        Sombreador.sombrear(selectorRango);
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/menu.css").toUri().toString());
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/menuLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
