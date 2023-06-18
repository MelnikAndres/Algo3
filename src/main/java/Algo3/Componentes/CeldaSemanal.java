package Algo3.Componentes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class CeldaSemanal extends Celda {
    @FXML
    Label titulo;
    @FXML
    Label horaInicial;
    @FXML
    Label horaFinal;
    @FXML
    Button botonBorrar;
    @FXML
    Button botonEditar;
    @FXML
    Button botonAlarma;
    @FXML
    CheckBox completada;
    public CeldaSemanal(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        cargarFXML("src/main/resources/Layouts/Componente/celdaSemanalLayout.fxml");
        listenerCompletado();
        this.titulo.setText(titulo);
        if(fechaFinal.equals(fechaInicio)){
            this.horaInicial.setText("Dia Completo");
            this.horaFinal.setVisible(false);
        }else{
            this.horaInicial.setText(formatearHora(fechaInicio.toLocalTime()));
            this.horaFinal.setText(formatearHora(fechaFinal.toLocalTime()));
        }
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/celda.css").toUri().toString());
        completada.visibleProperty().bind(esTarea);
    }
}
