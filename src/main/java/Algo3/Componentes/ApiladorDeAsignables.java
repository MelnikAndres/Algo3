package Algo3.Componentes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiladorDeAsignables extends AnchorPane {
    //Este componente se usa para apilar asignables en la vista diaria del calendario
    private final List<Apilable> apilados = new ArrayList<>();
    private final List<Double> anchosDeFilas = new ArrayList<>();
    public ApiladorDeAsignables(){
        this.setStyle("-fx-background-color: transparent");
        //iniciar los anchos de las filas en 0
        for(int i = 0; i<24;i++){
            for(int j=0;j<4;j++){
                anchosDeFilas.add(0.0);
            }
        }
    }
    public void agregarComportamiento(Apilable apilable){
        apilable.addBorrarEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getChildren().remove(apilable);
                desapilar(apilable);
            }
        });
    }

    public void apilar(Apilable nuevoApilado){
        if(apilados.contains(nuevoApilado)){
            return;
        }
        double mayorAncho = 0;
        List<Integer> filasOcupadas = nuevoApilado.filasOcupadas();
        //buscar la fila ocupada por el nuevo apilable que tenga el mayor ancho
        for(Integer fila: filasOcupadas){
            double anchoDeFila = anchosDeFilas.get(Math.min(fila,95));
            if(anchoDeFila>mayorAncho){
                mayorAncho = anchoDeFila;
            }
        }
        //colocar el nuevo apilable en el mayor ancho encontrado
        AnchorPane.setLeftAnchor(nuevoApilado,mayorAncho);
        apilados.add(nuevoApilado);
        //actualizar el mayor ancho de todas las filas ocupadas
        for(Integer fila: filasOcupadas){
            anchosDeFilas.set(Math.min(fila,95),mayorAncho + nuevoApilado.getWidth());
        }
    }

    public void desapilarTodo(){
        getChildren().clear();
        apilados.clear();
        Collections.fill(anchosDeFilas, 0.0);
    }
    public void desapilar(Apilable apiladoAborrar){
        if(!apilados.contains(apiladoAborrar)){
            return;
        }
        List<Apilable> aMover = new ArrayList<>();
        //buscar todos los elementos que están después del apilado a desapilar y en sus mismas filas
        for (int i = apilados.size()-1; i>=0; i--){
            var apiladoActual = apilados.get(i);
            if(apiladoActual==apiladoAborrar){
                //desapilar el apilado correspondiente
                apilados.remove(apiladoAborrar);
                break;
            }
            var haySuperposicion = apiladoAborrar.haySuperposicion(apiladoActual);
            if(haySuperposicion){
                aMover.add(apiladoActual);
            }
        }
        //mover todos los que estaban despues del que se borró
        moverSiguientes(apiladoAborrar,aMover);
        List<String> a = new ArrayList<>();
        String pal = "hola";
    }
    private void moverSiguientes(Apilable movidoAnterior, List<Apilable> aMover){
        List<Apilable> movidos = new ArrayList<>();
        List<Integer> ocupadas = movidoAnterior.filasOcupadas();
        //mover los apilables a mover, moviendo primero los más cercanos a la pared izquierda de la ventana
        //nota: Nunca puede pasar que un apilable apilado después que otro, este más cerca a la izquierda que ese otro
        //entonces SIEMPRE se va a recorrer en el orden correcto
        for(int i=aMover.size()-1;i>=0;i--){
            var movido = aMover.get(i);
            //si ya fue movido en una llamada recursiva, ignorar esta llamada
            double anchoMaximo = 0;
            //buscar, en las filas que ocupa, el mayor ancho antes del apilable a mover
            for (int j = apilados.indexOf(movido)-1; j>=0; j--) {
                var siguienteApilado = apilados.get(j);
                boolean haySuperposicion = movido.haySuperposicion(siguienteApilado);
                if(haySuperposicion){
                    var anchoActual = AnchorPane.getLeftAnchor(siguienteApilado)+siguienteApilado.getWidth();
                    if(anchoActual > anchoMaximo){
                        anchoMaximo = anchoActual;
                    }
                }
            }
            //mover el apilable
            AnchorPane.setLeftAnchor(movido, anchoMaximo);
            //marcarlo como ya movido para evitar repetir movimientos
            movidos.add(movido);
            //ajustar el ancho de las filas movidas
            List<Integer> ocupadasMovido = movido.filasOcupadas();
            for(Integer fila:ocupadasMovido){
                ocupadas.remove(fila);
                anchosDeFilas.set(fila,AnchorPane.getLeftAnchor(movido) + movido.getWidth());
            }
        }
        //Si sobran filas donde no se movio a ninguno, se busca el mayor ancho de esa fila
        for(int i = apilados.size()-1; i>=0; i--){
            var apiladoActual = apilados.get(i);
            var ocupadasActual = apiladoActual.filasOcupadas();
            ocupadasActual.retainAll(ocupadas);
            for (Integer ocupada: ocupadasActual){
                ocupadas.remove(ocupada);
                anchosDeFilas.set(ocupada,AnchorPane.getLeftAnchor(apiladoActual) + apiladoActual.getWidth());
            }
            //si ya se ajustaron todas las filas, se termina
            if(ocupadas.size() == 0){
                break;
            }
        }
        //si sobra alguna fila se la deja en 0
        for(int resto:ocupadas){
            anchosDeFilas.set(resto,0.0);
        }
        //mover a todos los que estan sostenidos por apilables ya movidos y que aun no se movieron
        for(Apilable movido: movidos){
            this.moverGrupo(movido, movidos);
        }
    }
    private void moverGrupo(Apilable apiladoAmover, List<Apilable> yaMovidos){
        List<Apilable> aMover = new ArrayList<>();
        //buscar todos los elementos que están después del apilado a mover y en sus mismas filas
        for (int i = apilados.size()-1; i>=0; i--){
            var apiladoActual = apilados.get(i);
            if(apiladoActual==apiladoAmover){
                break;
            }
            var haySuperposicion = apiladoAmover.haySuperposicion(apiladoActual);
            if(haySuperposicion && !yaMovidos.contains(apiladoActual)){
                aMover.add(apiladoActual);
            }
        }
        moverSiguientes(apiladoAmover, aMover);
    }
}
