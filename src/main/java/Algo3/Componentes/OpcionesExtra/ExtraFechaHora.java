package Algo3.Componentes.OpcionesExtra;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExtraFechaHora extends HBox implements OpcionExtra {

    @FXML
    private ScrollPane horaScroll;
    @FXML
    private ScrollPane minutoScroll;
    @FXML
    private VBox horasContainer;
    @FXML
    private VBox minutosContainer;
    @FXML
    private DatePicker fechaActual;
    private ToggleGroup grupoHoras = new ToggleGroup();
    private ToggleGroup grupoMinutos = new ToggleGroup();



    public ExtraFechaHora(){
        cargarFXML();
        cargarOpciones();
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/OpcionesExtra/extraFechaHora.css").toUri().toString());
        horaScroll.getStyleClass().addAll("fondo-primario","borde-Y");
        minutoScroll.getStyleClass().addAll("fondo-primario","borde-Y");
        horasContainer.getStyleClass().add("fondo-primario");
        minutosContainer.getStyleClass().add("fondo-primario");
    }

    private void cargarOpciones(){
        horasContainer.setSpacing(4);
        minutosContainer.setSpacing(4);
        for(int i=0; i<24;i++){
            var boton = new ToggleButton(String.valueOf(i));
            boton.setMaxWidth(1000);
            grupoHoras.getToggles().add(boton);
            boton.getStyleClass().add("boton-hora");
            horasContainer.getChildren().add(boton);
        }
        for(int i =0; i<60;i++){
            var boton = new ToggleButton(String.valueOf(i));
            boton.setMaxWidth(1000);
            grupoMinutos.getToggles().add(boton);
            boton.getStyleClass().add("boton-hora");
            minutosContainer.getChildren().add(boton);
        }
    }

    @Override
    public void setValor(String valor) {
        var fechaYhoraSeparados = valor.split("T");
        var fechaSeparada = fechaYhoraSeparados[0].split("-");
        LocalDate fecha = LocalDate.of(
                Integer.parseInt(fechaSeparada[0]),
                Integer.parseInt(fechaSeparada[1]),
                Integer.parseInt(fechaSeparada[2])
        );
        var horaSeparada = fechaYhoraSeparados[1].split(":");
        grupoHoras.getToggles().get(Integer.parseInt(horaSeparada[0])).setSelected(true);
        grupoMinutos.getToggles().get(Integer.parseInt(horaSeparada[1])).setSelected(true);
        fechaActual.setValue(fecha);
    }

    @Override
    public String getValor() {
        LocalTime hora = LocalTime.of(Integer.parseInt(((ToggleButton)grupoHoras.getSelectedToggle()).getText()),
                Integer.parseInt(((ToggleButton)grupoMinutos.getSelectedToggle()).getText()),0);
        return LocalDateTime.of(fechaActual.getValue(),hora).toString();
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Dialogo/extrasFechaHoraLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }
}
