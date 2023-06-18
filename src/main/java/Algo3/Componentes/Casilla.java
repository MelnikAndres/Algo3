package Algo3.Componentes;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Casilla extends VBox {
    private final LocalDate fecha;
    private Label numeroDia;
    private ObservableList<CeldaMensual> hijos = FXCollections.observableArrayList();
    private int cantidadHijos = 0;
    private Label hayMas = new Label("");

    public Casilla(LocalDate fecha){
        this.fecha = fecha;
        agregarNumeroDia();
        hayMas.setStyle("-fx-text-fill: white;-fx-padding: 3 0 0 0");
    }
    public Casilla(LocalDate fecha, String diaDeSemana){
        this.fecha = fecha;
        agregarNombreDia(diaDeSemana);
        agregarNumeroDia();
        hayMas.setStyle("-fx-text-fill: white;-fx-padding: 3 0 0 0");
    }

    private void agregarNumeroDia(){
        numeroDia =  new Label(String.valueOf(fecha.getDayOfMonth()));
        numeroDia.setTextAlignment(TextAlignment.CENTER);
        numeroDia.setStyle("-fx-text-fill: white");
        getChildren().add(numeroDia);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-border-color: derive(#142131, 40%)");
    }
    private void agregarNombreDia(String diaDeSemana){
        var labelDia = new Label (diaDeSemana);
        labelDia.setAlignment(Pos.TOP_CENTER);
        labelDia.setStyle("-fx-text-fill: white");
        getChildren().add(labelDia);
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setNoCorrespondeAlMes(){
        setStyle("-fx-border-color: derive(#142131, 40%); -fx-background-color: derive(#142131, 15%)");
        numeroDia.setStyle("-fx-text-fill: derive(#142131, 85%)");
    }

    public void agregarNuevo(CeldaMensual celdaMensual){
        hijos.add(celdaMensual);
        cantidadHijos = hijos.size();
        if(hijos.size()<=4){
            getChildren().add(celdaMensual);
        }else{
            getChildren().remove(hayMas);
            hayMas.setText(cantidadHijos-4 + " Mas");
            getChildren().add(hayMas);
        }
    }


}
