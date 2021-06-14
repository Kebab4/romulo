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
}
