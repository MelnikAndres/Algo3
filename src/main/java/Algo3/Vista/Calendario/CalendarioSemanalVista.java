package Algo3.Vista.Calendario;

import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarioSemanalVista extends VBox {
    @FXML
    private ScrollPane scrollContainer;
    @FXML
    private HBox labelsContainer;
    @FXML
    private HBox contenedor;
    @FXML
    private VBox primerContainer;
    @FXML
    private VBox segundoContainer;
    @FXML
    private VBox tercerContainer;
    @FXML
    private VBox cuartoContainer;
    @FXML
    private VBox quintoContainer;
    @FXML
    private VBox sextoContainer;
    @FXML
    private VBox septimoContainer;
    @FXML
    private Label primeraLabel;
    @FXML
    private Label segundaLabel;
    @FXML
    private Label terceraLabel;
    @FXML
    private Label cuartaLabel;
    @FXML
    private Label quintaLabel;
    @FXML
    private Label sextaLabel;
    @FXML
    private Label septimaLabel;
    private List<String> dias = List.of("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado");
    private List<VBox> containers;
    private List<Label> labels;
    private DoubleProperty maxHeight;


    public CalendarioSemanalVista(){
        cargarFXML();
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Vista/Calendario/calendarioSemanalVista.css").toUri().toString());
        containers = List.of(primerContainer,segundoContainer,tercerContainer,cuartoContainer,quintoContainer,sextoContainer,septimoContainer);
        labels = List.of(primeraLabel,segundaLabel,terceraLabel,cuartaLabel,quintaLabel,sextaLabel,septimaLabel);
        bindearAnchos();
    }

    private void bindearAnchos(){
        for(int i=0; i<labels.size();i++){
            containers.get(i).minWidthProperty().bind(labels.get(i).widthProperty());
        }
    }

    public void montarVista(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty, ObjectProperty<LocalDate> fechaActiva) {
        labelsContainer.prefWidthProperty().bind(widthProperty.subtract(278));
        scrollContainer.maxHeightProperty().bind(heightProperty.subtract(labelsContainer.heightProperty()));
        scrollContainer.prefViewportHeightProperty().bind(heightProperty);
        scrollContainer.prefViewportWidthProperty().bind(widthProperty.subtract(278));
        contenedor.minHeightProperty().bind(heightProperty);
        contenedor.prefWidthProperty().bind(labelsContainer.widthProperty());
        fechaActiva.addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                var diasReordenados = dias.subList(t1.getDayOfWeek().getValue()-1,t1.getDayOfWeek().getValue()+6);
                for(int i = 0;i<diasReordenados.size();i++){
                    labels.get(i).setText(diasReordenados.get(i));
                }
            }
        });
    }
    public List<VBox> getContainers(){
        return containers;
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader =  new FXMLLoader(Path.of("src/main/resources/Layouts/Calendario/semanalLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
