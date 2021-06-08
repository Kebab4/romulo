package romulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javafx.util.Pair;

public class Graph {
    int size;
    List<List<Integer>> incidence;
    List<Pair<Integer, List<List<Integer>>>> multipoles;

    public Graph(int size, List<List<Integer>> incs, List<Pair<Integer, List<List<Integer>>>> muls) {
        this.size = size;
        this.incidence = incs;
        this.multipoles = muls;
    }

    public void addEdge(int v1, int v2) { // moze byt aj double hrana
        this.incidence.get(v1).add(v2);
        this.incidence.get(v2).add(v1);
    }

    public void print() {
        System.out.println(size);
        for (List<Integer> inc : incidence) {
            System.out.println(inc);
        }
        for (Pair<Integer, List<List<Integer>>> p : multipoles) {
            System.out.println(p.getKey());
            for (List<Integer> inc : p.getValue()) {
                System.out.println(inc);
            }
        }
    }

    private Integer newIndex(List<Integer> vertices, Integer old) {
        int counter = 0;
        for (Integer v : vertices) {
            if (v < old)
                counter++;
            if (v.equals(old))
                return Collections.min(vertices);
        }
        if (counter == 0)
            return old;
        return old - counter + 1;
    }

    public Graph mergeMultipole(List<Integer> vertices, List<List<Pair<Integer, Integer>>> connectors) {

        Integer repre = Collections.min(vertices);
        List<List<Integer>> incs = new ArrayList<>();
        for (int i = 0; i < this.incidence.size(); i++) {
            List<Integer> neighs = new ArrayList<>();
            if (repre.equals(i)) {
                for (Integer vert : vertices) {
                    for (Integer nei : this.incidence.get(vert)) {
                        if (!vertices.contains(nei))
                            neighs.add(newIndex(vertices, nei));
                    }
                }
            } else if (vertices.contains(i)) {
                continue;
            } else {
                for (Integer v : this.incidence.get(i)) {
                    neighs.add(newIndex(vertices, v));
                }
            }
            incs.add(neighs);
        }
        List<Pair<Integer, List<List<Integer>>>> muls = new ArrayList<>();
        for (Pair<Integer, List<List<Integer>>> p : this.multipoles) {
            Integer newId = newIndex(vertices, p.getKey());
            List<List<Integer>> newCons = new ArrayList<>();
            for (List<Integer> Con : p.getValue()) {
                List<Integer> newCon = new ArrayList<>();
                for (Integer i : Con) {
                    newCon.add(newIndex(vertices, i));
                }
                newCons.add(newCon);
            }
            muls.add(new Pair<>(newId, newCons));
        }
        List<List<Integer>> newMultCons = new ArrayList<>();
        for (List<Pair<Integer, Integer>> MulCon : connectors) {
            List<Integer> newMulCon = new ArrayList<>();
            for (Pair<Integer, Integer> p : MulCon) {
                if (vertices.contains(p.getKey()))
                    newMulCon.add(newIndex(vertices, p.getValue()));
                else if (vertices.contains(p.getValue()))
                    newMulCon.add(newIndex(vertices, p.getKey()));
                else
                    System.out.println("boli ma ruka debil");
            }
            newMultCons.add(newMulCon);
        }
        muls.add(new Pair<>(repre, newMultCons));
        return new Graph(size - vertices.size() + 1, incs, muls);
    }
}

