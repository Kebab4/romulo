package romulo;

import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.graph.Vertex;

import java.io.FileWriter;
import java.io.IOException;

public class Export {
    public static String BAfromModel(Graph g) {
        String s = "1\n1\n" + g.size + "\n";
        for (Vertex v : g.vertices) {
            for (Edge e : v.edges) {
                if (e.e1.v.equals(v)) {
                    s += e.e2.v.id + " ";
                } else {
                    s += e.e1.v.id + " ";
                }
            }
            s += "\n";
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
}
