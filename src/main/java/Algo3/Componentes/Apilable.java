package Algo3.Componentes;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Apilable extends VBox {
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
    private StringProperty tituloDeAsignable ;
    private String horarioInicio = "";
    private StringProperty horarioFin;
    private BooleanProperty reescalando = new SimpleBooleanProperty(false);
    private int filaInicio;
    private int offsetInicio;

    private IntegerProperty filaFin;
    public Apilable(int filaInicio){//constructor desde arrastre
        cargarFXML();
        agregarEstilos();
        limitarTamanio();
        permitirReescalar();
        cambioSuaveDePadding();

        tituloDeAsignable = new SimpleStringProperty("(Sin Titulo)");
        titulo.textProperty().bind(tituloDeAsignable);

        this.filaInicio = filaInicio;
        this.offsetInicio = 0;
        this.filaFin = new SimpleIntegerProperty(filaInicio);
        AnchorPane.setTopAnchor(this,(double) this.filaInicio*15);

        //filaInicio/4 : hora --- filaInicio%4 * 15 : minutos
        horarioInicio = calcularHorario(filaInicio/4,filaInicio%4 * 15);
        horarioFin = new SimpleStringProperty(horarioInicio);
        horarioDinamico();
    }
    public Apilable(String tituloDeAsignable, LocalDateTime fechaInicio, LocalDateTime fechaFin){
        cargarFXML();
        agregarEstilos();
        limitarTamanio();
        permitirReescalar();
        cambioSuaveDePadding();
        this.tituloDeAsignable =  new SimpleStringProperty(tituloDeAsignable);
        titulo.textProperty().bind(this.tituloDeAsignable);

        this.filaInicio = fechaInicio.getHour()*4 + fechaInicio.getMinute()/15;
        this.offsetInicio = fechaInicio.getMinute()%15;
        this.filaFin = new SimpleIntegerProperty(fechaFin.getHour()*4+ fechaFin.getMinute()/15);
        AnchorPane.setTopAnchor(this,(double) filaInicio*15 + offsetInicio);

        //filaInicio/4 : hora --- filaInicio%4 * 15 : minutos
        horarioInicio = calcularHorario(filaInicio/4,filaInicio%4 * 15 + offsetInicio);
        horarioFin = new SimpleStringProperty(calcularHorario(filaFin.get()/4,filaFin.get()%4 * 15));
        horarioDinamico();

        double altura = fechaFin.getHour()*60+ fechaFin.getMinute() -
                (fechaInicio.getHour()*60 + fechaInicio.getMinute());
        setNuevaAltura(altura);
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
    public void setNuevaAltura(double altura) {
        filaFin.set(filaInicio  + ((int)(altura+14+offsetInicio)/15)-1);
        this.setPrefHeight(altura);
        this.horario.setVisible(altura >= 30);
        if(altura < 45){
            botonesPadding(new Insets(-2,0,0,0));
        }else{
            botonesPadding(new Insets(3,0,0,0));
        }
        reescribirHorario(altura);
    }
    public void reescribirHorario(double nuevaAltura) {
        nuevaAltura += (filaInicio*15)%60 + offsetInicio;
        var horaEntera = (int)nuevaAltura/60 + filaInicio/4;
        var minutos = (int)nuevaAltura%60;
        horarioFin.set(calcularHorario(horaEntera,minutos));
    }
    private String calcularHorario(int horaEntera, int minutos){
        String minutosTexto = minutos<10?"0"+minutos:String.valueOf(minutos);
        if(horaEntera <12){
            if(horaEntera == 0){
                return "12:"+minutosTexto+" AM";
            }else{
                return horaEntera+":"+minutosTexto+" AM";
            }
        }else{
            if(horaEntera == 12){
                return "12:"+minutosTexto+" PM";
            }else{
                return horaEntera-12+":"+minutosTexto+" PM";
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
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/celda.css").toUri().toString());
        horario.getStyleClass().add("texto-blanco");
        horario.setVisible(false);
        horario.setPadding(new Insets(-6,0,0,0));
        horaInLine.getStyleClass().add("texto-blanco");
        titulo.getStyleClass().addAll("texto-blanco","titulo","alinear-centro");
        titulo.setPadding(new Insets(-6, 0, 0, 0));
        this.getStyleClass().addAll("contenedor");
        botonesPadding(new Insets(-2,0,0,0));
    }
    private void botonesPadding(Insets insets){
        botonBorrar.setPadding(insets);
        botonEditar.setPadding(insets);
        botonFinal.setPadding(insets);
    }
    public void addBorrarEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonBorrar.addEventHandler(ActionEvent.ACTION,handler);
    }
    public void addEditarEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonEditar.addEventHandler(ActionEvent.ACTION,handler);
    }
    public void addFinalEvent(EventHandler<javafx.event.ActionEvent> handler){
        botonFinal.addEventHandler(ActionEvent.ACTION,handler);
    }
    public BooleanProperty getReescalandoProperty(){
        return reescalando;
    }
    private void permitirReescalar() {
        var valoresIniciales = new Object(){
            private double yInicial;
            private double alturaInicial;
        };
        addEventHandler(MouseEvent.MOUSE_PRESSED,new EventHandler<MouseEvent>() {

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
        });
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
            FXMLLoader loader =  new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Componente/apilableLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void editar(String tituloDeAsignable, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.tituloDeAsignable =  new SimpleStringProperty(tituloDeAsignable);
        titulo.textProperty().bind(this.tituloDeAsignable);

        this.filaInicio = fechaInicio.getHour()*4 + fechaInicio.getMinute()/15;
        this.offsetInicio = fechaInicio.getMinute()%15;
        this.filaFin = new SimpleIntegerProperty(fechaFin.getHour()*4+ fechaFin.getMinute()/15);
        AnchorPane.setTopAnchor(this,(double) filaInicio*15 + offsetInicio);

        //filaInicio/4 : hora --- filaInicio%4 * 15 : minutos
        horarioInicio = calcularHorario(filaInicio/4,filaInicio%4 * 15 + offsetInicio);
        horarioFin = new SimpleStringProperty(calcularHorario(filaFin.get()/4,filaFin.get()%4 * 15));
        horarioDinamico();

        double altura = fechaFin.getHour()*60+ fechaFin.getMinute() -
                (fechaInicio.getHour()*60 + fechaInicio.getMinute());
        setNuevaAltura(altura);
    }
}