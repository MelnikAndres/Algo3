package Algo3.Componentes.OpcionesExtra;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;

public class ExtraDiaDeSemana extends VBox {

    ExtraDiaDeSemana(){
        cargarFXML();
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/OpcionesExtra/extraDiaDeSemana.css").toUri().toString());
    }



    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/Dialogo/extrasDiaDeSemanaLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }
}
