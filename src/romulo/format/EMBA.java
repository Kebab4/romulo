package romulo.format;


import javafx.scene.text.Text;
import romulo.Utils;
import romulo.graph.*;

import java.util.*;

public class EMBA implements MBA {

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
    public List<Graph> loadModel(Scanner scan) {
        // convert format do oldBA
        // get cut format
        // create vertices
        // create groupings and simple version convert
        // iterate and add edges
        // create multipoles

        // convert to neato with attributes
        // json parser set XY // import modeltojson

        // moving scan to file
        StringBuilder inputFile = new StringBuilder();
        while (scan.hasNextLine()) {
            inputFile.append(scan.nextLine()).append("\n");
        }

        // reducing EMBA (extended multipole BA) to BA
        StringBuilder oldBaFormat = new StringBuilder();
        Scanner inputScan = new Scanner(inputFile.toString());
        int numOfGraphs = inputScan.nextInt();
        oldBaFormat.append(numOfGraphs).append("\n");
        List<List<Set<Integer>>> multipoles = new ArrayList<>();
        for (int i = 0; i < numOfGraphs; i++) {
            oldBaFormat.append(inputScan.nextInt()).append("\n"); // index
            int sizeOfGraph = inputScan.nextInt();
            int numOfMultipoles = inputScan.nextInt();
            oldBaFormat.append(sizeOfGraph).append("\n"); // pocet vrcholov
            inputScan.nextLine(); // pocet multipolov a endline
            for (int j = 0; j < sizeOfGraph; j++) {
                oldBaFormat.append(inputScan.nextLine()).append("\n"); // incidence
            }
            List<Set<Integer>> multsOfOne = new ArrayList<>();
            for (int j = 0; j < numOfMultipoles; j++) {
                inputScan.nextLine(); // name
                multsOfOne.add(new HashSet<>(Utils.getListNumbers(inputScan.nextLine().trim(), " +")));  // vertices
                inputScan.nextLine(); // connectors
                inputScan.nextLine(); // edges
            }
            multipoles.add(multsOfOne);
        }
        Utils.FilefromString(oldBaFormat.toString(), "export/tmp.ba");

        // creation of graphs
        List<Graph> graphs = new ArrayList<>();
        inputScan = new Scanner(inputFile.toString());
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
                for (Integer k : Utils.getListNumbers(inputScan.nextLine().trim(), " +")) {
                    if (!reduceId.get(j).equals(reduceId.get(k)) || k > j) // lebo by si vramci multipolu zaratal kazdu hranu dva krat
                        g.vertices.get(reduceId.get(j)).addEdge(reduceId.get(k), g);
                }
            }

            // add multipoles
            for (int j = 0; j < numOfMultipoles; j++) {
                String meno = inputScan.next(); // name
                inputScan.nextLine(); // end of line
                inputScan.nextLine(); // vertices
                List<Integer> conns = Utils.getListNumbers(inputScan.nextLine().trim(), " +"); // connectors
                String[] edges = inputScan.nextLine().trim().split(" *, *"); // edges

                List<List<Integer>> connectors = new ArrayList<>();
                int counter = 0;
                for (Integer connSize : conns) {
                    List<Integer> conn = new ArrayList<>();
                    for (int l = 0; l < connSize; l++) {
                        List<Integer> edge = Utils.getListNumbers(edges[counter].trim(), " +");
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
            graphs.add(g);
        }
        cutAndPosition(graphs);
        return graphs;
    }
}