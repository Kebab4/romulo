package romulo.graph;

import romulo.Romulo;

import java.util.List;

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
