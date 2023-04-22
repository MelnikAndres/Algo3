package Algo3.Constantes;

public enum ErrorTipo {
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
