package romulo.graph;


import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;
import java.util.List;

public class Vertex extends Node {
    public int id;
    public List<Edge> edges = new ArrayList<>();
    Graph g;


    public Vertex(int id, Graph g, int radius, Color color, String meno) {
        super(radius, color);
        this.id = id;
        this.g = g;
        this.setText(meno);
    }

    public Vertex(int id, Graph g) {
        super(10, Color.BLACK);
        this.id = id;
        this.g = g;
        this.setText(String.valueOf(id));
    }
    public void addEdge(int neig, Graph g) {
        if (this.id < neig) {
            Edge e = new Edge(this, g.vertices.get(neig), g);
            this.edges.add(e);
            g.addEdge(e);
            g.vertices.get(neig).edges.add(e);

        } else if (neig == this.id) {
            Edge e = new Edge(this, this, g);
            this.edges.add(e);
            g.addEdge(e);
        }
    }

    void setText(String meno) {
        Text text = new Text(String.valueOf(meno));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.xProperty().bind(this.centerXProperty());
        text.yProperty().bind(this.centerYProperty());
        text.setTextAlignment(TextAlignment.CENTER);
        this.g.addText(text);
    }

}
