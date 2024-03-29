package romulo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import romulo.format.EMBA;
import romulo.format.MBA;
import romulo.format.SMBA;
import romulo.graph.Graph;
import romulo.graph.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Romulo extends Application {
    public Stage stage;


    public void newFile(File f, MBA form) {
        try {
            Scanner scan = new Scanner(f);
            List<Graph> graphs = form.loadModel(scan);
            this.setWorkplace(new Session(graphs, this));
        } catch (java.io.IOException e) {
            System.out.println("throwed io exception");
        }
    }

    public void setWorkplace(Session s) {
        Button loadEMBAFile = new Button("Načítaj ExtendedMultipoleBA súbor");
        loadEMBAFile.setOnAction((
                ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Načítaj súbor");
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                this.newFile(f, new EMBA());
            }
        });
        Button loadSMBAFile = new Button("Načítaj SimpleMultipoleBA súbor");
        loadSMBAFile.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Načítaj súbor");
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                this.newFile(f, new SMBA());
            }
        });

        Button changeBind = new Button("Voľnosť hrán");
        changeBind.setOnAction((ActionEvent event) -> s.getActual().changeBind());

        Button prevGraph = new Button("<");
        prevGraph.setOnAction((ActionEvent event) -> s.prevGraph());

        Button nextGraph = new Button(">");
        nextGraph.setOnAction((ActionEvent event) -> s.nextGraph());


        javafx.scene.text.Text idGraph = new javafx.scene.text.Text("Graph ID: " + (s.getPointer() + 1));

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(loadEMBAFile, loadSMBAFile, changeBind, prevGraph, nextGraph, idGraph);

        GridPane gp = new GridPane();
        GridPane.setRowIndex(toolBar, 0);
        GridPane.setRowIndex(s.getActual(), 1);
        gp.getChildren().addAll(toolBar, s.getActual());
        stage.setTitle("Romulo");
        stage.setScene(new Scene(gp, s.getWidth(), s.getHeight()));

        stage.getScene().setOnScroll(e -> {
            toolBar.toFront();
            s.getActual().setTranslateX(s.getActual().getTranslateX() + e.getDeltaX());
            s.getActual().setTranslateY(s.getActual().getTranslateY() + e.getDeltaY());
        });

        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws java.io.IOException {

        Command.Run("make -C lib/ba-graph/apps/showcutgraph/");
        this.stage = primaryStage;
        this.setWorkplace(new Session(new ArrayList<>(), this));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
