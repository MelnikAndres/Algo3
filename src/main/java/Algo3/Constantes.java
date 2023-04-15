package Algo3;

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
    LUNES(1),MARTES(2),MIERCOLES(3),JUEVES(4),VIERNES(5),SABADO(6),DOMINGO(7);
    private final int valor;
    public int getValor(){return valor;}
    Dia(int valor){this.valor = valor;}
}
enum ErrorTipo {
    FECHA_INICIO_INVALIDA("La fecha de inicio es menor a la fecha final"),
    FECHA_FALTANTE("No se definien las fechas de inicio y/o fin"),
    TIPO_FALTANTE("No se selecciona el tipo"),
    NO_TITULO("No se proporciona un titulo"),
    INTERVALO_INVALIDO("No se proporciona un intervalo válido"),
    REPETICIONES_INVALIDAS("Se proporciona un numero de repeticiones invalido"),
    FECHA_ULTIMA_REPETICION("Se proporciona una fecha de repetición final previa a la fecha de comienzo del evento");


    public final String texto;
    ErrorTipo(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return this.texto;
    }
}



