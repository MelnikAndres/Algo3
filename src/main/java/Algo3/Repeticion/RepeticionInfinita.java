package Algo3.Repeticion;

import Algo3.Constantes.RepeticionTipo;
import Algo3.Parametros;

import java.time.LocalDateTime;
import java.util.HashMap;

public class RepeticionInfinita implements Repeticion{

    @Override
    public LocalDateTime obtenerUltimoDiaDeRepeticion(){
        return LocalDateTime.MAX;
    }

    @Override
    public RepeticionTipo getTipo() {
        return RepeticionTipo.INFINITO;
    }

}
