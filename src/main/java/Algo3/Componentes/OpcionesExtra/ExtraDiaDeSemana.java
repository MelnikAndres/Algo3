package Algo3.Componentes.OpcionesExtra;

import Algo3.Constantes.Dia;
import Algo3.Constantes.ParametroTipo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExtraDiaDeSemana extends VBox implements OpcionExtra {

    @FXML
    private ToggleButton toggleLunes;
    @FXML
    private ToggleButton toggleMartes;
    @FXML
    private ToggleButton toggleMiercoles;
    @FXML
    private ToggleButton toggleJueves;
    @FXML
    private ToggleButton toggleViernes;
    @FXML
    private ToggleButton toggleSabado;
    @FXML
    private ToggleButton toggleDomingo;

    private List<ToggleButton> opcionesDias;

    ExtraDiaDeSemana(){
        cargarFXML();
        this.getStylesheets().add(Path.of("src/main/java/Algo3/Componentes/OpcionesExtra/extraDiaDeSemana.css").toUri().toString());
        opcionesDias = List.of(toggleLunes,toggleMartes,toggleMiercoles,toggleJueves,toggleViernes,toggleSabado,toggleDomingo);
    }

    private void cargarFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/main/resources/Layouts/Dialogo/extrasDiaDeSemanaLayout.fxml").toUri().toURL());
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }catch(
                IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getValor() {
        List<Dia> diasDeLaSemana= new ArrayList<>();
        List<Dia> todosLosDias = Arrays.asList(Dia.values());
        for(int i =0;i<opcionesDias.size();i++){
            if(opcionesDias.get(i).isSelected()){
                diasDeLaSemana.add(todosLosDias.get(i));
            }
        }
        return diasDeLaSemana.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
    public void setValor(String valor){
        List<String> diasSplit= List.of(valor.split(","));
        for(String diaStr: diasSplit){
            Dia dia = Dia.valueOf(diaStr);
            opcionesDias.get(dia.ordinal()).setSelected(true);
        }
    }

    @Override
    public Node getRoot() {
        return this;
    }
}
