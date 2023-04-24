package Algo3.Disparador;


import Algo3.Constantes.DisparadorTipo;

public class Notificacion implements Disparador {
   @Override
    public void disparar(){
        //enviar notificacion
    }
    public DisparadorTipo getTipo(){return DisparadorTipo.NOTIFICACION;}

}
