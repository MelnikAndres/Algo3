package Algo3;

import Algo3.Controlador.MainControlador;
import Algo3.Vista.MainVista;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Calendario");
        var mainControlador = new MainControlador(stage.widthProperty());
        Scene mainScene = new Scene(mainControlador.getVista(),900,600);
        stage.setScene(mainScene);
        stage.show();
        stage.setMaximized(true);
    }
}
