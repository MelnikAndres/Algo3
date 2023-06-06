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
        var appControlador = new AppControlador(stage.widthProperty());
        Scene mainScene = new Scene(appControlador.getRoot(),900,600);
        stage.setScene(mainScene);
        stage.show();
        stage.setMaximized(true);
    }
}
