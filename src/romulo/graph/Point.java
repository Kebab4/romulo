package romulo.graph;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;

import java.util.Random;

public class Point extends Node {
    public Edge edge;
    double lastX, lastY;
    Random rand;
    public Point(Edge e) {
        super(4, Color.BLACK);
        this.edge = e;
        this.rand = new Random();
    }
    public void setBind() {
        this.lastX = this.getCenterX();
        this.lastY = this.getCenterY();
        this.setOnMousePressed(null);
        this.setOnMouseDragged(null);
        int shift = 50-rand.nextInt(101);
        this.setTranslateX(0);
        this.setTranslateY(0);
        DoubleBinding midPointX = this.edge.e1.startXProperty().add(
                        this.edge.e2.startXProperty()).divide(2);
        DoubleBinding midPointY = this.edge.e1.startYProperty().add(
                this.edge.e2.startYProperty()).divide(2);
        this.centerXProperty().bind(midPointX.add(midPointX.divide(1000).multiply(shift)));
        this.centerYProperty().bind(midPointY.add(midPointY.divide(1000).multiply(shift)));
    }
    public void unsetBind() {
        this.centerXProperty().unbind();
        this.centerYProperty().unbind();
        this.setOnMousePressed(circleOnMousePressedEventHandler);
        this.setOnMouseDragged(circleOnMouseDraggedEventHandler);
    }

}