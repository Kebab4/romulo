package romulo;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;

// compile & run: export PATHFX=../javafx-sdk-11.0.2/lib && javac --module-path $PATHFX --add-modules javafx.controls romulo/Romulo.java romulo/Graph.java && java --module-path $PATHFX --add-modules javafx.controls romulo.Romulo
// dot -Tjson0 ../../import/graph3.gv -o ../../export/graph3.dotjson

public class Romulo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // ProcessBuilder pb = new ProcessBuilder("ls");
        // pb.inheritIO();
        // pb.directory(new File("bin"));
        // pb.start();


        /* Process p = */ Runtime.getRuntime().exec("dot -Tjson0 ../import/graph3.gv -o ../xmls/graph3.dotjson");

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        // Scene scene = new Scene(new StackPane(l), 640, 480);

        Session s = new Session();
        stage.setScene(s.scene);
        stage.show();

        // manages new session (pass Stage)

        // handles global settings


    }

    public static void main(String[] args) {
        launch();
    }
}
