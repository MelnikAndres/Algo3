package Algo3.Componentes.OpcionesExtra;

import Algo3.Constantes.ParametroTipo;

import javafx.scene.layout.VBox;

public class OpcionesExtra extends VBox implements OpcionExtra {

    private OpcionExtra extra;

    public OpcionesExtra(){
        setMinWidth(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);
        setSpacing(10);
    }

    public void addExtra(){
        getChildren().clear();
        extra = null;
        getScene().getWindow().sizeToScene();
    }
    public void addExtra(ParametroTipo opcionTipo){
        getChildren().clear();
        switch (opcionTipo){
            case FECHA -> {
                ExtraFechaHora nuevoExtra= new ExtraFechaHora();
                getChildren().add(nuevoExtra);
                extra = nuevoExtra;
            }
            case INTERVALO -> {
                ExtraEntero nuevoExtra= new ExtraEntero("Intervalo");
                getChildren().add(nuevoExtra);
                extra = nuevoExtra;
            }
            case CANTIDAD -> {
                ExtraEntero nuevoExtra= new ExtraEntero("Repeticiones");
                getChildren().add(nuevoExtra);
                extra = nuevoExtra;
            }
            case DIASDESEMANA -> {
                ExtraDiaDeSemana nuevoExtra= new ExtraDiaDeSemana();
                getChildren().add(nuevoExtra);
                extra = nuevoExtra;
            }
            case REPETICION_FRECUENCIA -> {
                ExtraEvento nuevoExtra= new ExtraEvento();
                getChildren().add(nuevoExtra);
                extra = nuevoExtra;
            }
        }
        getScene().getWindow().sizeToScene();
    }

    @Override
    public String getValor() {
        if(extra!= null){
            return extra.getValor();
        }
        return "";
    }

    @Override
    public void setValor(String valor) {
        extra.setValor(valor);
    }
}
