package romulo;

public class Vertex extends javafx.scene.shape.Circle {
    int id;

    Vertex(double centerX, double centerY, double radius, int id) {
        super(centerX, centerY, radius);
        this.id = id;
    }
}
