package Algo3.Componentes;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Apilable extends Celda {
    @FXML
    private Label horario;
    @FXML
    private Label titulo;
    @FXML
    private CheckBox completada;
    @FXML
    private Label horaInLine;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonAlarma;
    private StringProperty tituloDeAsignable ;
    private String horarioInicio = "";
    private ObjectProperty<LocalTime> horarioFin;
    private int filaInicio;
    private int offsetInicio;
    private IntegerProperty filaFin;
    public Apilable(int filaInicio){//constructor desde arrastre
        cargarFXML("src/main/resources/Layouts/Componente/apilableLayout.fxml");
        agregarEstilos();
        limitarTamanio();
        cambioSuaveDePadding();
        listenerCompletado();

        tituloDeAsignable = new SimpleStringProperty("(Sin Titulo)");
        titulo.textProperty().bind(tituloDeAsignable);

        this.filaInicio = filaInicio;
        this.offsetInicio = 0;
        this.filaFin = new SimpleIntegerProperty(filaInicio);
        AnchorPane.setTopAnchor(this,(double) this.filaInicio*15);

        //filaInicio/4 : hora --- filaInicio%4 * 15 : minutos
        horarioInicio = formatearHora(LocalTime.of(filaInicio/4,filaInicio%4 * 15,0));
        horarioFin = new SimpleObjectProperty<>(LocalTime.of(filaInicio/4,filaInicio%4 * 15));
        horarioDinamico();
        completada.visibleProperty().bind(esTarea);
    }
    public Apilable(String tituloDeAsignable, LocalDateTime fechaInicio, LocalDateTime fechaFin, LocalDate hoy){
        cargarFXML("src/main/resources/Layouts/Componente/apilableLayout.fxml");
        agregarEstilos();
        limitarTamanio();
        cambioSuaveDePadding();
        listenerCompletado();
        if(fechaInicio.toLocalDate() != fechaFin.toLocalDate()){
            if(fechaInicio.toLocalDate().isBefore(hoy) && fechaFin.toLocalDate().isAfter(hoy)){
                fechaFin = LocalDateTime.of(hoy,LocalTime.of(23,59));
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
        this.tituloDeAsignable =  new SimpleStringProperty(tituloDeAsignable);
        titulo.textProperty().bind(this.tituloDeAsignable);

        this.filaInicio = fechaInicio.getHour()*4 + fechaInicio.getMinute()/15;
        this.offsetInicio = fechaInicio.getMinute()%15;
        this.filaFin = new SimpleIntegerProperty(fechaFin.getHour()*4+ fechaFin.getMinute()/15);
        AnchorPane.setTopAnchor(this,(double) filaInicio*15 + offsetInicio);

        //filaInicio/4 : hora --- filaInicio%4 * 15 : minutos
        horarioInicio = formatearHora(LocalTime.of(filaInicio/4,filaInicio%4 * 15 + offsetInicio));
        horarioFin = new SimpleObjectProperty<>(fechaFin.toLocalTime());
        horarioDinamico();
        double altura = fechaFin.getHour()*60+ fechaFin.getMinute() -
                (fechaInicio.getHour()*60 + fechaInicio.getMinute());
        setNuevaAltura(altura);
        completada.visibleProperty().bind(esTarea);
    }
    private void cambioSuaveDePadding(){
        StringBinding paddingHorario = new StringBinding() {
            {
                super.bind(prefHeightProperty());
            }
            @Override
            protected String computeValue() {
                if(prefHeightProperty().get() < 34){
                    return "-fx-padding: -6 0 0 0";
                }else{
                    return "-fx-padding: " + Math.min(prefHeightProperty().get()-39,3) + " 0 0 0";
                }
            }
        };
        horario.styleProperty().bind(paddingHorario);

        StringBinding paddingTitulo = new StringBinding() {
            {
                super.bind(prefHeightProperty());
            }
            @Override
            protected String computeValue() {
                if(prefHeightProperty().get() < 45){
                    return "-fx-padding: -6 0 0 0";
                }else{
                    return "-fx-padding: " + Math.min(prefHeightProperty().get()-50,0) + " 0 0 0";
                }
            }
        };
        titulo.styleProperty().bind(paddingTitulo);
        completada.styleProperty().bind(paddingTitulo);
        StringBinding paddingBotones = new StringBinding() {
            {
                super.bind(prefHeightProperty());
            }
            @Override
            protected String computeValue() {
                if(prefHeightProperty().get() < 45){
                    return "-fx-padding: -2 0 0 0";
                }else{
                    return "-fx-padding: " + Math.min(prefHeightProperty().get()-47,3) + " 0 0 0";
                }
            }
        };
        botonEditar.styleProperty().bind(paddingBotones);
        botonBorrar.styleProperty().bind(paddingBotones);
        botonAlarma.styleProperty().bind(paddingBotones);
    }
    private void horarioDinamico(){
        StringBinding horarioAmostrar = new StringBinding() {
            {
                super.bind(horarioFin);
            }
            @Override
            protected String computeValue() {
                var horaFinText = formatearHora(horarioFin.get());
                if(horarioInicio.equals(horaFinText) &&
                !(getStyleClass().contains("no-empieza-hoy") || getStyleClass().contains("no-termina-hoy"))){
                    return "Dia Completo";
                }
                return horarioInicio+ " a " +horaFinText;
            }
        };

        horario.textProperty().bind(horarioAmostrar);

        horaInLine.textProperty().bind(
                Bindings.when(horaInLine.visibleProperty())
                        .then(horario.textProperty())
                        .otherwise(""));
        horaInLine.visibleProperty().bind(horario.visibleProperty().not());
    }
    public void setNuevaAltura(double altura) {
        filaFin.set(Math.max(filaInicio  + ((int)(altura+14+offsetInicio)/15)-1,0));
        this.setPrefHeight(altura);
        this.horario.setVisible(altura >= 30);
        if(altura < 45){
            botonesPadding(new Insets(-2,0,0,0));
        }else{
            botonesPadding(new Insets(3,0,0,0));
        }
        reescribirHorario(altura);
    }
    private void reescribirHorario(double nuevaAltura) {
        nuevaAltura += (filaInicio*15)%60 + offsetInicio;
        var horaEntera = (int)nuevaAltura/60 + filaInicio/4;
        var minutos = (int)nuevaAltura%60;
        if(horaEntera == 24){
            horarioFin.set(LocalTime.of(23,59,0));
        }else{
            horarioFin.set(LocalTime.of(horaEntera,minutos,0));
        }
    }
    private void limitarTamanio() {
        this.setPrefHeight(18);
        this.setMaxHeight(-1);
        this.setMaxWidth(-1);
        this.setMinHeight(18);
    }
    private void agregarEstilos(){
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/celda.css").toUri().toString());
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/apilable.css").toUri().toString());
        horario.getStyleClass().add("texto-blanco");
        horario.setVisible(false);
        horario.setPadding(new Insets(-6,0,0,0));
        horaInLine.getStyleClass().add("texto-blanco");
        titulo.getStyleClass().addAll("texto-blanco","titulo","alinear-centro");
        titulo.setPadding(new Insets(-6, 0, 0, 0));
        this.getStyleClass().add("contenedor");
        botonesPadding(new Insets(-2,0,0,0));
    }
    private void botonesPadding(Insets insets){
        botonBorrar.setPadding(insets);
        botonEditar.setPadding(insets);
        botonAlarma.setPadding(insets);
    }
    public boolean haySuperposicion(Apilable otro){
        var estasOcupadas = this.filasOcupadas();
        var otrasOcupadas = otro.filasOcupadas();
        estasOcupadas.retainAll(otrasOcupadas);
        return estasOcupadas.size()>0;
    }
    public List<Integer> filasOcupadas(){
        List<Integer> ocupadas = new ArrayList<>();
        for(int i = filaInicio; i<=filaFin.getValue();i++){
            ocupadas.add(i);
        }
        return ocupadas;
    }
    public LocalTime getHoraFinal() {
        return horarioFin.get();
    }
}