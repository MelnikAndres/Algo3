package Algo3.Componentes;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Apilable extends VBox {
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label horario;
    @FXML
    private Label titulo;
    @FXML
    private Label horaInLine;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonFinal;
    private StringProperty tituloDeAsignable = new SimpleStringProperty("(Sin Titulo)");
    private String horarioInicio = "";
    private StringProperty horarioFin;
    private BooleanProperty reescalando = new SimpleBooleanProperty(false);
    private int filaInicio;
    private IntegerProperty filaFin;
    public Apilable(int filaInicio){
        cargarFXML();
        agregarEstilos();
        limitarTamanio();
        permitirReescalar();
        this.filaInicio = filaInicio;
        this.filaFin = new SimpleIntegerProperty(filaInicio);
        var hIn = filaInicio/4;
        var hQIn = filaInicio%4 * 15;
        horarioInicio = calcularHorario(hIn,hQIn);
        horarioFin = new SimpleStringProperty(horarioInicio);
        titulo.textProperty().bind(tituloDeAsignable);
        cambioSuaveDePadding();
        horarioDinamico();
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
        botonFinal.styleProperty().bind(paddingBotones);
    }
    private void horarioDinamico(){
        StringBinding horarioAmostrar = new StringBinding() {
            {
                super.bind(horarioFin);
            }
            @Override
            protected String computeValue() {
                return horarioInicio+ " a " +horarioFin.get();
            }
        };

        horario.textProperty().bind(horarioAmostrar);

        horaInLine.textProperty().bind(
                Bindings.when(horaInLine.visibleProperty())
                        .then(horario.textProperty())
                        .otherwise(""));
        horaInLine.visibleProperty().bind(horario.visibleProperty().not());
    }
    public void setTitulo(String tituloNuevo){
        tituloDeAsignable.setValue(tituloNuevo);
        titulo.setText(tituloNuevo);
    }
    public void setNuevaAltura(double altura) {
        this.setPrefHeight(altura);
        this.horario.setVisible(altura >= 30);
        if(altura < 45){
            botonesPadding(new Insets(-2,0,0,0));
        }else{
            botonesPadding(new Insets(3,0,0,0));
        }
        int alturaDiscreta = ((int)(altura+14)/15)*15;
        filaFin.set(filaInicio  + (alturaDiscreta/15)-1);
        reescribirHorario(altura);
    }
    public void reescribirHorario(double nuevaAltura) {
        nuevaAltura += (filaInicio*15)%60;
        var hIn = (int)nuevaAltura/60 + filaInicio/4;
        var hQIn = (int)nuevaAltura%60;
        horarioFin.set(calcularHorario(hIn,hQIn));
    }

    private String calcularHorario(int hIn, int hQIn){
        String hQtext = hQIn<10?"0"+hQIn:String.valueOf(hQIn);
        if(hIn <12){
            if(hIn == 0){
                return "12:"+hQtext+" AM";
            }else{
                return hIn+":"+hQtext+" AM";
            }
        }else{
            if(hIn == 12){
                return "12:"+hQtext+" PM";
            }else{
                return hIn-12+":"+hQtext+" PM";
            }
        }
    }

    private void limitarTamanio() {
        this.setPrefHeight(18);
        this.setMaxHeight(-1);
        this.setMaxWidth(-1);
        this.setMinHeight(18);
    }

    private void agregarEstilos(){
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/apilable.css").toUri().toString());
        horario.getStyleClass().add("texto-blanco");
        horario.setVisible(false);
        horario.setPadding(new Insets(-6,0,0,0));
        horaInLine.getStyleClass().add("texto-blanco");
        titulo.getStyleClass().addAll("texto-blanco","titulo-apilable","alinear-centro");
        titulo.setPadding(new Insets(-6, 0, 0, 0));
        this.getStyleClass().addAll("contenedor-apilable");
        botonBorrar.getStyleClass().add("boton-apilable");
        botonEditar.getStyleClass().add("boton-apilable");
        botonFinal.getStyleClass().add("boton-apilable");
        botonesPadding(new Insets(-2,0,0,0));
    }
    private void botonesPadding(Insets insets){
        botonBorrar.setPadding(insets);
        botonEditar.setPadding(insets);
        botonFinal.setPadding(insets);
    }

    public void addBorrarEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonBorrar.setOnAction(handler);
    }
    public void addEditarEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonEditar.setOnAction(handler);
    }
    public void addFinalEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonFinal.setOnAction(handler);
    }
    public BooleanProperty getReescalandoProperty(){
        return reescalando;
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
    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Diario/apilableLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void permitirReescalar() {
        var valoresIniciales = new Object(){
            private double yInicial;
            private double alturaInicial;
        };
        var eventHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                var y = event.getY();
                var height = getHeight()-3;
                if(height - y<=6){
                    valoresIniciales.yInicial = event.getSceneY();
                    valoresIniciales.alturaInicial = height;
                    reescalando.set(true);
                    event.consume();
                }
            }
        };
        addEventHandler(MouseEvent.MOUSE_PRESSED,eventHandler);
        addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(reescalando.get()){
                    var nuevaAltura = (valoresIniciales.alturaInicial + (event.getSceneY()- valoresIniciales.yInicial));
                    setNuevaAltura(Math.max(nuevaAltura,15));
                    event.consume();
                }
            }
        });
        addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reescalando.setValue(false);
            }
        });
        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                var y = event.getY();
                var height = getHeight()-3;
                if(height - y<=6){
                    setCursor(Cursor.V_RESIZE);
                }else{
                    setCursor(Cursor.DEFAULT);
                }
            }
        });
    }

}