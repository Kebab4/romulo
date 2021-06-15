package romulo.format;

import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import romulo.graph.Edge;
import romulo.graph.Graph;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSON {

    /*
    FORMAT
    {
        ...
        objects: [name, pos]
        edges: [head, tail, pos]
    }
    */

    JSONObject graph;
    public JSON(String filename) {
        JSONParser jsonParser = new JSONParser();
        JSONObject graph = new JSONObject();

        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            graph = (JSONObject) obj;
            System.out.println("imported " + filename);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        this.graph = graph;
    }
    public JSONObject getJSON() {
        return graph;
    }

    public void toModel(Graph g) {
        int zoom = 3;
        JSONArray objects = (JSONArray) this.graph.get("objects");
        JSONArray edges = (JSONArray) this.graph.get("edges");

        for (Object object : objects) {
            JSONObject vertex = (JSONObject) object;
            String[] pos = ((String) vertex.get("pos")).split(",");
            g.vertices.get(Integer.parseInt(vertex.get("name").toString())).setXY(
                    Double.parseDouble(pos[0]) * zoom, Double.parseDouble(pos[1]) * zoom);
        }
        List<Pair<Integer, Integer>> addedEdges = new ArrayList<>();
        for (Object o : edges) {
            JSONObject edge = (JSONObject) o;
            int tail = Integer.parseInt(((JSONObject) objects.get(((Long) edge.get("tail")).intValue())).get("name").toString());
            int head = Integer.parseInt(((JSONObject) objects.get(((Long) edge.get("head")).intValue())).get("name").toString());
            String[] pos = ((String) edge.get("pos")).split(" ");
            List<float[]> poss = new ArrayList<>();
            double aveX = 0, aveY = 0;
            for (String s : pos) {
                String[] ssplit = s.split(",");
                float[] longlist = {Float.parseFloat(ssplit[0]) * zoom, Float.parseFloat(ssplit[1]) * zoom};
                poss.add(longlist);
                aveX += longlist[0];
                aveY += longlist[1];
            }
            int duplicates = 0;
            for (Pair<Integer, Integer> e : addedEdges) {
                if ((e.getKey() == tail && e.getValue() == head) || (
                        e.getKey() == head && e.getValue() == tail)) {
                    duplicates++;
                }
            }
            addedEdges.add(new Pair<>(tail, head));
            for (Edge e : g.edges) {
                if ((e.e1.v.id == tail && e.e2.v.id == head) || (
                        e.e1.v.id == head && e.e2.v.id == tail)) {
                    if (duplicates != 0) {
                        duplicates--;
                    } else {
                        e.middle.setXY(aveX / pos.length, aveY / pos.length);
                        break;
                    }
                }
            }
        }
    }
}