package Algo3.Componentes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class Apilable extends VBox {
    private Label horario;
    private Label tituloDeApilable;
    private String tituloDeAsignable;
    private int filaInicio;
    private int filaFin;
    public Apilable(String tituloDeAsignable, int altura, int filaInicio, int filaFin){
        this.tituloDeAsignable = tituloDeAsignable;
        tituloDeApilable = new Label(altura<45?tituloDeAsignable+ " - "+ "12:00":tituloDeAsignable);
        horario = new Label("12:00 a 13:00");
        if(altura<45){
            horario.setVisible(false);
        }
        this.getChildren().add(tituloDeApilable);
        this.getChildren().add(horario);
        this.filaInicio = filaInicio;
        this.filaFin = filaFin;
        this.limitarTamanio(altura);
        this.agregarEstilos();
    }

    private void limitarTamanio(int altura) {
        this.setPrefHeight(altura);
        this.setMaxHeight(-1);
        this.setMaxWidth(-1);
        this.setMinHeight(18);
    }

    private void agregarEstilos(){
        horario.setStyle("-fx-text-fill: #efefef");
        tituloDeApilable.setStyle("-fx-font-weight: bold;-fx-text-fill: #efefef");
        tituloDeApilable.setTextAlignment(TextAlignment.CENTER);
        this.setStyle("-fx-background-color: #3bc4ee;-fx-border-color: #efefef;" +
                "-fx-border-radius: 5 5 5 5;-fx-background-radius: 5 5 5 5");
        this.setPadding(new Insets(0,10,0,10));
    }
    public void setNuevaAltura(int altura) {
        this.setPrefHeight(altura);
        this.horario.setVisible(altura >= 45);
        this.tituloDeApilable.setText(altura<45?tituloDeAsignable+ " - "+ "12:00":tituloDeAsignable);
    }
    public void setFilaFin(int filaFin) {
        this.filaFin = filaFin;
    }
    public boolean verSuperposicion(Apilable otro){
        var estasOcupadas = this.filasOcupadas();
        var otrasOcupadas = otro.filasOcupadas();
        estasOcupadas.retainAll(otrasOcupadas);
        return estasOcupadas.size()>0;
    }

    public List<Integer> filasOcupadas(){
        List<Integer> ocupadas = new ArrayList<>();
        for(int i = filaInicio; i<=filaFin;i++){
            ocupadas.add(i);
        }
        return ocupadas;
    }
}