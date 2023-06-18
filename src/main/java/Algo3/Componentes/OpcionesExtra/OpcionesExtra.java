package Algo3.Componentes.OpcionesExtra;

import Algo3.Constantes.ParametroTipo;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class OpcionesExtra extends VBox implements OpcionExtra {

    private OpcionExtra extra;

    public OpcionesExtra(){
        setMinWidth(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);
        setSpacing(10);
    }
    public void addExtra(){
        if(extra != null){
            getChildren().remove(extra.getRoot());
        }
        extra = null;
    }
    public void addExtra(OpcionExtra nuevoExtra){
        if(extra != null){
            getChildren().remove(extra.getRoot());
        }
        getChildren().add(nuevoExtra.getRoot());
        extra = nuevoExtra;
    }

    @Override
    public String getValor() {
        if(extra!= null){
            return extra.getValor();
        }
        return "null";
    }

    @Override
    public void setValor(String valor) {
        if(extra!= null){
            extra.setValor(valor);
        }
    }
    @Override
    public Node getRoot() {
        return this;
    }
}
