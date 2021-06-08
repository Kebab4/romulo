package romulo;

import java.util.*;

public class GraphView extends javafx.scene.layout.Pane {
    List<VertexView> vertices = new ArrayList<>();
    List<EdgeView> edges = new ArrayList<>();

    void addEdge(List<float[]> poss, long i, long j) {
        EdgeView e = new EdgeView(poss, i, j);
        edges.add(e);
        this.getChildren().add(e);
    }
    void addVertex(double x, double y, int r) {
        VertexView v = new VertexView(x, y, r, vertices.size());
        vertices.add(v);
        this.getChildren().add(v);
    }
}
