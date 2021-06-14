package romulo.graph;

import javafx.scene.paint.*;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;

import java.util.EventObject;

public class Node extends javafx.scene.shape.Circle {
    int radius;
    Color color;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public Node(double radius, Color color) {
        super(radius);
        //this.setFill(javafx.scene.paint.Color.TRANSPARENT);
        setColor(color);
        this.color = color;
        this.setCursor(Cursor.HAND);
        this.setOnMousePressed(circleOnMousePressedEventHandler);
        this.setOnMouseDragged(circleOnMouseDraggedEventHandler);
    }
    public void setXY(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }
    public void setColor(Color color) {
        this.color = color;
        this.setStroke(color);
    }

    public void scale(double times) {
        this.setTranslateX(this.getTranslateX());
        this.setTranslateY(this.getTranslateY());
        this.setCenterX(this.getCenterX());
        this.setCenterY(this.getCenterY());
        this.radius *= times;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
        new EventHandler<>() {

            @Override
            public void handle(MouseEvent t) {
                Object eo = t.getSource();
                if (t.getSource() instanceof Text) {
                    eo = ((Text) t.getSource()).parent;
                }
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Node) eo).getTranslateX();
                orgTranslateY = ((Node) eo).getTranslateY();
            }
        };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
        new EventHandler<>() {

            @Override
            public void handle(MouseEvent t) {
                Object eo = t.getSource();
                if (t.getSource() instanceof Text) {
                    eo = ((Text) t.getSource()).parent;
                }
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                ((Node) eo).setTranslateX(newTranslateX);
                ((Node) eo).setTranslateY(newTranslateY);

                //((Node)(t.getSource())).setCenterX(t.getSceneX());
               // ((Node)(t.getSource())).setCenterY(t.getSceneY());
            }
        };
}