package Algo3.Controlador;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class MensualControlador{
    @FXML
    StackPane contenedor;
    @FXML
    ScrollPane scrollMensual;
    @FXML
    GridPane grillaMensual;

    public MensualControlador(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty, ObjectProperty<LocalDate> dateValue) throws MalformedURLException {
        cargarFXML();
        crearGrilla(dateValue.get());

        widthProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrollMensual.setPrefWidth(t1.doubleValue()-250);

            }
        });
        heightProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrollMensual.setPrefHeight(t1.doubleValue());

            }
        });
        dateValue.addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                cambiarGrilla(t1);
            }
        });
    }
    public void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/mensualLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void crearGrilla(LocalDate fecha){
        agregarColumnas();
        agregarSeparadoresHorizontales();
        grillaMensual.setStyle("-fx-background-color: -fx-color-fondo");
        rellenarGrilla(fecha);
    }

    public void agregarColumnas(){
        for(int i = 0;i<7;i++){
            var column = crearColumna();
            var constraints = new ColumnConstraints();
            constraints.setMinWidth(200);
            constraints.setMaxWidth(200);
            constraints.setPrefWidth(200);
            constraints.setHalignment(HPos.RIGHT);
            grillaMensual.getColumnConstraints().add(constraints);
            grillaMensual.addColumn(i, column, crearSeparadores());
        }
    }

    private void cambiarGrilla(LocalDate fecha){
        grillaMensual.getChildren().clear();
        agregarSeparadoresHorizontales();
        agregarSeparadoresVerticales();
        grillaMensual.setStyle("-fx-background-color: -fx-color-fondo");
        rellenarGrilla(fecha);
    }
    private void rellenarGrilla(LocalDate fecha){
        int year = fecha.getYear();
        LocalDate primerDiaMes = LocalDate.of(year, fecha.getMonth(), 1);
        LocalDate ultimoDiaMes = LocalDate.of(year, fecha.getMonth().plus(1),1).minusDays(1);
        int ultimoDiaMesAnterior = primerDiaMes.minusDays(1).getDayOfMonth();
        int diaPrimerDia = primerDiaMes.getDayOfWeek().getValue();
        int contador = 0;
        int dias = 1;
        for(int i = 0; i < 7; i++){
            if(i<diaPrimerDia && diaPrimerDia != 7){
                grillaMensual.add(diaParaGrillaMensual(ultimoDiaMesAnterior-(diaPrimerDia-i-1),contador),i,0);
            }else {
                grillaMensual.add(diaParaGrillaMensual(dias, contador), i, 0);
                dias += 1;
            }
            contador += 1;
        }
        for (int j = 1; j < 6; j++) {
            for (int k = 0; k < 7; k++) {
                if(dias > LocalDate.of(year,fecha.getMonth().plus(1),1).minusDays(1).getDayOfMonth()){
                    break;
                }
                grillaMensual.add(diaParaGrillaMensual(dias, contador), k, j);
                dias += 1;

            }
        }
        /*int diasDelSiguienteMes = 1;
        for(int t = 0; t< 7-diaUltimoDia-1;t++){
            if(diaUltimoDia != 6){
                grillaMensual.add(diaParaGrillaMensual(diasDelSiguienteMes, contador),diaUltimoDia+t+1,4);
                diasDelSiguienteMes += 1;
            }
        }
        //for(int w = 0; w<7)*/


    }
    //Contador lleva la cuenta de los primeros 7 dias para escribir los dias sobre el calendario.
    private VBox diaParaGrillaMensual(int dia, int contador){
        List<String> lista = List.of("Dom","Lun", "Mar", "Mier", "Jue", "Vie", "Sab");
        var vbox = new VBox();
        if(contador < 7){
            var labelDia = new Label (lista.get(contador));
            labelDia.setAlignment(Pos.TOP_CENTER);
            labelDia.setStyle("-fx-text-fill: #778899");
            vbox.getChildren().add(labelDia);
        }
        var label = new Label(""+dia);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.BASELINE_CENTER);
        vbox.getChildren().add(label);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }
    public void agregarSeparadoresHorizontales(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                grillaMensual.add(crearSeparadoresHorizontales(),j,i);
            }
        }
    }
    public void agregarSeparadoresVerticales(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                grillaMensual.add(crearSeparadores(),j,i);
            }
        }
    }
    private HBox crearSeparadores(){
        var separador = new Separator(Orientation.VERTICAL);
        separador.setMinHeight(1500);
        separador.setMaxWidth(0.01);
        separador.setHalignment(HPos.CENTER);

        return new HBox(separador);
    }
    private VBox crearSeparadoresHorizontales(){
        var separador = new Separator(Orientation.HORIZONTAL);
        separador.setMinWidth(1500);
        separador.setValignment(VPos.CENTER);
        var vbox = new VBox(separador);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        return new VBox(separador);
    }
    private StackPane crearColumna(){
        var columnaDia = new StackPane();
        return columnaDia;
    }


    public Parent getRoot(){return scrollMensual;}
}
