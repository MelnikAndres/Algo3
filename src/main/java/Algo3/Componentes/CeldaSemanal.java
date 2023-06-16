package Algo3.Componentes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class CeldaSemanal extends VBox {
    @FXML
    private Label titulo;
    @FXML
    private Label horaInicial;
    @FXML
    private Label horaFinal;

    public CeldaSemanal(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        cargarFXML();
        this.titulo.setText(titulo);
        this.horaInicial.setText(formatearHora(fechaInicio));
        this.horaFinal.setText(formatearHora(fechaFinal));
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/celda.css").toUri().toString());
    }

    private String formatearHora(LocalDateTime aFormatear){
        var minutos = aFormatear.getMinute();
        var horaEntera = aFormatear.getHour();
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


    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Componente/celdaSemanalLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
