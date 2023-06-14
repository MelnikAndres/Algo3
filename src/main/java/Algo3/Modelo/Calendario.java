package Algo3.Modelo;


import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Algo3.Frecuencia.FrecuenciaDiaria;
import Algo3.Repeticion.RepeticionCantidadLimite;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Calendario implements Serializable{
    private final HashMap<Integer, Asignable> asignables = new HashMap<>();
    @JsonProperty("idIncremental")
    private int idIncremental = 0;

    public Calendario(){
        var eventoDePrueba = new Evento("Prueba","Esto es una prueba",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(61),
                new FrecuenciaDiaria(1),
                new RepeticionCantidadLimite(new FrecuenciaDiaria(1), LocalDateTime.now(), 10));
       this.agregar(eventoDePrueba);
    }
    public int getIdIncremental() {
        return idIncremental;
    }


    public Asignable obtenerAsignablePorId(Integer id){
        return asignables.get(id);
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

    public Map<Asignable, List<LocalDateTime>> obtenerAparicionesEnMesyAnio(int numeroDeMes, int anio){
        HashMap<Asignable,List<LocalDateTime>> repeticiones = new HashMap<>();
        for(Asignable asignable: asignables.values()){
            repeticiones.put(asignable,asignable.obtenerAparicionesEnMesyAnio(numeroDeMes,anio));
        }
        return repeticiones;
    }


    public static Calendario deserializar(InputStream is) throws IOException{
        ObjectMapper objectMapper = new JsonMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectReader objectReader = objectMapper.reader();
        return objectReader.readValue(is, Calendario.class);
    }


}