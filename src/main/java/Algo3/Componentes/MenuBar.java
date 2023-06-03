package Algo3.Componentes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.nio.file.Path;

public class MenuBar extends FlowPane {
    private Button cerrar;
    private Button max;
    private Button min;
    private double xOffset;
    private double yOffset;

    public MenuBar(){
        agregarBotones();
        agregarEstilos();
        agregarEventos();
    }

    private void agregarBotones(){
        min =  new Button();
        var minIcono = new ImageView(new Image(Path.of("src/main/resources/Imagenes/minimizar.png").toUri().toString()));
        minIcono.setFitHeight(15);
        minIcono.setFitWidth(15);
        max = new Button();
        var maxIcono = new ImageView(new Image(Path.of("src/main/resources/Imagenes/maximizar.png").toUri().toString()));
        maxIcono.setFitHeight(15);
        maxIcono.setFitWidth(15);
        cerrar = new Button();
        var cerrarIcono = new ImageView(new Image(Path.of("src/main/resources/Imagenes/cerrar.png").toUri().toString()));
        cerrarIcono.setFitHeight(15);
        cerrarIcono.setFitWidth(15);
        min.setGraphic(minIcono);
        max.setGraphic(maxIcono);
        cerrar.setGraphic(cerrarIcono);
        this.getChildren().add(min);
        this.getChildren().add(max);
        this.getChildren().add(cerrar);
    }
    private void agregarEstilos(){
        this.setAlignment(Pos.TOP_RIGHT);
        this.setStyle("-fx-background-color: lightgray");
        getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/MenuBar.css").toUri().toString());
        cerrar.getStyleClass().add("boton-menu");
        max.getStyleClass().add("boton-menu");
        min.getStyleClass().add("boton-menu");
        cerrar.getStyleClass().add("boton-cerrar");

        //efecto para hacer que la cruz sea blanca cuando el mouse se pone encima
        ColorAdjust cambiarColor = new ColorAdjust();
        cerrar.setOnMouseEntered(event -> cambiarColor.setBrightness(1));
        cerrar.setOnMouseExited(event -> cambiarColor.setBrightness(-1));
        cerrar.getGraphic().setEffect(cambiarColor);
    }
    private void agregarEventos(){
        cerrar.setOnAction(actionEvent -> {
            Stage stage = (Stage) cerrar.getScene().getWindow();
            stage.close();
        });
        max.setOnAction(actionEvent -> {
            Stage stage = (Stage) cerrar.getScene().getWindow();
            stage.setMaximized(!stage.isMaximized());
        });
        min.setOnAction(actionEvent -> {
            Stage stage = (Stage) cerrar.getScene().getWindow();
            stage.setIconified(true);
        });

        //mover la ventana arrastrando en la barra
        this.setOnMousePressed(event -> {
            Stage stage = (Stage) cerrar.getScene().getWindow();
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        this.setOnMouseDragged(event -> {
            Stage stage = (Stage) cerrar.getScene().getWindow();

            if(stage.isMaximized()){
                //si la ventana estaba maximizada se achica
                var previousWidth = stage.getWidth();
                stage.setMaximized(false);
                var postWidth = stage.getWidth();
                stage.setX(event.getScreenX()- event.getSceneX()*postWidth/previousWidth);
                xOffset = stage.getX() - event.getScreenX();
                stage.setY(event.getScreenY()-10);
                return;
            }
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
    }
}
