package Algo3;

import java.time.LocalDateTime;

enum AlarmaTipo {
    NOTIFICACION,SONIDO,MAIL

}
enum FrecuenciaTipo{
    NINGUNA,DIARIA,SEMANAL,MENSUAL,ANUAL
}

enum RepeticionTipo {
    CANTIDAD_LIMITE("cantidad"), FECHA_LIMITE("fecha"), INFINITO("infinito");
    public final String valor;

    RepeticionTipo(String valor) {
        this.valor =valor;
    }

}
enum Dia{
    LUNES(1, 1),
    MARTES(2, 2),
    MIERCOLES(3, 4),
    JUEVES(4, 8),
    VIERNES(5, 16),
    SABADO(6, 32),
    DOMINGO(7, 64);
    private final int valor;
    private final int valorBinario;
    public int getValor(){return valor;}
    public int getValorBinario(){return valorBinario;}
    Dia(int valor, int valorBinario){
        this.valor = valor;
        this.valorBinario = valorBinario;
    }
}
enum ErrorTipo {
    FECHA_INICIO_INVALIDA("La fecha de inicio es menor a la fecha final"),
    FECHA_FALTANTE("No se definien las fechas de inicio y/o fin"),
    TIPO_FALTANTE("No se selecciona el tipo"),
    NO_TITULO("No se proporciona un titulo"),
    INTERVALO_INVALIDO("No se proporciona un intervalo válido"),
    REPETICIONES_INVALIDAS("Se proporciona un numero de repeticiones invalido"),
    FECHA_ULTIMA_REPETICION("Se proporciona una fecha de repetición final previa a la fecha de comienzo del evento"),
    ASIGNABLE_INVALIDO("Se proporciona un asignable invalido");


    private final String texto;
    ErrorTipo(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return this.texto;
    }
}



