package Algo3.Componentes.OpcionesExtra;

import Algo3.Constantes.RepeticionTipo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class ExtraRepeticion extends VBox {
    @FXML
    private ChoiceBox<RepeticionTipo> opciones;
    ExtraRepeticion(){
        cargarFXML();
        agregarOpciones();
    }

    private void agregarOpciones(){
        for(RepeticionTipo frecuencia: RepeticionTipo.values()){
            opciones.getItems().add(frecuencia);
        }
    }
    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Dialogo/extrasFrecuenciaLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }
}
