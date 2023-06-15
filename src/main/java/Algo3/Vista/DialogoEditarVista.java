package Algo3.Vista;

import Algo3.Componentes.OpcionesExtra.OpcionesExtra;
import Algo3.Constantes.ParametroTipo;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.io.IOException;
import java.nio.file.Path;

public class DialogoEditarVista extends DialogPane {
    @FXML
    private ChoiceBox<String> selectorAsignableTipo;
    @FXML
    private VBox contenedor;
    private OpcionesExtra opcionesExtra = new OpcionesExtra();

    public DialogoEditarVista(){
        cargarFXML();
        ObservableList<String> tipos = FXCollections.observableArrayList("Evento","Tarea");
        selectorAsignableTipo.setItems(tipos);
        contenedor.setSpacing(10);
        selectorAsignableTipo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("Evento")){
                    opcionesExtra.setExtra(ParametroTipo.FRECUENCIA,ParametroTipo.REPETICION);
                }else{
                    opcionesExtra.setExtra();
                }
            }
        });
        ((VBox)getContent()).getChildren().add(opcionesExtra);
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/dialogoEditarVista.css").toUri().toString());
        getStyleClass().add("contenedor");
    }

    public void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Dialogo/dialogoEditarLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
