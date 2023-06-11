package Algo3.Constantes;

import java.time.LocalDateTime;

public enum Dia{
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO;

    public Dia siguiente(Dia dia){
        Integer diaSiguiente = dia.ordinal()+1;
        if(diaSiguiente==7){
            return LUNES;
        }
        return Dia.values()[diaSiguiente];
    }
    public static int differencia(Dia diaInicial,Dia diaFinal ){
        int ordinalInicial = diaInicial.ordinal();
        int ordinalFinal = diaFinal.ordinal();
        if(ordinalInicial<ordinalFinal){
            return ordinalFinal - ordinalInicial;
        }else if(ordinalFinal == ordinalInicial){
            return 7;
        }else{
            return 7 - differencia(diaFinal,diaInicial);
        }
    }
}



