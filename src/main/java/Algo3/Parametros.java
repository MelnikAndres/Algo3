package Algo3;

import Algo3.Constantes.ParametroTipo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

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
