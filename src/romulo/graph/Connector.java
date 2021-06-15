package romulo.graph;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class Connector {
    private final List<HalfEdge> edges = new ArrayList<>();
    Multipole parent;
    Color color;
    public Connector(Multipole parent, Color c) {
        this.parent = parent;
        this.color = c;
    }
    public void addEdge(HalfEdge e) {
        this.edges.add(e);
        for (HalfEdge edge : edges) {
            edge.setStroke(this.color);
        }
    }
}
