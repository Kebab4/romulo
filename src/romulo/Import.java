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



    public static GraphView ViewfromJSON(JSONObject file, Graph g) {
        GraphView gv = new GraphView(g);
        JSONArray objects = (JSONArray) file.get("objects");
        JSONArray edges = (JSONArray) file.get("edges");

        for (int i = 0; i < objects.size(); i++) {
            JSONObject vertex = (JSONObject) objects.get(i);
            String[] pos = ((String) vertex.get("pos")).split(",");
            if (g.multipoles.stream().anyMatch(n -> (n.getKey().equals(Integer.parseInt(vertex.get("name").toString()))))) {
                gv.addMult(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), 13, Integer.parseInt(vertex.get("name").toString()));
            } else {
                gv.addVertex(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), 10, Integer.parseInt(vertex.get("name").toString()));
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