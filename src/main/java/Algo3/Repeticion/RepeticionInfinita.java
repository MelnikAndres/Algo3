package Algo3.Repeticion;

import Algo3.Constantes.RepeticionTipo;

import java.time.LocalDateTime;

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
