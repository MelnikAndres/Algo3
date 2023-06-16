package Algo3.Componentes;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.time.LocalDate;


public class Casilla extends VBox {
    private VBox base = new VBox();
    private final LocalDate fecha;

    public Casilla(LocalDate fecha){
        this.fecha = fecha;
    }

    public VBox getBase() {
        return base;
    }

    public LocalDate getFecha() {
        return fecha;
    }

}
