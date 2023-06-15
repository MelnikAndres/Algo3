package Algo3.Componentes.OpcionesExtra;

import Algo3.Constantes.FrecuenciaTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class ExtraRepeticion extends VBox {
    @FXML
    private ChoiceBox<RepeticionTipo> opciones;
    private OpcionesExtra opcionesExtra = new OpcionesExtra();

    ExtraRepeticion(){
        cargarFXML();
        agregarOpciones();
        agregarListener();
        getChildren().add(opcionesExtra);
    }

    private void agregarOpciones(){
        for(RepeticionTipo repeticion: RepeticionTipo.values()){
            opciones.getItems().add(repeticion);
        }
    }
    private void agregarListener(){
        opciones.valueProperty().addListener(new ChangeListener<RepeticionTipo>() {
            @Override
            public void changed(ObservableValue<? extends RepeticionTipo> observableValue, RepeticionTipo repeticionTipo, RepeticionTipo t1) {
                switch (t1){
                    case FECHA_LIMITE -> opcionesExtra.setExtra(ParametroTipo.FECHA);
                    case CANTIDAD_LIMITE -> opcionesExtra.setExtra(ParametroTipo.CANTIDAD);
                    case INFINITO -> opcionesExtra.setExtra();
                }
            }
        });
    }
    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Dialogo/extrasRepeticionLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }
}
