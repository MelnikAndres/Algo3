package Algo3;


import java.io.*;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

    public void agregarAlarma(Integer clave, Alarma alarma){
        Asignable asignable = asignables.get(clave);
        if(asignable != null){
            asignable.agregarAlarma(alarma);
        }
    }
    public void serializar() throws IOException {
        ObjectMapper mapper = new JsonMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.registerModule(new JavaTimeModule());
        File file = new File("package.json");
        mapper.writeValue(file, this);
    }

    public Calendario deserializar(File archivoJSON) throws IOException {
        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(archivoJSON, Calendario.class);
    }


}