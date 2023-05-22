package Algo3.Disparador;



import Algo3.Constantes.DisparadorTipo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
public interface Disparador extends Serializable {
    void disparar();
    DisparadorTipo getTipo();

}
