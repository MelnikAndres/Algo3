package Algo3.Modelo;


import Algo3.Constantes.ErrorTipo;
import Algo3.Constantes.ParametroTipo;
import Algo3.Utilidad.Editor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
public abstract class Asignable implements Serializable {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private List<Alarma> alarmas = new ArrayList<>();

    Asignable(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        if (titulo == null || titulo.isEmpty())
            throw new RuntimeException(ErrorTipo.NO_TITULO.toString());

        if (fechaInicio == null || fechaFinal == null)
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());

        if (fechaInicio.isAfter(fechaFinal))
            throw new RuntimeException(ErrorTipo.FECHA_INICIO_INVALIDA.toString());

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public void agregarAlarma(Alarma alarma) throws RuntimeException {
        if (alarma != null) {
            alarmas.add(alarma);
        }
    }

    public void editar(String nuevoTitulo, String nuevaDescripcion,
                       LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFinal) throws RuntimeException {
        if (nuevoTitulo == null || nuevoTitulo.isEmpty()) {
            throw new RuntimeException(ErrorTipo.NO_TITULO.toString());
        }
        if (nuevaFechaInicio == null || nuevaFechaFinal == null) {
            throw new RuntimeException(ErrorTipo.FECHA_FALTANTE.toString());
        }
        if (nuevaFechaInicio.isAfter(nuevaFechaFinal)) {
            throw new RuntimeException(ErrorTipo.FECHA_INICIO_INVALIDA.toString());
        }
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
        this.fechaInicio = nuevaFechaInicio;
        this.fechaFinal = nuevaFechaFinal;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Alarma> getAlarmas() {
        return alarmas;
    }

    public abstract List<LocalDateTime> obtenerAparicionesEnMesyAnio(int numeroDeMes, int anio);
    @Override
    public String toString(){
        String data = titulo +", "+descripcion+", "+fechaInicio.toString()+
                ", "+fechaFinal.toString();
        for (Alarma alarma: alarmas) {
            data =data.concat(", "+alarma.toString());
        }
        return data;
    }
    public Map<ParametroTipo,String> obtenerParametros(){
        HashMap<ParametroTipo,String> parametros = new HashMap<>();
        parametros.put(ParametroTipo.TITULO,titulo);
        parametros.put(ParametroTipo.DESCRIPCION,descripcion);
        parametros.put(ParametroTipo.DIACOMPLETO,fechaFinal.equals(fechaInicio)?"1":"");
        parametros.put(ParametroTipo.FECHAINICIAL,fechaInicio.toString());
        parametros.put(ParametroTipo.FECHAFINAL, fechaFinal.toString());
        return parametros;
    }
    public boolean comparar(Asignable asignable) {
        return this.toString().equals(asignable.toString());
    }

    public abstract void aceptarEdicion(Editor editor);
}
