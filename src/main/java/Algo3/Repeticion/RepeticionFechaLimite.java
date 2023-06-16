package Algo3.Repeticion;

import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class RepeticionFechaLimite implements Repeticion {

    private LocalDateTime fechaLimite;
    public RepeticionFechaLimite(@JsonProperty("fechaLimite")LocalDateTime fechaLimite){
        if (fechaLimite == null)
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        this.fechaLimite = fechaLimite;
    }

    @Override
    public LocalDateTime obtenerUltimoDiaDeRepeticion(){
        return fechaLimite;
    }

    @Override
    public RepeticionTipo getTipo() {
        return RepeticionTipo.FECHA_LIMITE;
    }

    public String getParams() {
        return  fechaLimite.toString();
    }
}
