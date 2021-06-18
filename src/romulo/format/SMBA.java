package romulo.format;


import javafx.scene.text.Text;
import romulo.Command;
import romulo.Utils;
import romulo.graph.Graph;
import romulo.graph.Multipole;
import romulo.graph.Point;
import romulo.graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SMBA implements MBA {

    /*
    FORMAT
    n // number of graphs
    n-times:
        m // number of vertices, number of multipoles
        m-times:
            i j k l... // list of neighbours
            if multipole:
                Name i j, k, l m n ... // Name of multipole and neighbours grouped in connectors
     */

    @Override
    public List<Graph> loadModel(Scanner scan) {

        List<Graph> graphs = new ArrayList<>();
        int numOfGraphs = scan.nextInt();
        for (int i = 0; i < numOfGraphs; i++) {
            scan.nextInt(); // index of graph
            int sizeOfGraph = scan.nextInt(); // pocet vrcholov
            Graph g = new Graph(sizeOfGraph);
            scan.nextLine(); // because of problems with endline
            String[] lines = new String[sizeOfGraph];
            for (int j = 0; j < sizeOfGraph; j++) {
                String l = scan.nextLine().trim();
                if (l.matches("[0-9 ]+")) {
                    g.addVertex(j);
                } else {
                    g.addMultipole(j, l.split(" +")[l.split(" +").length-1]);
                }
                lines[j] = l;
            }
            for (int j = 0; j < sizeOfGraph; j++) {
                String l = lines[j];
                if (l.matches("[0-9 ]+")) { // normalny vrchol
                    for (Integer k : Utils.getListNumbers(l.trim(), " +")) {
                        g.vertices.get(j).addEdge(k, g);
                    }
                } else { // multipol
                    String name = l.split(" +")[l.split(" +").length-1];
                    String[] allCon = String.join(" ", Arrays.copyOfRange(l.split(" +"), 0,
                            l.split(" +").length - 1)).split(",");
                    List<List<Integer>> connectors = new ArrayList<>();
                    for (String con : allCon) {
                        List<Integer> cons = Utils.getListNumbers(con.trim(), " +");
                        for (Integer k : cons) {
                            g.vertices.get(j).addEdge(k, g);
                        }
                        connectors.add(cons);
                    }
                    Multipole m = (Multipole) g.vertices.get(j);
                    m.setConnectors(connectors);
                    m.setType(name);
                }
            }
            for (Vertex v : g.vertices) {
                v.toFront();
            }
            for (Point p : g.points) {
                p.toFront();
            }
            for (Text t : g.texts) {
                t.toFront();
            }
            graphs.add(g);
        }
        Command.Run("rm -rf export/*");
        String ba = new BA(graphs).toString();
        Utils.FilefromString(ba, "export/tmp.ba");
        cutAndPosition(graphs);
        return graphs;
    }
}