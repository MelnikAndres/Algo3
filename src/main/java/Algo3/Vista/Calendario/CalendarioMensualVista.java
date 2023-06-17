package Algo3.Vista.Calendario;


import Algo3.Componentes.Casilla;
import Algo3.Componentes.CeldaMensual;
import Algo3.Componentes.CeldaSemanal;
import Algo3.Modelo.Asignable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

import javafx.scene.text.TextAlignment;

public class CalendarioMensualVista extends ScrollPane{
    @FXML
    StackPane contenedor;
    @FXML
    GridPane grillaMensual;

    private ObjectProperty<LocalDate> dateValue;

    private final HashMap<LocalDate, Casilla> casillas = new HashMap<LocalDate, Casilla>();

    public CalendarioMensualVista(){
        cargarFXML();
    }
    private void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Calendario/mensualLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    private void crearGrilla(LocalDate fecha){
        agregarColumnas();
        grillaMensual.setStyle("-fx-background-color: #142131");
        grillaMensual.setGridLinesVisible(true);
        rellenarGrilla(fecha);
        //agregarSeparadores();

    }


    private void agregarColumnas(){
        for(int i = 0;i<7;i++){
            var column = new StackPane();
            var constraints = new ColumnConstraints();

            constraints.setMinWidth(200);
            constraints.setMaxWidth(200);
            constraints.setPrefWidth(200);
            constraints.setHalignment(HPos.RIGHT);
            grillaMensual.getColumnConstraints().add(constraints);
            grillaMensual.addColumn(i, column);
        }
    }

    public void cambiarGrilla(LocalDate fecha){
        grillaMensual.getChildren().clear();
        grillaMensual.setStyle("-fx-background-color: #142131");

        rellenarGrilla(fecha);
        //agregarSeparadores();
    }
    private void rellenarGrilla(LocalDate fecha){
        int year = fecha.getYear();
        LocalDate primerDiaMes = LocalDate.of(year, fecha.getMonth(), 1);
        int ultimoDiaMesAnterior = primerDiaMes.minusDays(1).getDayOfMonth();
        int diaPrimerDia = primerDiaMes.getDayOfWeek().getValue();
        int contador = 0;
        int dias = 1;
        for(int i = 0; i < 7; i++){
            if(i<diaPrimerDia && diaPrimerDia != 7){
                int o = ultimoDiaMesAnterior-(diaPrimerDia-i-1);
                grillaMensual.add(diaParaGrillaMensual(o,fecha.minusMonths(1).getMonthValue(),fecha.minusMonths(1).getYear(), contador,false).getBase(),i,0);
            }else {
                grillaMensual.add(diaParaGrillaMensual(dias,fecha.getMonthValue(), fecha.getYear(), contador,true).getBase(), i, 0);
                dias += 1;
            }
            contador += 1;
        }
        int fila = 0;
        int columna = 0;
        for (int j = 1; j < 6; j++) {
            if(dias > LocalDate.of(year,fecha.getMonth().plus(1),1).minusDays(1).getDayOfMonth()){
                break;
            }
            for (int k = 0; k < 7; k++) {
                if(dias > LocalDate.of(year,fecha.getMonth().plus(1),1).minusDays(1).getDayOfMonth()){
                    break;
                }
                fila = j;
                columna = k;
                grillaMensual.add(diaParaGrillaMensual(dias, fecha.getMonthValue(), fecha.getYear(), contador,true).getBase(), k, j);
                dias += 1;

            }
        }
        dias = 1;
        while(fila<6 && columna<7){
            if(columna == 6){
                fila++;
                columna = 0;
            }else{
                columna++;
            }
            if(fila == 6){
                break;
            }
            grillaMensual.add(diaParaGrillaMensual(dias, fecha.plusMonths(1).getMonthValue(), fecha.plusMonths(1).getYear(), contador,false).getBase(),columna, fila);
            dias++;
        }
    }


    //Contador lleva la cuenta de los primeros 7 dias para escribir los dias sobre el calendario.
    private Casilla diaParaGrillaMensual(int dia, int mes,int anio, int contador, boolean correspondeAlMes){
        List<String> lista = List.of("Dom","Lun", "Mar", "Mier", "Jue", "Vie", "Sab");
        var casilla = new Casilla(LocalDate.of(anio,mes, dia));
        if(contador < 7){
            var labelDia = new Label (lista.get(contador));
            labelDia.setAlignment(Pos.TOP_CENTER);
            labelDia.setStyle("-fx-text-fill: white");
            casilla.getBase().getChildren().add(labelDia);
        }
        var label = new Label(""+dia);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-text-fill: white");
        casilla.getBase().getChildren().add(label);
        casilla.getBase().setAlignment(Pos.TOP_CENTER);
        casilla.getBase().setStyle("-fx-border-color: white");
        if(!correspondeAlMes){
            casilla.getBase().setStyle("-fx-border-color: white; -fx-background-color: derive(#142131, 70%)");
            label.setStyle("-fx-opacity: 0.5");
        }
        if(casillas.containsKey(LocalDate.of(anio,mes,dia))){
            casillas.replace(LocalDate.of(anio,mes,dia), casilla);
        }else
            casillas.put(LocalDate.of(anio,mes,dia), casilla);
        return casilla;
    }
    private void agregarSeparadores(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                grillaMensual.add(crearSeparadoresHorizontales(),j,i);
                grillaMensual.add(crearSeparadores(),j,i);
            }
        }
    }

    public CeldaMensual crearAsignable(){
        return new CeldaMensual();
    }

    private HBox crearSeparadores(){
        var separador = new Separator(Orientation.VERTICAL);
        separador.setMaxWidth(0.00001);
        separador.setHalignment(HPos.CENTER);

        return new HBox(separador);
    }
    private VBox crearSeparadoresHorizontales(){
        var separador = new Separator(Orientation.HORIZONTAL);
        separador.setValignment(VPos.CENTER);
        return new VBox(separador);
    }

    public void montarVista(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,ObjectProperty<LocalDate> dateValue) {
        this.dateValue = dateValue;
        crearGrilla(dateValue.get());
    }

    public HashMap<LocalDate, Casilla> getCasillas() {
        return casillas;
    }
}
