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
        this.startXProperty().bind(v.centerXProperty());
        this.startYProperty().bind(v.centerYProperty());
        this.endXProperty().bind(parent.middle.centerXProperty());
        this.endYProperty().bind(parent.middle.centerYProperty());
    }
}