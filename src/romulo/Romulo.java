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
        File myObj = new File("import/graphs/ex2.mba");
        Scanner scan = new Scanner(myObj);
        List<Graph> graphs = new SimpleFormatter().loadModel(scan);

        Graph g = graphs.get(0);

        g.move(200, 150);
        g.setBind();
        //g.unsetBind();
        //graphs.get(0).scale(2);
        primaryStage.setTitle("Okno");
        primaryStage.setScene(new Scene(graphs.get(0), 500, 400));
        System.out.println("vsetky deti " + graphs.get(0).getChildren().size() + " " + graphs.get(0).getChildren());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
