package Algo3;

import java.util.HashMap;

public class Calendario {

    private final HashMap<Integer, Asignable> asignables = new HashMap<>();
    private int IdIncremental = 0;

    public void editar(Integer clave, Asignable asignableEditado){
        asignables.put(clave, asignableEditado);
    }

    public void eliminar(Integer clave) {
        asignables.remove(clave);
    }

    public void agregar(Asignable asignableNuevo){
        asignables.put(IdIncremental, asignableNuevo);
        IdIncremental = IdIncremental + 1;
    }

    public boolean contiene(Asignable asignable){
        return asignables.containsValue(asignable);
    }
    public Asignable asignableConClave(Integer clave){return asignables.get(clave);}
}