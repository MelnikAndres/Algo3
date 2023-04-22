package Algo3.Constantes;

import java.time.LocalDateTime;

public enum Dia{
    LUNES(1),
    MARTES(2),
    MIERCOLES(4),
    JUEVES(8),
    VIERNES(16),
    SABADO(32),
    DOMINGO(64);
    private final int valor;
    public int getValor(){return valor;}
    Dia(int valor){
        this.valor = valor;
    }
}



