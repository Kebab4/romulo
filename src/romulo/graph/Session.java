package romulo.graph;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import romulo.format.EMBA;
import romulo.format.MBA;
import romulo.format.SMBA;
import romulo.Romulo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.*;
import javafx.scene.control.*;
import java.util.Scanner;
import javafx.scene.layout.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.scene.input.*;
import java.util.*;

public class Session {
    List<Graph> graphs;
    Graph actual;
    int pointer;
    Romulo romulo;
    int height, width;

    public Session(List<Graph> graphs, Romulo romulo) {
        this.graphs = graphs;
        if (graphs.size() == 0)
            this.graphs.add(new Graph(0));
        this.romulo = romulo;
        this.actual = graphs.get(0);
        this.pointer = 0;
        this.height = 30 + 600;
        this.width = 800;
    }

    public Graph getActual() {
        return actual;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public void nextGraph() {
        if (pointer < graphs.size() - 1)
            pointer++;
        this.actual = graphs.get(this.pointer);
        this.romulo.setWorkplace(this);
    }
    public void prevGraph() {
        if (pointer >= 1)
            pointer--;
        this.actual = graphs.get(this.pointer);
        this.romulo.setWorkplace(this);
    }
    public int getPointer() {
        return pointer;
    }
}
