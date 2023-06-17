package Algo3.Modelo;


import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Algo3.Constantes.Dia;
import Algo3.Disparador.Notificacion;
import Algo3.Frecuencia.FrecuenciaDiaria;
import Algo3.Frecuencia.FrecuenciaSemanal;
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
    public Alarma proximaAlarma(){
        LocalDateTime fechaComparacion = LocalDateTime.MAX;
        Alarma proximaAlarma = null;
        for(Asignable asignable: asignables.values()){
            for(Alarma alarma: asignable.getAlarmas()){
                if(alarma.getFecha().minus(alarma.getTiempoAntes()).isBefore(fechaComparacion)){
                    proximaAlarma = alarma;
                    fechaComparacion = alarma.getFecha().minus(alarma.getTiempoAntes());
                }
            }
        }
        return proximaAlarma;
    }



    public Asignable asignableConProximaAlarma(){
        LocalDateTime fechaComparacion = LocalDateTime.MAX;
        Asignable proximaAlarma = null;
        for(Asignable asignable: asignables.values()){
            for(Alarma alarma: asignable.getAlarmas()){
                if(alarma.getFecha().minus(alarma.getTiempoAntes()).isBefore(fechaComparacion)){
                    proximaAlarma = asignable;
                    fechaComparacion = alarma.getFecha().minus(alarma.getTiempoAntes());
                }
            }
        }
        return proximaAlarma;
    }

    public Map<Integer, List<LocalDateTime>> obtenerAparicionesEnMesyAnio(int numeroDeMes, int anio){
        HashMap<Integer,List<LocalDateTime>> repeticiones = new HashMap<>();
        for(Integer asignableId: asignables.keySet()){
            repeticiones.put(asignableId,obtenerAsignablePorId(asignableId).obtenerAparicionesEnMesyAnio(numeroDeMes,anio));
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