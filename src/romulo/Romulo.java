package romulo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import romulo.graph.Graph;

import java.io.File;
import java.util.*;


public class Romulo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        File myObj = new File("import/graph.mba");
        Scanner scan = new Scanner(myObj);
        List<Graph> graphs = new SimpleFormatter().loadModel(scan);

        String BA = Export.BAfromModel(graphs.get(0));
        Export.FilefromString(BA, "export/tmp.ba");
        // Command.Run("make -C lib/ba-graph/apps/showcutgraph/");
        Command.Run("./lib/ba-graph/apps/showcutgraph/showcutgraph -i export/tmp.ba -p -s 1 -w 1  > export/tmp.gv");
        Command.Run("neato -Tjson0 export/tmp.gv -o export/tmp.dotjson");
        Command.Run("neato -Tpdf export/tmp.gv -o export/tmp.pdf");


        Import.ModelfromJSON(Import.JSONfromfile("export/tmp.dotjson"), graphs.get(0));
        graphs.get(0).move(100, 100);
        graphs.get(0).scale(1.5);
        primaryStage.setTitle("Okno");
        primaryStage.setScene(new Scene(graphs.get(0), 500, 400));
        System.out.println("vsetky deti " + graphs.get(0).getChildren().size() + " " + graphs.get(0).getChildren());
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
