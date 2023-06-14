package Algo3.Componentes.OpcionesExtra;

import Algo3.Constantes.ParametroTipo;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Algo3.Constantes.ParametroTipo.*;

public class OpcionesExtra extends VBox {

    public OpcionesExtra(){
        setMinWidth(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);
    }
    public void setExtra(ParametroTipo... opciones){
        getChildren().clear();
        List<Node> extras= new ArrayList<>();
        for(ParametroTipo opcion: opciones){
            if(opcion==FECHA){
                extras.add(new DatePicker(LocalDate.now()));
            }else if(opcion==ENTERO){
                extras.add(new ExtraIntervalo());
            }else if(opcion==DIASDESEMANA){
                extras.add(new Label("Dias de semana"));
            }else if(opcion == FRECUENCIA){
                extras.add(new ExtraFrecuencia());
            }else if(opcion == REPETICION){
                extras.add(new ExtraRepeticion());
            }
        }
        getChildren().addAll(extras);
    }
}
