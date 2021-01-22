package romulo;

import javafx.scene.*;
import javafx.scene.layout.*;

public class Session {
    Graph[] graphs;
    Graph current_graph;
    Scene scene;
    Scene scene2;

    Session() {
        this.current_graph = new Graph();
        Pane p = current_graph.pane;

        // naladuj si import grafov

        // manages current stage (and scene)

        // handles top bar, view settings, export all

        this.scene = new Scene(p, 600, 600);
    }
}
