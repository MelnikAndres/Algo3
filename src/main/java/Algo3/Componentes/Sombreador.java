package Algo3.Componentes;

import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Sombreador {
    public static void sombrear(Node nodo){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
        dropShadow.setHeight(20);
        dropShadow.setOffsetY(4);
        dropShadow.setRadius(8);
        dropShadow.setWidth(0);
        dropShadow.setColor(new Color(0.25,0.25,0.25,0.8));
        nodo.setEffect(dropShadow);
    }
}
