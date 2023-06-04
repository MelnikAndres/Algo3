package Algo3.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MenuControlador {
    @FXML
    private VBox root;
    @FXML
    private GridPane selectorDeDia;
    @FXML
    private DatePicker datePicker;
    @FXML
    private StackPane datePickerPane;
    @FXML
    private Label fechaElegida;

    MenuControlador(){
        cargarFXML();
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);
        datePicker.setVisible(false);
        fechaElegida.setText(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.getRoot().getChildren().remove(datePicker);
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        datePickerPane.getChildren().add(popupContent);
        datePicker.setOnAction(actionEvent -> fechaElegida.setText(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/menuLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public VBox getRoot(){

        return root;
    }

    private void cargarSelector(){

    }
}
