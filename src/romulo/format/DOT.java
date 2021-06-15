package romulo.format;

import javafx.util.Pair;
import romulo.graph.Edge;
import romulo.graph.Graph;

import java.util.List;

public class DOT {
    String file;
    public DOT(Graph g, List<Pair<Edge, Integer>> weights) {
        String ss = "graph {\noverlap = false;\nsplines = false;\nsep=.3;\nnode[margin=0, fontsize=12, shape=circle, height=.3, width=.3];\n";
        for (Edge e : g.edges) {
            ss += e.e1.v.id + " -- " + e.e2.v.id;
            for (Pair<Edge, Integer> p : weights) {
                if (p.getKey().equals(e)) {
                    ss += "[weight=" + p.getValue() + "]";
                }
            }
            ss += "\n";
        }
        ss += "}\n";
        this.file = ss;
    }
    public String toString() {
        return file;
    }

}