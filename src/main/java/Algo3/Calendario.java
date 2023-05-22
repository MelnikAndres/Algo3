package Algo3;


import java.io.*;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;

public class Calendario implements Serializable{

    private final HashMap<Integer, Asignable> asignables = new HashMap<>();
    @JsonProperty("idIncremental")
    private int idIncremental = 0;

    public int getIdIncremental() {
        return idIncremental;
    }

    public HashMap<Integer, Asignable> getAsignables() {
        return asignables;
    }

    public void editar(Integer clave, Asignable asignableEditado){
        asignables.put(clave, asignableEditado);
    }

    public void eliminar(Integer clave) {
        asignables.remove(clave);
    }

    public void agregar(Asignable asignableNuevo){
        asignables.put(idIncremental, asignableNuevo);
        idIncremental = idIncremental + 1;
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
    public void serializar(OutputStream os) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        objectWriter.writeValue(os, this);

    }

    public Calendario deserializar(InputStream is) throws IOException{
        ObjectMapper objectMapper = new JsonMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectReader objectReader = objectMapper.reader();
        return objectReader.readValue(is, Calendario.class);
    }


}