package Algo3.Controlador;

import Algo3.Modelo.Asignable;
import javafx.scene.Node;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public abstract class CalendarioControlador {

    public abstract void cargarAsignables(Map<Asignable, List<LocalDateTime>> repeticiones);

    public abstract Node getVista();

}
