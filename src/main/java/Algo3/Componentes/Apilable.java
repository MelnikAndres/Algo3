package Algo3.Componentes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
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
        this.setMinHeight(17);
    }

    private void agregarEstilos(){
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/apilable.css").toUri().toString());
        horario.getStyleClass().add("texto-blanco");
        tituloDeApilable.getStyleClass().addAll("texto-blanco","titulo-apilable","alinear-centro");
        VBox.setMargin(tituloDeApilable, new Insets(-3,0,3,0));
        this.getStyleClass().addAll("fondo-primario","borde-secundario","contenedor-apilable");
    }
    public void setNuevaAltura(int altura) {
        this.setPrefHeight(altura);
        this.horario.setVisible(altura >= 45);
        this.tituloDeApilable.setText(altura<45?tituloDeAsignable+ " - "+ "12:00":tituloDeAsignable);
        if(altura < 18){
            VBox.setMargin(tituloDeApilable, new Insets(-3,0,3,0));
        }else{
            VBox.clearConstraints(tituloDeApilable);
        }
    }
    public void setFilaFin(int filaFin) {
        this.filaFin = filaFin;
    }
    public boolean haySuperposicion(Apilable otro){
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