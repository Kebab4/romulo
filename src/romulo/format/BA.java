package romulo.format;

import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.graph.Vertex;

import java.util.*;

public class BA {
    String file;
    public BA(List<Graph> graphs) { // from model
        String s = graphs.size() + "\n";
        for (int i = 0; i < graphs.size(); i++) {
            s += i + "\n" + graphs.get(i).size + "\n";
            for (Vertex v : graphs.get(i).vertices) {
                for (Edge e : v.edges) {
                    if (e.e1.v.equals(v)) {
                        s += e.e2.v.id + " ";
                    } else {
                        s += e.e1.v.id + " ";
                    }
                }
                s += "\n";
            }
        }
        this.file = s;
    }
    public String toString() {
        return file;
    }
}

