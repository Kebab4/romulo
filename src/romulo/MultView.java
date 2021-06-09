package romulo;

public class MultView extends javafx.scene.shape.Circle {
    int id;

    MultView(double centerX, double centerY, double radius, int id) {
        super(centerX, centerY, radius);
        this.id = id;
        this.setFill(javafx.scene.paint.Color.RED);
    }
}
