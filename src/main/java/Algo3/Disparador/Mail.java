package Algo3.Disparador;


import Algo3.Constantes.DisparadorTipo;

public class Mail implements Disparador {
    @Override
    public void disparar(){
        //enviar mail
    }
    public DisparadorTipo getTipo(){return DisparadorTipo.MAIL;}
}
