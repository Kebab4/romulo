package romulo.graph;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.scene.paint.Color;
import java.util.Random;

public class Point extends Node {
    public Edge edge;
    double lastX, lastY;
    Random rand;
    public Point(Edge e) {
        super(3, Color.GREY);
        this.edge = e;
        this.rand = new Random();
    }
    public void setBind() {
        this.lastX = this.getCenterX();
        this.lastY = this.getCenterY();
        this.setOnMousePressed(null);
        this.setOnMouseDragged(null);
        int shift = 2-rand.nextInt(5);
        DoubleBinding midPointX = this.edge.e1.startXProperty().add(
                        this.edge.e2.startXProperty()).divide(2);
        DoubleBinding midPointY = this.edge.e1.startYProperty().add(
                this.edge.e2.startYProperty()).divide(2);
        this.centerXProperty().bind(midPointX.add(midPointX.divide(100).multiply(shift)));
        this.centerYProperty().bind(midPointY.add(midPointY.divide(100).multiply(shift)));
        System.out.println(this.getCenterX() + " " + this.getCenterY());
    }
    public void unsetBind() {
        this.centerXProperty().unbind();
        this.centerYProperty().unbind();
        this.setCenterX(this.lastX);
        this.setCenterY(this.lastY);
        this.setOnMousePressed(circleOnMousePressedEventHandler);
        this.setOnMouseDragged(circleOnMouseDraggedEventHandler);
    }

}