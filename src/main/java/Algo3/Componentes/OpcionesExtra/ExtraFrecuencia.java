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

public class ExtraFrecuencia extends VBox implements OpcionExtra {
    @FXML
    private ChoiceBox<FrecuenciaTipo> opciones;

    private OpcionesExtra opcionesExtra = new OpcionesExtra();
    ExtraFrecuencia(){
        cargarFXML();
        agregarOpciones();
        agregarListener();
        getChildren().add(opcionesExtra);
    }

    private void agregarOpciones(){
        for(FrecuenciaTipo frecuencia: FrecuenciaTipo.values()){
            opciones.getItems().add(frecuencia);
        }
    }

    private void agregarListener(){
        opciones.valueProperty().addListener(new ChangeListener<FrecuenciaTipo>() {
            @Override
            public void changed(ObservableValue<? extends FrecuenciaTipo> observableValue, FrecuenciaTipo frecuenciaTipo, FrecuenciaTipo t1) {
                switch (t1){
                    case DIARIA -> opcionesExtra.addExtra(ParametroTipo.INTERVALO);
                    case SEMANAL -> opcionesExtra.addExtra(ParametroTipo.DIASDESEMANA);
                    case MENSUAL, ANUAL, NULA -> opcionesExtra.addExtra();
                }
            }
        });
    }

    public String getValor() {
        return opciones.getValue().name() + ";"+opcionesExtra.getValor();
    }
    public void setValor(String valor){
        String[] valorSplit = valor.split(";");
        opciones.setValue(FrecuenciaTipo.valueOf(valorSplit[0]));
        opcionesExtra.setValor(valorSplit[1]);
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Dialogo/extrasFrecuenciaLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }
}
