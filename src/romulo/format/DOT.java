package romulo.format;

import javafx.util.Pair;
import romulo.graph.Edge;
import romulo.graph.Graph;

import java.util.List;

public class DOT {

    /*
    FORMAT
    {
        ...;
        a -- b [weight=x] // edge between a and b
    }
    */

    String file;
    public DOT(Graph g, List<Pair<Edge, Integer>> weights) {
        StringBuilder ss = new StringBuilder("graph {\noverlap = false;\nsplines = false;\nsep=.3;\nnode[margin=0, fontsize=12, shape=circle, height=.3, width=.3];\n");
        for (Edge e : g.edges) {
            ss.append(e.e1.v.id).append(" -- ").append(e.e2.v.id);
            for (Pair<Edge, Integer> p : weights) {
                if (p.getKey().equals(e)) {
                    ss.append("[weight=").append(p.getValue()).append("]");
                }
            }
            ss.append("\n");
        }
        ss.append("}\n");
        this.file = ss.toString();
    }
    public String toString() {
        return file;
    }

}