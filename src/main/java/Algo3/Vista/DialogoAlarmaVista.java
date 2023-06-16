package Algo3.Vista;

import Algo3.Componentes.OpcionesExtra.ExtraFechaHora;
import Algo3.Disparador.Disparador;
import Algo3.Utilidad.FechaParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ToggleButton;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class DialogoAlarmaVista extends DialogPane {
    @FXML
    private ExtraFechaHora fecha;
    @FXML
    private ToggleButton esTiempoAntes;


    public DialogoAlarmaVista(){
        cargarFXML();
    }

    public LocalDateTime obtenerFecha() {
        return FechaParser.fromString(fecha.getValor());
    }

    public boolean obtenerTiempoAntes(){
        return esTiempoAntes.isSelected();
    }
    private void cargarFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Path.of("src/main/resources/Layouts/Dialogo/dialogoAlarmaLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
