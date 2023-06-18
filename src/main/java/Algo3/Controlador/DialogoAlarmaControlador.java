package Algo3.Controlador;

import Algo3.Disparador.Disparador;
import Algo3.Disparador.Notificacion;
import Algo3.Modelo.Alarma;
import Algo3.Modelo.Asignable;
import Algo3.Vista.DialogoAlarmaVista;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DialogoAlarmaControlador extends Dialog<ButtonType> {
    DialogoAlarmaVista dialogoAlarmaVista;


    public DialogoAlarmaControlador(Stage stage, List<Alarma> alarmas){
        dialogoAlarmaVista = new DialogoAlarmaVista(alarmas);
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);
        setDialogPane(dialogoAlarmaVista);
        setTitle("Editar");
        var botonAceptar = new ButtonType("Aceptar", ButtonType.APPLY.getButtonData());
        var botonSalir = new ButtonType("Cancelar", ButtonType.CLOSE.getButtonData());
        getDialogPane().getButtonTypes().add(botonAceptar);
        getDialogPane().getButtonTypes().add(botonSalir);
    }

    public Alarma abrirYcrear(LocalDateTime fechaDeAsignable){
        Optional<ButtonType> result = showAndWait();
        if(result.isPresent()){
            var resultadoTipo = result.get();
            if(resultadoTipo.getButtonData() == ButtonBar.ButtonData.APPLY){
                Disparador disparador = new Notificacion();
                if(dialogoAlarmaVista.esAbsoluta()){
                    return new Alarma(dialogoAlarmaVista.getFecha(),Duration.ZERO,disparador);
                }else{
                    Duration duration = dialogoAlarmaVista.getDuration();
                    return new Alarma(fechaDeAsignable,duration, disparador);
                }
            }else{
                return null;
            }
        }
        return null;
    }
}
