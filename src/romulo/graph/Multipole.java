package romulo.graph;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Multipole extends Vertex {
    public String type;
    public List<Connector> connectors = new ArrayList<>();

    Multipole(int id, String type, Graph g) {
        super(id, g, 12, Color.RED, type);
        this.type = type;
    }
    public void setType(String type) {
        this.type = type;
        this.setText(type);
    }
    public void setConnectors(List<List<Integer>> connectors) {
        Map<Integer, Integer> usedEdges = new HashMap<>();
        for (List<Integer> con : connectors) {
            Connector c = new Connector(this);
            for (Integer neig : con) {
                if (this.id < neig) {
                    Edge e = new Edge(this, this.g.vertices.get(neig), this.g);
                    this.edges.add(e);
                    this.g.vertices.get(neig).edges.add(e);
                    c.edges.add(e.e1);
                    this.g.addEdge(e);
                } else if (neig == this.id) {
                    // forbidden TODO
                    continue;
                } else {
                    if (!usedEdges.containsKey(neig))
                        usedEdges.put(neig, 0);
                    for (int i = usedEdges.get(neig); i < this.g.vertices.get(neig).edges.size(); i++) {
                        Edge tmpE = this.g.vertices.get(neig).edges.get(i);
                        if (tmpE.e2.v.equals(this)) {
                            usedEdges.put(neig, i+1);
                            c.edges.add(tmpE.e2);
                            break;
                        }
                    }
                }
            }
            this.connectors.add(c);
        }
    }
}