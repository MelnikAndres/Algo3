package Algo3.Disparador;



import Algo3.Constantes.DisparadorTipo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public interface Disparador extends Serializable {
    void disparar();
    @JsonIgnore
    DisparadorTipo getTipoDisparador();

}
