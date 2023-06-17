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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class CeldaSemanal extends VBox {
    @FXML
    private Label titulo;
    @FXML
    private Label horaInicial;
    @FXML
    private Label horaFinal;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonAlarma;
    @FXML
    private CheckBox completada;
    private BooleanProperty esTarea = new SimpleBooleanProperty(false);

    public CeldaSemanal(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        cargarFXML();
        listenerCompletado();
        this.titulo.setText(titulo);
        this.horaInicial.setText(formatearHora(fechaInicio));
        this.horaFinal.setText(formatearHora(fechaFinal));
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/celda.css").toUri().toString());
        completada.visibleProperty().bind(esTarea);

    }
    private void listenerCompletado(){
        completada.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    getStyleClass().clear();
                    getStyleClass().add("contenedor-completado");
                }else{
                    getStyleClass().clear();
                    getStyleClass().add("contenedor");
                }
            }
        });
    }
    private String formatearHora(LocalDateTime aFormatear){
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
    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Componente/celdaSemanalLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
