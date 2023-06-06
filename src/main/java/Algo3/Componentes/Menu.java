package Algo3.Componentes;

import Algo3.Componentes.SelectorCalendario;
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

    public Menu(){
        cargarFXML();
        fechaElegida.setText(selectorCalendario.getFecha());
        selectorCalendario.setOnAction(actionEvent -> fechaElegida.setText(selectorCalendario.getFecha()));
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
