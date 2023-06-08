package Algo3.Componentes;

import Algo3.Utilidad.Sombreador;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.StackPane;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SelectorCalendario extends StackPane {
    private DatePicker datePicker;

    public SelectorCalendario(){
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        popupContent.setEffect(null);
        this.getChildren().add(popupContent);
        agregarEstilos();

    }
    private void agregarEstilos(){
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/selectorCalendario.css").toUri().toString());
        this.getStyleClass().add("raiz");
        Sombreador.sombrear(this);
    }

    public void setOnAction(EventHandler<ActionEvent> handler){
        datePicker.setOnAction(handler);
    }
    public String getFecha(){
        return datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
