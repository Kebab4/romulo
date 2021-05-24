package romulo;

import java.util.*;

public class Graph extends javafx.scene.layout.Pane {
    List<Vertex> vertices = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();

    void addEdge(List<float[]> poss, long i, long j) {
        Edge e = new Edge(poss, i, j);
        edges.add(e);
        this.getChildren().add(e);
    }
    void addVertex(int x, int y, int r) {
        Vertex v = new Vertex(x, y, r, vertices.size());
        vertices.add(v);
        this.getChildren().add(v);
    }
}
