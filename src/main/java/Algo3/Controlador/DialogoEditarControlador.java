package Algo3.Controlador;
import Algo3.Componentes.OpcionesExtra.ExtraEvento;
import Algo3.Modelo.Asignable;
import Algo3.Utilidad.Editor;
import Algo3.Vista.DialogoEditarVista;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.*;

public class DialogoEditarControlador extends Dialog<ButtonType> {
    private DialogoEditarVista dialogoEditarVista = new DialogoEditarVista();
    private Editor editor;
    public DialogoEditarControlador(Stage stage){
        editor = new Editor(dialogoEditarVista);
        initModality(Modality.WINDOW_MODAL);
        initOwner(stage);
        setDialogPane(dialogoEditarVista);
        setTitle("Editar");
        var botonAceptar = new ButtonType("Aceptar", ButtonType.APPLY.getButtonData());
        var botonSalir = new ButtonType("Cancelar", ButtonType.CLOSE.getButtonData());
        getDialogPane().getButtonTypes().add(botonAceptar);
        getDialogPane().getButtonTypes().add(botonSalir);
        dialogoEditarVista.addTipoListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                dialogoEditarVista.removerExtra();
                if(t1.equals("Evento")){
                    ExtraEvento extra = new ExtraEvento();
                    dialogoEditarVista.agregarExtra(extra);
                    editor.setObtenerEvento(extra);
                }else{
                    editor.setObtenerTarea();
                }
                dialogoEditarVista.getScene().getWindow().sizeToScene();
            }
        });
    }
    public Asignable abrirYeditar(){
        Optional<ButtonType> result = showAndWait();
        if(result.isPresent()){
            var resultadoTipo = result.get();
            if(resultadoTipo.getButtonData() == ButtonBar.ButtonData.APPLY){
                return obtenerResultado();
            }else{
                return null;
            }
        }
        return null;
    }
    private Asignable obtenerResultado(){
        return editor.obtenerAsignable();
    }
    public void cargarValores(Asignable asignable){
        asignable.recibirEditor(editor);

    }
}
