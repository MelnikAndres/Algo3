package Algo3.Componentes.OpcionesExtra;

import Algo3.Frecuencia.Frecuencia;
import Algo3.Repeticion.Repeticion;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class ExtraEvento extends VBox {
    private ExtraFrecuencia extraFrecuencia = new ExtraFrecuencia();
    private ExtraRepeticion extraRepeticion = new ExtraRepeticion();

    public ExtraEvento(){
        setMinWidth(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);
        setSpacing(10);
        getChildren().addAll(extraFrecuencia,extraRepeticion);
    }
    public void setFrecuencia(String frecuencia){
        extraFrecuencia.setValor(frecuencia);
    }
    public void setRepeticion(String repeticion){
        extraRepeticion.setValor(repeticion);
    }
    public Frecuencia getFrecuencia(){
        return Frecuencia.desdeString(extraFrecuencia.getValor());
    }
    public String getRepeticionString(){
        return extraRepeticion.getValor();
    }
}
