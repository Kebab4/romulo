package romulo;

import java.util.List;
import java.util.Set;

public class Multipole {
    private List<List<Integer>> connectors;
    public Multipole(Set<Integer> vertices, List<List<Integer>> connectors) {
        this.connectors = connectors;
    }
}