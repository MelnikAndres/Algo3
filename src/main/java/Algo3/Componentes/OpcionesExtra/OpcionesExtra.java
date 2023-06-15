package Algo3.Componentes.OpcionesExtra;

import Algo3.Constantes.ParametroTipo;

import javafx.scene.Node;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class OpcionesExtra extends VBox {

    public OpcionesExtra(){
        setMinWidth(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);
        setSpacing(10);
    }
    public void setExtra(ParametroTipo... opciones){

        getChildren().clear();
        List<Node> extras= new ArrayList<>();
        for(ParametroTipo opcion: opciones){
            switch (opcion){
                case FECHA -> extras.add(new ExtraFechaHora());
                case INTERVALO -> extras.add(new ExtraEntero("Intervalo"));
                case CANTIDAD -> extras.add(new ExtraEntero("Repeticiones"));
                case DIASDESEMANA -> extras.add(new ExtraDiaDeSemana());
                case FRECUENCIA -> extras.add(new ExtraFrecuencia());
                case REPETICION -> extras.add(new ExtraRepeticion());
            }
        }
        getChildren().addAll(extras);
        getScene().getWindow().sizeToScene();
    }
}
