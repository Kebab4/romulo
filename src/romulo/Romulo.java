package romulo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Romulo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");
        Graph g = new Graph();
        primaryStage.setScene(new Scene(g, 500, 400));
        primaryStage.show();
        Import file = new Import("export/graph3.dotjson");
        JSONArray objects = (JSONArray) file.graph.get("objects");
        JSONObject sizes = (JSONObject) objects.get(0);
        JSONArray edges = (JSONArray) file.graph.get("edges");

        for (int i = 1; i <= ((JSONArray) sizes.get("nodes")).size(); i++) {
            JSONObject vertex = (JSONObject) objects.get(i);
            String[] pos = ((String) vertex.get("pos")).split(",");
            g.addVertex(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), 10);
            System.out.println(vertex.get("name"));
        }
        for (int i = 0; i < ((JSONArray) sizes.get("edges")).size(); i++) {
            JSONObject edge = (JSONObject) edges.get(i);
            long tail = (long) edge.get("tail");
            long head = (long) edge.get("head");
            String[] pos = ((String) edge.get("pos")).substring(2).split(" ");
            List< float[] > poss = new ArrayList<>();
            for (String s : pos) {
                String[] ssplit = s.split(",");
                float[] longlist = {Float.parseFloat(ssplit[0]), Float.parseFloat(ssplit[1])};
                poss.add(longlist);
                System.out.println(s);
            }
            g.addEdge(poss, tail, head);
            System.out.println(tail + " " + head);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
