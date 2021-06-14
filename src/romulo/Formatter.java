package romulo;

import javafx.scene.paint.Color;
import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.graph.Multipole;
import romulo.graph.Vertex;
import romulo.graph.Point;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

import java.util.*;

public interface Formatter {
    List<Graph> loadModel(Scanner s) throws java.io.IOException;
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
            name // name of multipole
            i j k l... // list of vertices in multipole
            r s t u... // number of connectors in connector
            a b, c d, e f... // r-times, s-times, t-times edges as vertex vertex, vertex vertex...
    */

    @Override
    public List<Graph> loadModel(Scanner scan) throws java.io.IOException {
        // convert format do oldBA
        // get cut format
        // create vertices
        // create groupings and simple version convert
        // iterate and add edges
        // create multipoles

        // convert to neato with attributes
        // json parser set XY // import modeltojson

        // moving scan to file
        String inputFile = "";
        while (scan.hasNextLine()) {
            inputFile += scan.nextLine() + "\n";
        }

        // reducing EMBA (extended multipole BA) to BA
        String oldBaFormat = "";
        Scanner inputScan = new Scanner(inputFile);
        int numOfGraphs = inputScan.nextInt();
        oldBaFormat += numOfGraphs + "\n";
        List<List<Set<Integer>>> multipoles = new ArrayList<>();
        for (int i = 0; i < numOfGraphs; i++) {
            oldBaFormat += inputScan.nextInt() + "\n"; // index
            int sizeOfGraph = inputScan.nextInt();
            int numOfMultipoles = inputScan.nextInt();
            oldBaFormat += sizeOfGraph + "\n"; // pocet vrcholov
            inputScan.nextLine(); // pocet multipolov a endline
            for (int j = 0; j < sizeOfGraph; j++) {
                oldBaFormat += inputScan.nextLine() + "\n"; // incidence
            }
            List<Set<Integer>> multsOfOne = new ArrayList<>();
            for (int j = 0; j < numOfMultipoles; j++) {
                inputScan.nextLine(); // name
                multsOfOne.add(new HashSet<>(this.getListNumbers(inputScan.nextLine().trim(), " +")));  // vertices
                inputScan.nextLine(); // connectors
                inputScan.nextLine(); // edges
            }
            multipoles.add(multsOfOne);
        }
        Export.FilefromString(oldBaFormat, "export/tmp.ba");
        //Command.Run("make -C lib/ba-graph/apps/showcutgraph/");
        Command.Run("./lib/ba-graph/apps/showcutgraph/showcutgraph -i export/tmp.ba > export/tmp.cut");
        Scanner cutScan = new Scanner(new File("export/tmp.cut"));

        // parsing cut file
        List<Pair<Integer, List<List<Pair<Integer, Integer>>>>> cuts = new ArrayList<>();
        cutScan.nextInt(); // num of graphs
        for (int i = 0; i < numOfGraphs; i++) {
            int numOfCuts = cutScan.nextInt(); // num of cuts
            int smallestCut = cutScan.nextInt();
            cutScan.nextLine();
            List<List<Pair<Integer, Integer>>> allCuts = new ArrayList<>();
            for (int j = 0; j < numOfCuts; j++) {
                String[] allCon = cutScan.nextLine().trim().split(" *, *");
                List<Pair<Integer, Integer>> oneCut = new ArrayList<>();

                for (String con : allCon) {
                    List<Integer> cons = this.getListNumbers(con.trim(), " +");
                    oneCut.add(new Pair<>(cons.get(0), cons.get(1)));
                }
                allCuts.add(oneCut);
            }
            cuts.add(new Pair<>(smallestCut, allCuts));
        }

        // Print of cut file
        System.out.println(cuts);
        for (Pair<Integer, List<List<Pair<Integer, Integer>>>> graph : cuts) {
            System.out.println(graph.getKey() + " " + graph.getValue().size() + "\n");
            for (List<Pair<Integer, Integer>> cut : graph.getValue()) {
                for (Pair<Integer, Integer> edge : cut) {
                    System.out.print(edge.getKey() + " " + edge.getValue() + ", ");
                }
                System.out.print("\n");
            }
        }

        // creation of graphs
        List<Graph> graphs = new ArrayList<>();
        inputScan = new Scanner(inputFile);
        inputScan.nextLine(); // num of graphs line
        for (int i = 0; i < numOfGraphs; i++) {
            inputScan.nextLine(); // graph num
            int sizeOfGraph = inputScan.nextInt(); // pocet vrcholov
            int numOfMultipoles = inputScan.nextInt(); // pocet multipolov
            int graphsize = sizeOfGraph;

            // reduce function
            Map<Integer, Integer> reduceId = new HashMap<>(); // Id in new graph
            Set<Integer> multIds = new HashSet<>();
            for (Set<Integer> mult : multipoles.get(i)) {
                for (Integer k : mult) {
                    reduceId.put(k, Collections.min(mult));
                }
                graphsize -= mult.size() - 1;
                multIds.add(Collections.min(mult));
            }
            int posun = 0;
            for (int j = 0; j < sizeOfGraph; j++) {
                if (!reduceId.containsKey(j)) { // normalny vrchol
                    reduceId.put(j, j - posun);
                } else if (multIds.contains(j)) { // vrchol multipolu
                    reduceId.put(j, reduceId.get(j) - posun);
                    multIds.remove(j);
                    multIds.add(reduceId.get(j));
                } else { // zredukovany vrchol
                    reduceId.put(j, reduceId.get(j) - posun);
                    posun++;
                }
            }
            Graph g = new Graph(graphsize);

            // add vertices
            for (int j = 0; j < graphsize; j++) {
                if (multIds.contains(j)) {
                    g.addMultipole(j, "None");
                } else {
                    g.addVertex(j);
                }
            }

            // add edges
            inputScan.nextLine(); // endline
            for (int j = 0; j < sizeOfGraph; j++) {
                for (Integer k : this.getListNumbers(inputScan.nextLine().trim(), " +")) {
                    if (!reduceId.get(j).equals(reduceId.get(k)) || k > j) // lebo by si vramci multipolu zaratal kazdu hranu dva krat
                        g.vertices.get(reduceId.get(j)).addEdge(reduceId.get(k), g);
                }
            }

            // add multipoles
            for (int j = 0; j < numOfMultipoles; j++) {
                String meno = inputScan.next(); // name
                inputScan.nextLine(); // end of line
                inputScan.nextLine(); // vertices
                List<Integer> conns = this.getListNumbers(inputScan.nextLine().trim(), " +"); // connectors
                String[] edges = inputScan.nextLine().trim().split(" *, *"); // edges

                List<List<Integer>> connectors = new ArrayList<>();
                int counter = 0;
                for (int k = 0; k < conns.size(); k++) {
                    List<Integer> conn = new ArrayList<>();
                    for (int l = 0; l < conns.get(k); l++) {
                        List<Integer> edge = this.getListNumbers(edges[counter].trim(), " +");
                        if (multipoles.get(i).get(j).contains(edge.get(0))) {
                            conn.add(edge.get(1));
                        } else {
                            conn.add(edge.get(0));
                        }
                        counter++;
                    }
                    connectors.add(conn);
                }
                int indexOfMultipole = reduceId.get(Collections.min(multipoles.get(i).get(j)));
                Multipole m = (Multipole) g.vertices.get(indexOfMultipole);
                m.setConnectors(connectors);
                m.setType(meno);
            }
            Export.FilefromString(Export.DotFromModel(g), "export/tmp.gv");
            Command.Run("neato -Tjson0 export/tmp.gv -o export/tmp.dotjson");
            Command.Run("neato -Tpdf export/tmp.gv -o export/tmp.pdf");
            Import.ModelfromJSON(Import.JSONfromfile("export/tmp.dotjson"), g);
            graphs.add(g);
            for (Vertex v : g.vertices) {
                v.toFront();
            }
            for (Point p : g.points) {
                p.toFront();
            }
            for (Text t : g.texts) {
                t.toFront();
            }
        }
        return graphs;
    }
}

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
    public List<Graph> loadModel(Scanner scan) throws java.io.IOException {

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
            Command.Run("./lib/ba-graph/apps/showcutgraph/showcutgraph -i export/tmp.ba > export/tmp.cut");
            Export.FilefromString(Export.DotFromModel(graphs.get(i)), "export/tmp.gv");
            Command.Run("neato -Tjson0 export/tmp.gv -o export/tmp.dotjson");
            Command.Run("neato -Tpdf export/tmp.gv -o export/tmp.pdf");
            Import.ModelfromJSON(Import.JSONfromfile("export/tmp.dotjson"), graphs.get(i));
        }
        return graphs;
    }
}