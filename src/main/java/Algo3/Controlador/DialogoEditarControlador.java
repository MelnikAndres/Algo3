package Algo3.Controlador;

import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Frecuencia.Frecuencia;
import Algo3.Modelo.Asignable;
import Algo3.Modelo.Evento;
import Algo3.Modelo.Tarea;
import Algo3.Repeticion.Repeticion;
import Algo3.Utilidad.FechaParser;
import Algo3.Vista.DialogoEditarVista;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class DialogoEditarControlador extends Dialog<ButtonType> {
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
        HashMap<ParametroTipo, String> valores = dialogoEditarVista.obtenerValores();
        List<String> extras = dialogoEditarVista.obtenerExtra();
        if(valores.get(ParametroTipo.TIPO).equals("Evento")){
            var repeticionFrecuencia = extras.get(0).split("R:F");
            Frecuencia frecuencia = Frecuencia.desdeString(repeticionFrecuencia[1]);
            Repeticion repeticion;
            if(RepeticionTipo.valueOf(repeticionFrecuencia[0].split(";")[0]).equals(RepeticionTipo.CANTIDAD_LIMITE)){
                repeticion = Repeticion.desdeString(extras.get(0)+ "R:F" + valores.get(ParametroTipo.FECHAINICIAL));
            }else{
                repeticion = Repeticion.desdeString(repeticionFrecuencia[0]);
            }
            return new Evento(valores.get(ParametroTipo.TITULO),valores.get(ParametroTipo.DESCRIPCION),
                    FechaParser.fromString(valores.get(ParametroTipo.FECHAINICIAL)),
                    FechaParser.fromString(valores.get(ParametroTipo.FECHAFINAL)),
                    frecuencia, repeticion);
        }else{
            return new Tarea(valores.get(ParametroTipo.TITULO),valores.get(ParametroTipo.DESCRIPCION),
                    FechaParser.fromString(valores.get(ParametroTipo.FECHAINICIAL)),
                    FechaParser.fromString(valores.get(ParametroTipo.FECHAFINAL)));
        }
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
