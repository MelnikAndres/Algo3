package Algo3.Vista;

import Algo3.Modelo.Alarma;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DialogoEditarControlador extends Dialog<ButtonType> {
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private List<Alarma> alarmas = new ArrayList<>();
    private DialogoEditarVista dialogoEditarVista = new DialogoEditarVista();

    public DialogoEditarControlador(Stage stage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);
        setDialogPane(dialogoEditarVista);
        setTitle("Editar");
        var botonAceptar = new ButtonType("Aceptar", ButtonType.APPLY.getButtonData());
        var botonSalir = new ButtonType("Cancelar", ButtonType.CLOSE.getButtonData());
        getDialogPane().getButtonTypes().add(botonAceptar);
        getDialogPane().getButtonTypes().add(botonSalir);
        Optional<ButtonType> result = showAndWait();
        if(result.isPresent()){
            var resultadoTipo = result.get();
            if(resultadoTipo == ButtonType.CLOSE){
                System.out.println("closed");
            }else if(resultadoTipo == ButtonType.APPLY){
                System.out.println("aceptado");
            }
        }
    }
}
