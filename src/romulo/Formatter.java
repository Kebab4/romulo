package romulo;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public interface Formatter {
    public List<Graph> loadModel(Scanner s);
    default List<Integer> getListNumbers(String s, String regexSep) {
        List<Integer> list = new ArrayList<>();
        for (String field : s.split(regexSep)) { // susedia vrchola
            list.add(Integer.parseInt(field));
        }
        return list;
    }
}

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

     */
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