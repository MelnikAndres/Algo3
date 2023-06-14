package Algo3.Componentes.OpcionesExtra;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.nio.file.Path;

public class ExtraIntervalo extends VBox {
    @FXML
    private TextField intervaloInput ;

    ExtraIntervalo(){
        cargarFXML();
        intervaloInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    intervaloInput.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Dialogo/extrasIntervaloLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }
}
