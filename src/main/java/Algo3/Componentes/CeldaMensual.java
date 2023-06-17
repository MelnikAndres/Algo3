package Algo3.Componentes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CeldaMensual extends HBox {
    
    private Button botonBorrar;
    private Button botonEditar;
    private Button botonAlarma;
    private Label tituloLabel;
    
    
    public CeldaMensual(){
        botonBorrar=crearBoton();
        botonBorrar.setStyle("-fx-background-color: red; -fx-border-color: black");
        botonEditar=crearBoton();
        botonEditar.setStyle("-fx-background-color: blue; -fx-border-color: black");
        botonAlarma=crearBoton();
        botonAlarma.setStyle("-fx-background-color: orange; -fx-border-color: black");
        tituloLabel = new Label();
        tituloLabel.setPrefWidth(150);
        tituloLabel.setMaxWidth(150);
        tituloLabel.setMinWidth(150);
        setAlignment(Pos.TOP_RIGHT);
        setMaxWidth(200);
        setMinWidth(200);
        setPrefWidth(200);
        setMaxHeight(30);
        setMinHeight(30);
        setPrefHeight(30);
        getChildren().addAll(tituloLabel, botonBorrar,botonEditar,botonAlarma);
        setStyle("-fx-background-color: yellow");
    }

    public Button getBotonAlarma() {
        return botonAlarma;
    }

    public Button getBotonBorrar() {
        return botonBorrar;
    }

    public Button getBotonEditar() {
        return botonEditar;
    }

    public Label getTituloLabel() {
        return tituloLabel;
    }

    private Button crearBoton(){
        Button boton = new Button();
        boton.setPrefHeight(10);
        boton.setMinHeight(10);
        boton.setMaxHeight(10);
        boton.setPrefWidth(10);
        boton.setMinWidth(10);
        boton.setMaxWidth(10);
        boton.setAlignment(Pos.TOP_RIGHT);
        return boton;
    }
}
