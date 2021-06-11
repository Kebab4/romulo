package romulo.graph;

public class HalfEdge extends javafx.scene.shape.Line {
    String color;
    Edge parent;
    public Vertex v;
    HalfEdge(Vertex v, Edge e) {
        super();
        this.parent = e;
        this.v = v;
        this.setStrokeWidth(1);
        this.startXProperty().bind(v.centerXProperty().add(v.translateXProperty()));
        this.startYProperty().bind(v.centerYProperty().add(v.translateYProperty()));
        this.endXProperty().bind(parent.middle.centerXProperty().add(parent.middle.translateXProperty()));
        this.endYProperty().bind(parent.middle.centerYProperty().add(parent.middle.translateYProperty()));
    }
}