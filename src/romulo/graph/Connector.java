package romulo.graph;

import java.util.ArrayList;
import java.util.List;

public class Connector {
    List<HalfEdge> edges = new ArrayList<>();
    Multipole parent;
    String color;
    protected Connector(Multipole parent) {
        this.parent = parent;
    }
}
