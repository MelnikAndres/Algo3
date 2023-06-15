package Algo3.Componentes.OpcionesExtra;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class ExtraFechaHora extends HBox {

    @FXML
    private ScrollPane horaScroll;
    @FXML
    private ScrollPane minutoScroll;
    @FXML
    private VBox horasContainer;
    @FXML
    private VBox minutosContainer;
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

    public void cargarOpciones(){
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



    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Dialogo/extrasFechaHoraLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }
}
