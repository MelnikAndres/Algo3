package Algo3;

enum AlarmaTipo {
    NOTIFICACION,SONIDO,MAIL

}
enum FrecuenciaTipo{
    DIARIA,SEMANAL,MENSUAL,ANUAL
}
enum RepeticionTipo {
    CANTIDAD_LIMITE("cantidad"), FECHA_LIMITE("fecha"), INFINITO("infinito");
    public final String valor;
    RepeticionTipo(String valor) {
        this.valor =valor;
    }
}
enum ErrorTipo {
    FECHA_INICIO_INVALIDA, FECHA_FALTANTE, TIPO_FALTANTE, NO_TITULO
}

