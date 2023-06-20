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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    public CeldaSemanal(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFin, LocalDate hoy){
        cargarFXML("src/main/resources/Layouts/Componente/celdaSemanalLayout.fxml");
        listenerCompletado();
        this.getStyleClass().add("contenedor");
        if(fechaInicio.toLocalDate() != fechaFin.toLocalDate()){
            if(fechaInicio.toLocalDate().isBefore(hoy) && fechaFin.toLocalDate().isAfter(hoy)){
                fechaFin = LocalDateTime.of(hoy, LocalTime.of(23,59));
                fechaInicio = LocalDateTime.of(hoy,LocalTime.of(0,0));
                getStyleClass().add("todo-el-dia");
            }else if(fechaInicio.toLocalDate().isBefore(hoy)){
                fechaInicio = LocalDateTime.of(hoy,LocalTime.of(0,0));
                getStyleClass().add("no-empieza-hoy");
            }else if(fechaFin.toLocalDate().isAfter(hoy)){
                fechaFin = LocalDateTime.of(hoy,LocalTime.of(23,59));
                getStyleClass().add("no-termina-hoy");
            }
        }
        this.titulo.setText(titulo);
        if(fechaFin.equals(fechaInicio) && fechaInicio.toLocalDate() == fechaFin.toLocalDate()){
            this.horaInicial.setText("Dia Completo");
            this.horaFinal.setVisible(false);
        }else{
            this.horaInicial.setText(formatearHora(fechaInicio.toLocalTime()));
            this.horaFinal.setText(formatearHora(fechaFin.toLocalTime()));
        }
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/celda.css").toUri().toString());
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/celdaSemanal.css").toUri().toString());
        completada.visibleProperty().bind(esTarea);
    }
}
