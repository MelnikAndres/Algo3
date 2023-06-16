package Algo3.Vista;

import Algo3.Componentes.OpcionesExtra.ExtraFechaHora;
import Algo3.Componentes.OpcionesExtra.OpcionesExtra;
import Algo3.Constantes.ParametroTipo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogoEditarVista extends DialogPane {
    @FXML
    private ChoiceBox<String> selectorAsignableTipo;
    @FXML
    private VBox contenedor;

    private OpcionesExtra opcionesExtra = new OpcionesExtra();
    @FXML
    private TextField tituloField;
    @FXML
    private TextArea descripcionArea;
    @FXML
    private ToggleButton diaCompletoToggle;
    @FXML
    private HBox fechasContainer;
    @FXML
    private ExtraFechaHora fechaInicial;
    @FXML
    private ExtraFechaHora fechaFinal;


    public DialogoEditarVista(){
        cargarFXML();
        ObservableList<String> tipos = FXCollections.observableArrayList("Evento","Tarea");
        selectorAsignableTipo.setItems(tipos);
        contenedor.setSpacing(10);
        selectorAsignableTipo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("Evento")){
                    opcionesExtra.addExtra(ParametroTipo.REPETICION_FRECUENCIA);
                }else{
                    opcionesExtra.addExtra();
                }
            }
        });
        ((VBox)getContent()).getChildren().add(opcionesExtra);
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/dialogoEditarVista.css").toUri().toString());
        getStyleClass().add("contenedor");
        bindDiaCompleto();
        fechaInicial.setValor(LocalDateTime.now().toString());
        fechaFinal.setValor(LocalDateTime.now().toString());
        diaCompletoToggle.setSelected(true);
    }

    private void bindDiaCompleto(){
        diaCompletoToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                fechasContainer.setDisable(t1);
            }
        });
    }

    public void cargarValor(ParametroTipo tipo, String valor){
        switch (tipo){
            case TITULO -> tituloField.setText(valor);
            case DESCRIPCION -> descripcionArea.setText(valor);
            case DIACOMPLETO -> diaCompletoToggle.setSelected(valor.equals("1"));
            case FECHAINICIAL -> fechaInicial.setValor(valor);
            case FECHAFINAL -> fechaFinal.setValor(valor);
            case TIPO -> selectorAsignableTipo.setValue(valor);
            case REPETICION_FRECUENCIA -> opcionesExtra.setValor(valor);
        }
    }

    public HashMap<ParametroTipo,String> obtenerValores(){
        HashMap<ParametroTipo,String> valores = new HashMap<>();
        valores.put(ParametroTipo.TITULO,tituloField.getText());
        valores.put(ParametroTipo.DESCRIPCION,descripcionArea.getText());
        valores.put(ParametroTipo.DIACOMPLETO,diaCompletoToggle.isSelected()?"1":"");
        valores.put(ParametroTipo.FECHAINICIAL,fechaInicial.getValor());
        valores.put(ParametroTipo.FECHAFINAL,fechaFinal.getValor());
        valores.put(ParametroTipo.TIPO,selectorAsignableTipo.getValue());
        return valores;
    }
    public List<String> obtenerExtra(){
        List<String> extras = new ArrayList<>();
        if(selectorAsignableTipo.getValue().equals("Evento")){
            extras.add(opcionesExtra.getValor());
        }
        return extras;
    }

    private void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Dialogo/dialogoEditarLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
