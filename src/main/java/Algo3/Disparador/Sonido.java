package Algo3.Disparador;


import Algo3.Constantes.DisparadorTipo;

public class Sonido implements Disparador {
    @Override
    public void disparar(){
        //hacer sonido
    }
    public DisparadorTipo getTipoDisparador(){return DisparadorTipo.SONIDO;}
}
