package Algo3.Componentes.OpcionesExtra;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class ExtraEntero extends VBox implements OpcionExtra {
    @FXML
    private TextField enteroInput;
    @FXML
    private Label titulo;

    ExtraEntero(String titulo){
        cargarFXML();
        this.titulo.setText(titulo);
        enteroInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    enteroInput.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Dialogo/extrasEnteroLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getValor() {
        return enteroInput.getText();
    }
    @Override
    public void setValor(String valor) {
        enteroInput.setText(valor);
    }
}
