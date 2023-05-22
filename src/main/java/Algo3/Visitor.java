package Algo3;

public class Visitor {
    public boolean visitar(Evento evento, Asignable asignable){
        asignable.comparar(this, evento);
        return true;
    }
    public boolean visitar(Evento evento, Evento evento2){
        evento.comparar(evento2);
        return true;
    }
}
