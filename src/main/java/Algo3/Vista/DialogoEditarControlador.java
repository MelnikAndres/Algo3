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
        getDialogPane().setContent(dialogoEditarVista);
        dialogoEditarVista.getAlturaContenedor().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                //141 es la altura del footer con los botones
                setHeight(t1.doubleValue()+141);
            }
        });
        setTitle("Editar");
        var botonAceptar = new ButtonType("Aceptar", ButtonType.APPLY.getButtonData());
        var botonSalir = new ButtonType("Cancelar", ButtonType.CLOSE.getButtonData());
        getDialogPane().getButtonTypes().add(botonAceptar);
        getDialogPane().getButtonTypes().add(botonSalir);
        Optional<ButtonType> result = showAndWait();
        if(result.isPresent()){
            var resultadoTipo = result.get();
            if(resultadoTipo == botonSalir){
                System.out.println("closed");
            }else if(resultadoTipo == botonAceptar){
                System.out.println("aceptado");
            }
        }
    }
}
