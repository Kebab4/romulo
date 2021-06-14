package romulo;

import javafx.scene.paint.Color;
import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.graph.Multipole;
import romulo.graph.Vertex;
import romulo.graph.Point;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

import java.util.*;

public interface Formatter {
    List<Graph> loadModel(Scanner s);
    default List<Integer> getListNumbers(String s, String regexSep) {
        List<Integer> list = new ArrayList<>();
        for (String field : s.split(regexSep)) { // susedia vrchola
            list.add(Integer.parseInt(field));
        }
        return list;
    }
}
/*
class ExtendedFormatter implements Formatter {

    /*
    FORMAT
    n // number of graphs
    n-times:
        m o // number of vertices, number of multipoles
        m-times:
            i j k l... // list of neighbours
        o-times:
            i j k l... // list of vertices in multipole
            r s t u... // number of connectors in connector
            a b, c d, e f... // r-times, s-times, t-times edges as vertex vertex, vertex vertex...


    @Override
    public List<Graph> loadModel(Scanner scan) {
        List<Graph> graphs = new ArrayList<>();
        int numOfGraphs = scan.nextInt();
        for (int i = 0; i < numOfGraphs; i++) {
            int indexNum = scan.nextInt();
            int sizeOfGraph = scan.nextInt(); // pocet vrcholov
            int numOfMultipoles = scan.nextInt(); // pocet multipolov
            scan.nextLine(); // because of problems with endline
            List<List<Integer>> incidence = new ArrayList<>();
            for (int j = 0; j < sizeOfGraph; j++) {
                incidence.add(this.getListNumbers(scan.nextLine(), " +")); // susedia vrchola
            }
            Graph g = new Graph(sizeOfGraph, incidence, new ArrayList<>());
            for (int j = 0; j < numOfMultipoles; j++) {
                List<Integer> vertices = this.getListNumbers(scan.nextLine(), " +"); // vrcholy multipolu
                List<Integer> conSizes = this.getListNumbers(scan.nextLine(), " +"); // pocty konektorov
                List<List<Pair<Integer, Integer>>> connectors = new ArrayList<>();
                String[] allCon = scan.nextLine().split(",");
                int iterator = 0;
                for (Integer conSize : conSizes) {
                    List<Pair<Integer, Integer>> conn = new ArrayList<>();
                    for (int l = 0; l < conSize; l++) {
                        List<Integer> edge = this.getListNumbers(allCon[iterator], " +"); // hrana konektora
                        conn.add(new Pair<>(edge.get(0), edge.get(1)));
                        iterator++;
                    }
                    connectors.add(conn);
                }
                g = g.mergeMultipole(vertices, connectors);
            }
            graphs.add(g);
        }
        return graphs;
    }
}
*/
class SimpleFormatter implements Formatter {

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
                    for (Integer k : this.getListNumbers(l.trim(), " +")) {
                        g.vertices.get(j).addEdge(k, g);
                    }
                } else { // multipol
                    String name = l.split(" +")[l.split(" +").length-1];
                    String[] allCon = String.join(",", Arrays.copyOfRange(l.split(" +"), 0,
                            l.split(" +").length - 1)).split(",");
                    List<List<Integer>> connectors = new ArrayList<>();
                    for (String con : allCon) {
                        List<Integer> cons = this.getListNumbers(con.trim(), " +");
                        connectors.add(cons);
                    }
                    Vertex v = g.vertices.get(j);
                    Multipole m = (Multipole) g.vertices.get(j);
                    m.setConnectors(connectors);
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
        String BA = Export.BAfromModel(graphs);
        Export.FilefromString(BA, "export/tmp.ba");
        // Command.Run("make -C lib/ba-graph/apps/showcutgraph/");
        for (int i = 0; i < numOfGraphs; i++) {
            Command.Run("./lib/ba-graph/apps/showcutgraph/showcutgraph -i export/tmp.ba -p -s 4 -w 1  > export/tmp.gv");
            Command.Run("neato -Tjson0 export/tmp.gv -o export/tmp.dotjson");
            Command.Run("neato -Tpdf export/tmp.gv -o export/tmp.pdf");
            Import.ModelfromJSON(Import.JSONfromfile("export/tmp.dotjson"), graphs.get(i));
        }
        return graphs;
    }
}