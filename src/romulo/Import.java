package romulo;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.util.Pair;

import java.io.*;

public class Import {
    public static JSONObject JSONfromfile(String filename) {
        JSONParser jsonParser = new JSONParser();
        JSONObject graph = new JSONObject();

        try (FileReader reader = new FileReader(filename))
        {
            Object obj = jsonParser.parse(reader);
            graph = (JSONObject) obj;
            System.out.println("imported " + filename);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return graph;
    }

    private static List<Integer> getLineNumbers(String s) {
        List<Integer> list = new ArrayList<>();
        for (String field : s.split(" +")) { // susedia vrchola
            list.add(Integer.parseInt(field));
        }
        return list;
    }

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

    public static List<Graph> ModelfromBA(String filename) {
        List<Graph> graphs = new ArrayList<>();
        try {
            File myObj = new File(filename);
            Scanner scan = new Scanner(myObj);
            int numOfGraphs = scan.nextInt();
            for (int i = 0; i < numOfGraphs; i++) {
                int indexNum = scan.nextInt();
                int sizeOfGraph = scan.nextInt(); // pocet vrcholov
                int numOfMultipoles = scan.nextInt(); // pocet multipolov
                scan.nextLine(); // because of problems with endline
                List<List<Integer>> incidence = new ArrayList<>();
                for (int j = 0; j < sizeOfGraph; j++) {
                    incidence.add(getLineNumbers(scan.nextLine())); // susedia vrchola
                }
                Graph g = new Graph(sizeOfGraph, incidence, new ArrayList<>());
                for (int j = 0; j < numOfMultipoles; j++) {
                    List<Integer> vertices = getLineNumbers(scan.nextLine()); // vrcholy multipolu
                    List<Integer> conSizes = getLineNumbers(scan.nextLine()); // pocty konektorov
                    List<List<Pair<Integer, Integer>>> connectors = new ArrayList<>();
                    String[] allCon = scan.nextLine().split(",");
                    int iterator = 0;
                    for (Integer conSize : conSizes) {
                        List<Pair<Integer, Integer>> conn = new ArrayList<>();
                        for (int l = 0; l < conSize; l++) {
                            List<Integer> edge = getLineNumbers(allCon[iterator]); // hrana konektora
                            conn.add(new Pair<>(edge.get(0), edge.get(1)));
                            iterator++;
                        }
                        connectors.add(conn);
                    }
                    g = g.mergeMultipole(vertices, connectors);
                }
                graphs.add(g);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return graphs;
    }

    public static GraphView ViewfromJSON(JSONObject file, Graph g) {
        GraphView gv = new GraphView();
        JSONArray objects = (JSONArray) file.get("objects");
        JSONArray edges = (JSONArray) file.get("edges");

        for (int i = 0; i < objects.size(); i++) {
            JSONObject vertex = (JSONObject) objects.get(i);
            String[] pos = ((String) vertex.get("pos")).split(",");
            if (g.multipoles.stream().anyMatch(n -> (n.getKey().equals(Integer.parseInt(vertex.get("name").toString()))))) {
                gv.addMult(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), 13);
            } else {
                gv.addVertex(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), 10);
            }
            System.out.println(vertex.get("name"));
        }
        for (int i = 0; i < edges.size(); i++) {
            JSONObject edge = (JSONObject) edges.get(i);
            long tail = (long) edge.get("tail");
            long head = (long) edge.get("head");
            String[] pos = ((String) edge.get("pos")).split(" ");
            List<float[]> poss = new ArrayList<>();
            for (String s : pos) {
                String[] ssplit = s.split(",");
                float[] longlist = {Float.parseFloat(ssplit[0]), Float.parseFloat(ssplit[1])};
                poss.add(longlist);
                System.out.println(s);
            }
            gv.addEdge(poss, tail, head);
            System.out.println(tail + " " + head);
        }
        return gv;
    }
}