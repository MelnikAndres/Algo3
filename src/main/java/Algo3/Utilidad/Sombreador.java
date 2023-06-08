package Algo3.Utilidad;

import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Sombreador {
    public static void sombrear(Node nodo){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.THREE_PASS_BOX);
        dropShadow.setHeight(40);
        dropShadow.setOffsetY(4);
        dropShadow.setOffsetX(3);
        dropShadow.setRadius(8);
        dropShadow.setWidth(12);
        dropShadow.setColor(new Color(0.25,0.25,0.33,0.9));
        nodo.setEffect(dropShadow);
    }
}
