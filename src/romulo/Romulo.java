package romulo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import romulo.graph.Graph;

import java.io.File;
import java.util.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.stage.FileChooser;


public class Romulo extends Application {
    Graph graph;
    Stage stage;
    HBox editor;

    void newFile(File f) {
        try {
            Scanner scan = new Scanner(f);
            List<Graph> graphs = new SimpleFormatter().loadModel(scan);

            Graph g = graphs.get(0);

            g.move(200, 150);
            g.setBind();
            this.graph = g;
            System.out.println(this.graph.vertices);
            VBox vbox = new VBox(8);
            vbox.getChildren().addAll(editor, this.graph);
            this.stage.setScene(new Scene(vbox, 500, 400));
        } catch (java.io.IOException e) {
            System.out.println("throwed io exception");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        Button loadFile = new Button("Načítaj súbor");
        loadFile.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Načítaj súbor");
            newFile(fileChooser.showOpenDialog(primaryStage));
        });

        this.editor = new HBox(8);
        this.editor.getChildren().add(loadFile);

        this.graph = new Graph(0);
        //File myObj = new File("import/graphs/ex2.mba");

        //g.unsetBind();
        //graphs.get(0).scale(2);


        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(editor, this.graph);
        primaryStage.setTitle("Okno");
        primaryStage.setScene(new Scene(vbox, 500, 400));
        // System.out.println("vsetky deti " + graphs.get(0).getChildren().size() + " " + graphs.get(0).getChildren());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
