package Algo3.Componentes.OpcionesExtra;

import javafx.scene.layout.VBox;

public class ExtraEvento extends VBox implements OpcionExtra {
    private ExtraFrecuencia extraFrecuencia = new ExtraFrecuencia();
    private ExtraRepeticion extraRepeticion = new ExtraRepeticion();

    public ExtraEvento(){
        setMinWidth(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);
        setSpacing(10);
        getChildren().addAll(extraFrecuencia,extraRepeticion);
    }

    @Override
    public String getValor() {
        return extraRepeticion.getValor() + "R:F"+extraFrecuencia.getValor();
    }

    @Override
    public void setValor(String valor) {
        System.out.println(valor);
        String[] valorSplit = valor.split("R:F");
        extraFrecuencia.setValor(valorSplit[1]);
        extraRepeticion.setValor(valorSplit[0]);
    }
}
