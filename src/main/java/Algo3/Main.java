package Algo3;

import Algo3.Controlador.AppControlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Calendario");
        Scene preScene = new Scene(new Pane());
        stage.setScene(preScene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        var ancho = stage.getWidth();
        var alto =  stage.getHeight();
        var appControlador = new AppControlador(ancho,alto);
        Scene mainScene = new Scene(appControlador.getRoot(),ancho,alto);
        mainScene.setFill(Color.TRANSPARENT);
        stage.setScene(mainScene);
    }
}
