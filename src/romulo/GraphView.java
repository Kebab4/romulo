package romulo;

import javafx.scene.Node;
import java.util.*;

public class GraphView extends javafx.scene.layout.Pane {
    List<VertexView> vertices = new ArrayList<>();
    List<MultView> multipoles = new ArrayList<>();
    List<EdgeView> edges = new ArrayList<>();

    void addEdge(List<float[]> poss, long i, long j) {
        EdgeView e = new EdgeView(poss, i, j);
        edges.add(e);
        this.getChildren().add(e);
    }
    void addVertex(double x, double y, int r) {
        VertexView v = new VertexView(x, y, r, vertices.size() + multipoles.size());
        vertices.add(v);
        this.getChildren().add(v);
    }
    void addMult(double x, double y, int r) {
        MultView m = new MultView(x, y, r, vertices.size() + multipoles.size());
        multipoles.add(m);
        this.getChildren().add(m);
    }
    void move(double x, double y) {
        for (Node n : this.getChildren()) {
            n.setTranslateX(n.getTranslateX() + x);
            n.setTranslateY(n.getTranslateY() + y);
        }
    }
    void scale(double times) {
        this.setScaleX(this.getScaleX() * times);
        this.setScaleY(this.getScaleY() * times);
    }
}
