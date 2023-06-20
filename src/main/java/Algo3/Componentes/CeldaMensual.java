package Algo3.Componentes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CeldaMensual extends CeldaSemanal{
    public CeldaMensual(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, LocalDate fechaActual) {
        super(titulo, fechaInicio, fechaFinal,fechaActual);
        adaptarMensual();
    }
    private void adaptarMensual() {
        getChildren().remove(horaInicial);
        getChildren().remove(horaFinal);
        Node contenedorBotones = lookup("#botonesContenedor");
        HBox contenedorTitulo = (HBox)lookup("#tituloContenedor");
        contenedorTitulo.getChildren().add(contenedorBotones);
        titulo.setStyle("-fx-font-size: 10");
        botonAlarma.setStyle("-fx-padding: 0");
        botonBorrar.setStyle("-fx-padding: 0");
        botonEditar.setStyle("-fx-padding: 0");
        titulo.setWrapText(false);
        HBox.setMargin(completada,new Insets(0,2,0,0));
    }

}
