package romulo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.*;

public class Romulo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        List<Graph> graphs = Import.ModelfromFile("import/graph.mba");
        for (Graph g : graphs)
            g.print();

/*
        primaryStage.setTitle("Hello World");
        GraphView g = new GraphView();
        primaryStage.setScene(new Scene(g, 500, 400));
        primaryStage.show();
        JSONObject file = Import.JSONfromfile("import/graph3.dotjson");
        JSONArray objects = (JSONArray) file.get("objects");
        JSONObject sizes = (JSONObject) objects.get(0);
        JSONArray edges = (JSONArray) file.get("edges");

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
        }*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
