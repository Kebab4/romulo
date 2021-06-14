package romulo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import romulo.graph.Graph;

import java.io.File;
import java.text.Normalizer;
import java.util.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.stage.FileChooser;


public class Romulo extends Application {
    List<Graph> graphs;
    Graph actual;
    Stage stage;
    HBox editor;

    void newFile(File f, Formatter form) {
        try {
            Scanner scan = new Scanner(f);
            List<Graph> graphs = form.loadModel(scan);
            this.graphs = graphs;
            Graph g = graphs.get(0);

            g.move(200, 150);
            g.changeBind();
            System.out.println(g.vertices);
            VBox vbox = new VBox(8);
            vbox.getChildren().addAll(editor, g);
            this.actual = g;
            this.stage.setScene(new Scene(vbox, 500, 400));
        } catch (java.io.IOException e) {
            System.out.println("throwed io exception");
        }
    }

    @Override
    public void start(Stage primaryStage) throws java.io.IOException {
        this.stage = primaryStage;

        Button loadEMBAFile = new Button("Načítaj ExtendedMultipoleBA súbor");
        loadEMBAFile.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Načítaj súbor");
            File f = fileChooser.showOpenDialog(primaryStage);
            if (f != null) {
                newFile(f, new ExtendedFormatter());
            }
        });
        Button loadSMBAFile = new Button("Načítaj SimpleMultipoleBA súbor");
        loadSMBAFile.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Načítaj súbor");
            File f = fileChooser.showOpenDialog(primaryStage);
            if (f != null) {
                newFile(f, new SimpleFormatter());
            }
        });

        Button changeBind = new Button("Voľnosť hrán");
        changeBind.setOnAction((ActionEvent event) -> {
            this.actual.changeBind();
        });

        this.editor = new HBox(8);
        this.editor.getChildren().addAll(loadEMBAFile, loadSMBAFile, changeBind);

        newFile(new File("import/mults.emba"),  new ExtendedFormatter());
        //File myObj = new File("import/graphs/ex2.mba");

        //g.unsetBind();
        //graphs.get(0).scale(2);


        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(editor, new Graph(0));
        primaryStage.setTitle("Okno");
        primaryStage.setScene(new Scene(vbox, 500, 400));
        // System.out.println("vsetky deti " + graphs.get(0).getChildren().size() + " " + graphs.get(0).getChildren());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
