package romulo.graph;

import java.util.*;

import javafx.scene.text.*;

public class Graph extends javafx.scene.layout.Pane {
    public List<Point> points = new ArrayList<>();
    public List<Vertex> vertices = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();
    public List<Text> texts = new ArrayList<>();
    public int size;
    public Graph(int size) {
        super();
        for (int i = 0; i < size; i++) {
            this.addVertex(new Vertex(i, this));
        }
        this.size = size;
    }
    public void addPoint(Point p) {
        points.add(p);
        this.getChildren().add(p);
    }
    void addVertex(Vertex v) {
        vertices.add(v);
        this.getChildren().add(v);
    }
    public void addEdge(Edge e) {
        edges.add(e);
        this.getChildren().add(e.e1);
        this.getChildren().add(e.e2);
    }
    void addText(Text t) {
        texts.add(t);
        this.getChildren().add(t);
    }

    public void move(double x, double y) {
        for (javafx.scene.Node n : this.getChildren()) {
            n.setTranslateX(n.getTranslateX() + x);
            n.setTranslateY(n.getTranslateY() + y);
        }
    }
    public void scale(double times) {
        this.setScaleX(this.getScaleX() * times);
        this.setScaleY(this.getScaleY() * times);
    }
}
