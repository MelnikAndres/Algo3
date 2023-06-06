package Algo3.Componentes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SelectorCalendario extends StackPane {
    private DatePicker datePicker;

    public SelectorCalendario(){
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        this.getChildren().add(popupContent);
        agregarEstilos();

    }
    private void agregarEstilos(){
        this.setAlignment(Pos.TOP_CENTER);
        this.maxWidth(230);
        this.minWidth(230);
        this.prefWidth(230);
    }

    public void setOnAction(EventHandler<ActionEvent> handler){
        datePicker.setOnAction(handler);
    }
    public String getFecha(){
        return datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
