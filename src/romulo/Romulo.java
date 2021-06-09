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

        List<Graph> graphs = Import.ModelfromBA("import/graph.mba");

        String BA = Export.BAfromModel(graphs.get(0));
        Export.FilefromString(BA, "export/tmp.ba");
        // Command.Run("make -C lib/ba-graph/apps/showcutgraph/");
        Command.Run("./lib/ba-graph/apps/showcutgraph/showcutgraph -i export/tmp.ba -p  > export/tmp.gv");
        Command.Run("neato -Tjson0 export/tmp.gv -o export/tmp.dotjson");
        Command.Run("neato -Tpdf export/tmp.gv -o export/tmp.pdf");


        GraphView g = Import.ViewfromJSON(Import.JSONfromfile("export/tmp.dotjson"), graphs.get(0));
        g.move(200, 200);
        g.scale(1.5);
        primaryStage.setTitle("Okno");
        primaryStage.setScene(new Scene(g, 500, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
