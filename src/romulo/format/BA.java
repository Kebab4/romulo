package romulo.format;

import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.graph.Vertex;

import java.util.*;

public class BA {

    /*
    FORMAT
    n // number of graphs
    n-times:
        m // number of vertices
        m-times:
            i j k l... // list of neighbours
    */

    String file;
    public BA(List<Graph> graphs) { // from model
        StringBuilder s = new StringBuilder(graphs.size() + "\n");
        for (int i = 0; i < graphs.size(); i++) {
            s.append(i + 1).append("\n").append(graphs.get(i).size).append("\n");
            for (Vertex v : graphs.get(i).vertices) {
                for (Edge e : v.edges) {
                    if (e.e1.v.equals(v)) {
                        s.append(e.e2.v.id).append(" ");
                    } else {
                        s.append(e.e1.v.id).append(" ");
                    }
                }
                s.append("\n");
            }
        }
        this.file = s.toString();
    }
    public String toString() {
        return file;
    }
}

