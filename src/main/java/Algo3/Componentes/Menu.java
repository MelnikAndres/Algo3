package Algo3.Componentes;

import Algo3.Constantes.Dia;
import Algo3.Constantes.Mes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class Menu extends VBox {
    @FXML
    private SelectorCalendario selectorCalendario;
    @FXML
    private Label diaElegido;
    @FXML
    private Label mesElegido;
    @FXML
    private Label anioElegido;
    @FXML
    private VBox selectorRango;

    public Menu(){
        cargarFXML();
        asignarFecha(selectorCalendario.getFecha());
        selectorCalendario.setOnAction(actionEvent -> asignarFecha(selectorCalendario.getFecha()));
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/menu.css").toUri().toString());
    }

    private void asignarFecha(LocalDate fechaActual){
        String diaMayusculas =Dia.values()[fechaActual.getDayOfWeek().getValue()-1].name();
        String dia = diaMayusculas.charAt(0) + diaMayusculas.substring(1).toLowerCase();

        diaElegido.setText(dia + " " + fechaActual.getDayOfMonth());

        String mesMayusculas = Mes.values()[fechaActual.getMonth().getValue()-1].name();
        String mes = mesMayusculas.charAt(0) + mesMayusculas.substring(1).toLowerCase();

        mesElegido.setText(mes);

        anioElegido.setText(String.valueOf(fechaActual.getYear()));
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/menuLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
