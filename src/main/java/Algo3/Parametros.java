package Algo3;

import Algo3.Constantes.ParametroTipo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Parametros {
    private HashMap<String, ParametroTipo> tipos = new HashMap<>();
    private HashMap<String, String> valores = new HashMap<>();

    public void agregarParametro(String nombre, ParametroTipo tipo, String valor){
        tipos.put(nombre, tipo);
        valores.put(nombre,valor);
    }
    public String getValor(String nombre){
        return valores.get(nombre);
    }
    public ParametroTipo getTipo(String nombre){
        return tipos.get(nombre);
    }
    public List<String> getNombres(){
        return new ArrayList<>(tipos.keySet());
    }
}
