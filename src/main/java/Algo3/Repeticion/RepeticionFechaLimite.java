package Algo3.Repeticion;

import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Constantes.RepeticionTipo;
import Algo3.Parametros;

import java.time.LocalDateTime;

public class RepeticionFechaLimite implements Repeticion {

    private LocalDateTime fechaLimite;
    public RepeticionFechaLimite(LocalDateTime fechaLimite){
        if (fechaLimite == null)
            throw new RuntimeException(ErrorTipo.FECHA_ULTIMA_REPETICION.toString());
        this.fechaLimite = fechaLimite;
    }

    @Override
    public LocalDateTime obtenerUltimoDiaDeRepeticion(){
        return fechaLimite;
    }

    public LocalDateTime getFecha(){return fechaLimite;}

    @Override
    public RepeticionTipo getTipo() {
        return RepeticionTipo.FECHA_LIMITE;
    }

    public Parametros getParams() {
        Parametros parametros = new Parametros();
        parametros.agregarParametro("Fecha Limite", ParametroTipo.FECHA, fechaLimite.toString());
        return  parametros;
    }
}
