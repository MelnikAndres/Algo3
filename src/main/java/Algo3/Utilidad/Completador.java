package Algo3.Utilidad;

import Algo3.Modelo.Asignable;
import Algo3.Modelo.Tarea;

public class Completador {
    private boolean nuevoEstado;
    private boolean soloRecibir;
    private boolean soloPreguntar;

    public Completador(boolean nuevoEstado){
        this.nuevoEstado = nuevoEstado;
    }
    public boolean esRecibido(Asignable asignable){
        soloRecibir = true;
        return asignable.recibirCompletador(this);
    }
    public boolean estadoCompletar(Asignable asignable){
        soloPreguntar = true;
        asignable.recibirCompletador(this);
        return soloPreguntar;
    }
    public void trabajar(Tarea tarea){
        if(soloRecibir){
            soloRecibir = false;
            return;
        }
        if(soloPreguntar){
            soloPreguntar = tarea.getEsCompletada();
            return;
        }
        tarea.setCompletada(nuevoEstado);
    }
}
