package romulo.graph;

import java.util.*;


public class Graph extends javafx.scene.layout.Pane {
    public List<Point> points = new ArrayList<>();
    public List<Vertex> vertices = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();
    public List<Text> texts = new ArrayList<>();
    public int size;
    public boolean binded = false;
    public Graph(int size) {
        this.size = size;
    }

    public void changeBind() {
        if (!binded) {
            for (Point p : points) {
                p.setBind();
            }
            binded = true;
        } else {
            for (Point p : points) {
                p.unsetBind();
            }
            binded = false;
        }
    }

    public void addVertex(int id) {
        Vertex v = new Vertex(id, this);
        this.vertices.add(v);
        this.getChildren().add(v);
    }

    public void addMultipole(int id, String type) {
        Multipole m = new Multipole(id, type,this);
        this.vertices.add(m);
        this.getChildren().add(m);
    }

    public void addPoint(Point p) {
        points.add(p);
        this.getChildren().add(p);
    }
    void addVertex(Vertex v) {
        vertices.add(v);
        this.getChildren().add(v);
    }
    void addEdge(Edge e) {
        edges.add(e);
        this.getChildren().add(e.e1);
        this.getChildren().add(e.e2);
    }
    void addText(Text t) {
        texts.add(t);
        this.getChildren().add(t);
    }

    public void move(double x, double y) {
        for (Node n : this.vertices) {
            n.setCenterX(n.getCenterX() + x);
            n.setCenterY(n.getCenterY() + y);
        }
        for (Node n : this.points) {
            n.setCenterX(n.getCenterX() + x);
            n.setCenterY(n.getCenterY() + y);
        }
    }
    public void scale(double times) {
        for (Node n : this.vertices) {
            n.scale(times);
        }
        for (Node n : this.points) {
            n.scale(times);
        }
        for (Text n : this.texts) {
            n.scale(times);
        }
    }
}
