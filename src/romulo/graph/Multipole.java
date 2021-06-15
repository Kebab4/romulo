package romulo.graph;

import javafx.scene.paint.Color;

import java.util.*;

public class Multipole extends Vertex {
    public String type;
    public List<Connector> connectors = new ArrayList<>();

    Multipole(int id, String type, Graph g) {
        super(id, g, 18, Color.RED, type);
        this.type = type;
    }
    public void setType(String type) {
        this.type = type;
        this.setText(type);
    }
    public void setConnectors(List<List<Integer>> connectors) {
        Map<Integer, Integer> usedEdges = new HashMap<>();
        Random rand = new Random();
        for (List<Integer> con : connectors) {
            Color color = Color.web("rgb(" + rand.nextInt(255) + "," + rand.nextInt(255) + "," + rand.nextInt(255) + ")");
            Connector c = new Connector(this, color);
            for (Integer neig : con) {
                if (!usedEdges.containsKey(neig)) // pozor ak uz taka edge existuje
                    usedEdges.put(neig, 0); // pozri od posledneho miesta kde si uz bol
                for (int i = usedEdges.get(neig); i < this.g.vertices.get(neig).edges.size(); i++) {
                    Edge tmpE = this.g.vertices.get(neig).edges.get(i);
                    if (tmpE.e2.v.equals(this)) {
                        usedEdges.put(neig, i+1);
                        c.addEdge(tmpE.e2);
                        break;
                    } else if (tmpE.e1.v.equals(this)) {
                        usedEdges.put(neig, i + 1);
                        c.addEdge(tmpE.e1);
                        break;
                    }
                }
            }
            this.connectors.add(c);
        }
    }
}