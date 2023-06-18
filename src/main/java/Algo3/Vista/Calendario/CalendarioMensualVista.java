package Algo3.Vista.Calendario;


import Algo3.Componentes.Casilla;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class CalendarioMensualVista extends ScrollPane{
    @FXML
    GridPane grillaMensual;

    private ObjectProperty<LocalDate> dateValue;

    private final HashMap<LocalDate, Casilla> casillas = new HashMap<LocalDate, Casilla>();
    private List<String> nombresDias = List.of("Domingo","Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado");

    public CalendarioMensualVista(){
        cargarFXML();
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/Calendario/calendarioMensualVista.css").toUri().toString());
    }
    private void crearGrilla(LocalDate fecha){
        rellenarGrilla(fecha);
    }

    public void cambiarGrilla(LocalDate fecha){
        grillaMensual.getChildren().clear();
        rellenarGrilla(fecha);
    }

    private void rellenarGrilla(LocalDate fecha){
        int year = fecha.getYear();
        LocalDate primerDiaMes = LocalDate.of(year, fecha.getMonth(), 1);
        int diaPrimerDia = primerDiaMes.getDayOfWeek().getValue();
        int contador = 0;
        int diferencia = diaPrimerDia%7;
        LocalDate fechaActual = primerDiaMes.minusDays(diferencia);
        //llenar desde el domingo anterior mas cercano al primerDia
        for(int i = 0;i<diferencia;i++){
            grillaMensual.add(nuevaCasillaConNombre(fechaActual, nombresDias.get(contador),false),i,0);
            contador++;
            fechaActual = fechaActual.plusDays(1);
        }
        //llenar el resto de la primera fila que tiene los nombres de los dias
        for(;contador<7;contador++){
            grillaMensual.add(nuevaCasillaConNombre(fechaActual, nombresDias.get(contador),true),contador,0);
            fechaActual = fechaActual.plusDays(1);
        }
        int fila = 1;
        int columna = 0;
        //llenar hasta quedarse sin dias en el mes actual
        for (; fila<6; fila++) {
            for (; columna < 7 && fechaActual.getMonthValue() == fecha.getMonthValue(); columna++) {
                grillaMensual.add(nuevaCasilla(fechaActual,true), columna, fila);
                fechaActual = fechaActual.plusDays(1);
            }
            if(fechaActual.getMonthValue() != fecha.getMonthValue()){
                break;
            }
            columna = 0;
        }
        //completar el resto de la grilla con dias del mes siguiente
        for (; fila<6; fila++) {
            for (; columna < 7 ; columna++) {
                grillaMensual.add(nuevaCasilla(fechaActual,false), columna, fila);
                fechaActual = fechaActual.plusDays(1);
            }
            columna = 0;
        }
    }


    //Contador lleva la cuenta de los primeros 7 dias para escribir los dias sobre el calendario.
    private Casilla nuevaCasilla(LocalDate fechaDia, boolean correspondeAlMes){
        Casilla casilla = new Casilla(fechaDia);
        if(!correspondeAlMes){
            casilla.setNoCorrespondeAlMes();
        }
        casillas.put(fechaDia, casilla);
        return casilla;
    }
    private Casilla nuevaCasillaConNombre(LocalDate fechaDia,String nombreDia, boolean correspondeAlMes){
        Casilla casilla = new Casilla(fechaDia,nombreDia);
        if(!correspondeAlMes){
            casilla.setNoCorrespondeAlMes();
        }
        casillas.put(fechaDia, casilla);
        return casilla;
    }

    public void montarVista(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,ObjectProperty<LocalDate> dateValue) {
        this.dateValue = dateValue;
        crearGrilla(dateValue.get());
    }

    public HashMap<LocalDate, Casilla> getCasillas() {
        return casillas;
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

}
