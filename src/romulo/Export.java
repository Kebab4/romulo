package romulo;

import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.graph.Vertex;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

public class Export {
    public static String BAfromModel(List<Graph> graphs) {
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
        return s;
    }
    public static void FilefromString(String s, String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(s);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static String DotFromModel(Graph g) {
        /*
        std::stringstream ss;
        ss << "graph {\noverlap = false;\nsplines = false;\nsep=.3;\nnode[margin=0, fontsize=12, shape=circle, height=.3, width=.3];\n";
        for (auto ii : G.list(RP::all(), IP::primary())) {
            ss << ii->n1().to_int() << " -- " << ii->n2().to_int() << " [id=\"" << ii->l().index() << "\"";
            if (std::find(edges.begin(), edges.end(), ii->e().id().to_int()) != edges.end()) {
                ss << ", len=" << sl;
            } else if (std::find(weakedges.begin(), weakedges.end(), ii->e().id().to_int()) != weakedges.end()) {
                ss << ", len=" << wl;
            }
            ss << "];\n";
        }
        ss << "}\n";
        return ss.str();
         */
        String ss = "graph {\noverlap = false;\nsplines = false;\nsep=.3;\nnode[margin=0, fontsize=12, shape=circle, height=.3, width=.3];\n";
        for (Edge e : g.edges) {
            ss += e.e1.v.id + " -- " + e.e2.v.id + "\n";
        }
        ss += "}\n";
        return ss;
    }
}
