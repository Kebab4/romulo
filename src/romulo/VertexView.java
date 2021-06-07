package romulo;

public class VertexView extends javafx.scene.shape.Circle {
    int id;

    VertexView(double centerX, double centerY, double radius, int id) {
        super(centerX, centerY, radius);
        this.id = id;
    }
}
