import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.*;
// compile & run: export PATHFX=../../javafx-sdk-11.0.2/lib && javac --module-path $PATHFX --add-modules javafx.controls HelloFX.java && java --module-path $PATHFX --add-modules javafx.controls HelloFX
// dot -Tjson0 ../../import/graph3.gv -o ../../export/graph3.dotjson


public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // ProcessBuilder pb = new ProcessBuilder("ls");
        // pb.inheritIO();
        // pb.directory(new File("bin"));
        // pb.start();


        /* Process p = */ Runtime.getRuntime().exec("dot -Tjson0 ../../import/graph3.gv -o ../../export/graph3.dotjson");

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        // Scene scene = new Scene(new StackPane(l), 640, 480);

        Pane pane = new Pane();
        Shape shape = new Rectangle(60, 60, 50, 50);
        pane.getChildren().add(shape);

        Scene scene = new Scene(pane, 600, 600);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
