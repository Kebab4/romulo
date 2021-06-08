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

        String BA = Export.BAfromModel(graphs.get(0));
        Export.FilefromString(BA, "export/tmp.ba");
        Command.Run("make -C lib/ba-graph/apps/showcutgraph/");
        Command.Run("./lib/ba-graph/apps/showcutgraph/showcutgraph -i export/tmp.ba -p  > export/tmp.gv");
        Command.Run("neato -Tjson0 export/tmp.gv -o export/tmp.dotjson");
        Command.Run("neato -Tpdf export/tmp.gv -o export/tmp.pdf");


        primaryStage.setTitle("Okno");
        GraphView g = new GraphView();
        primaryStage.setScene(new Scene(g, 500, 400));
        primaryStage.show();
        JSONObject file = Import.JSONfromfile("export/tmp.dotjson");
        JSONArray objects = (JSONArray) file.get("objects");
        JSONArray edges = (JSONArray) file.get("edges");

        for (int i = 0; i < objects.size(); i++) {
            JSONObject vertex = (JSONObject) objects.get(i);
            String[] pos = ((String) vertex.get("pos")).split(",");
            g.addVertex(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), 10);
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
            g.addEdge(poss, tail, head);
            System.out.println(tail + " " + head);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
