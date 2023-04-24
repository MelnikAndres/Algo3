package Algo3;


import Algo3.Constantes.DisparadorTipo;
import Algo3.Disparador.Mail;
import Algo3.Disparador.Notificacion;
import Algo3.Disparador.Sonido;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.Assert.*;


public class AlarmaTest {
    @Test
    public void alarmaTipoMail(){

        var alarma = new Alarma(LocalDateTime.of(2022,2,2,0,0), Duration.ZERO, new Mail());
        assertEquals(DisparadorTipo.MAIL, alarma.getTipo());
    }

    @Test
    public void alarmaTipoNotificacion(){
        var alarma = new Alarma(LocalDateTime.of(2022,2,2,0,0), Duration.ZERO, new Notificacion());
        assertEquals(DisparadorTipo.NOTIFICACION, alarma.getTipo());
    }
    @Test
    public void alarmaTipoSonido(){
        var alarma = new Alarma(LocalDateTime.of(2022,2,2,0,0), Duration.ZERO, new Sonido());
        assertEquals(DisparadorTipo.SONIDO, alarma.getTipo());
    }
}