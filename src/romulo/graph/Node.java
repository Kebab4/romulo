package romulo.graph;

import javafx.scene.paint.*;

public class Node extends javafx.scene.shape.Circle {
    int radius;
    Color color;

    public Node(double radius, Color color) {
        super(radius);
        this.setFill(javafx.scene.paint.Color.TRANSPARENT);
        setColor(color);
        this.color = color;
    }
    public void setXY(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }
    public void setColor(Color color) {
        this.color = color;
        this.setStroke(color);
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
}