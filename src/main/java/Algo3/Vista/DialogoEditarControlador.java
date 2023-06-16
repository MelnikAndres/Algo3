package Algo3.Vista;

import Algo3.Constantes.ParametroTipo;
import Algo3.Modelo.Asignable;
import Algo3.Utilidad.Editor;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class DialogoEditarControlador extends Dialog<ButtonType> {
    private DialogoEditarVista dialogoEditarVista = new DialogoEditarVista();

    private Editor editor = new Editor();

    public DialogoEditarControlador(Stage stage){
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);
        setDialogPane(dialogoEditarVista);
        setTitle("Editar");
        var botonAceptar = new ButtonType("Aceptar", ButtonType.APPLY.getButtonData());
        var botonSalir = new ButtonType("Cancelar", ButtonType.CLOSE.getButtonData());
        getDialogPane().getButtonTypes().add(botonAceptar);
        getDialogPane().getButtonTypes().add(botonSalir);
    }

    public boolean abrirYeditar(Asignable asignable){
        Optional<ButtonType> result = showAndWait();
        if(result.isPresent()){
            var resultadoTipo = result.get();
            if(resultadoTipo.getButtonData() == ButtonBar.ButtonData.APPLY){
                editor.cargarValores(dialogoEditarVista.obtenerValores());
                editor.cargarExtras(dialogoEditarVista.obtenerExtra());
                asignable.aceptarEdicion(editor);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public void cargarValores(Map<ParametroTipo, String> parametros){
        HashSet<ParametroTipo> keys = new HashSet<>(parametros.keySet());
        dialogoEditarVista.cargarValor(ParametroTipo.TIPO, parametros.get(ParametroTipo.TIPO));
        keys.remove(ParametroTipo.TIPO);
        for(ParametroTipo tipo: keys){
            dialogoEditarVista.cargarValor(tipo, parametros.get(tipo));
        }
    }
}
