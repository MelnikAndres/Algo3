package Algo3.Componentes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Celda extends VBox {
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonAlarma;
    @FXML
    private CheckBox completada;
    BooleanProperty esTarea = new SimpleBooleanProperty(false);
    void cargarFXML(String path){
        try {
            FXMLLoader loader =  new FXMLLoader(
                    Path.of(path).toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addBorrarEvent(EventHandler<ActionEvent> handler){
        botonBorrar.addEventHandler(ActionEvent.ACTION,handler);
    }
    public void addEditarEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonEditar.addEventHandler(ActionEvent.ACTION,handler);
    }
    public void addAlarmaEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonAlarma.addEventHandler(ActionEvent.ACTION,handler);
    }
    public void addCheckBoxListener(ChangeListener<Boolean> listener){
        completada.selectedProperty().addListener(listener);
    }
    public void setEsTarea(boolean esTarea){
        this.esTarea.set(esTarea);
    }
    public void setCheckBoxEstado(boolean nuevoEstado){
        completada.setSelected(nuevoEstado);
    }
    void listenerCompletado(){
        completada.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    getStyleClass().add("contenedor-completado");
                }else{
                    getStyleClass().remove("contenedor-completado");
                }
            }
        });
    }
    String formatearHora(LocalTime aFormatear){
        var minutos = aFormatear.getMinute();
        var horaEntera = aFormatear.getHour();
        String minutosTexto = minutos<10?"0"+minutos:String.valueOf(minutos);
        if(horaEntera <12){
            if(horaEntera == 0){
                return "12:"+minutosTexto+" AM";
            }else{
                return horaEntera+":"+minutosTexto+" AM";
            }
        }else{
            if(horaEntera == 12){
                return "12:"+minutosTexto+" PM";
            }else{
                return horaEntera-12+":"+minutosTexto+" PM";
            }
        }
    }


}
