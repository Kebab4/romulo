package romulo.graph;

public class Edge {
    public HalfEdge e1, e2;
    public Point middle;
    Edge(Vertex v1, Vertex v2, Graph g) {
        Point p = new Point(this);
        this.middle = p;
        this.e1 = new HalfEdge(v1, this);
        this.e2 = new HalfEdge(v2, this);
        g.addPoint(p);
    }
}