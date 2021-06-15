package romulo.format;

import javafx.util.Pair;
import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class CUT {
    List<Pair<Integer, List<List<Pair<Integer, Integer>>>>> cuts;
    List<Graph> graphs;

    public CUT(Scanner cutScan, List<Graph> graphs) {
        List<Pair<Integer, List<List<Pair<Integer, Integer>>>>> cuts = new ArrayList<>();
        int numOfGraphs = cutScan.nextInt(); // num of graphs
        for (int i = 0; i < numOfGraphs; i++) {
            int numOfCuts = cutScan.nextInt();
            int smallestCut = cutScan.nextInt();
            cutScan.nextLine();
            List<List<Pair<Integer, Integer>>> allCuts = new ArrayList<>();
            for (int j = 0; j < numOfCuts; j++) {
                String[] tmpCon = cutScan.nextLine().trim().split(" *, *");
                String[] allCon = Arrays.copyOfRange(tmpCon, 0, tmpCon.length - 1);
                List<Pair<Integer, Integer>> oneCut = new ArrayList<>();
                for (String con : allCon) {
                    List<Integer> cons = Utils.getListNumbers(con.trim(), " +");
                    oneCut.add(new Pair<>(cons.get(0), cons.get(1)));
                }
                allCuts.add(oneCut);
            }
            cuts.add(new Pair<>(smallestCut, allCuts));
        }
        this.cuts = cuts;
        this.graphs = graphs;
    }
    public List<Pair<Edge, Integer>> getWeights(int index) {
        int SILA = 2; // TODO nastav modulovatelne
        List<Pair<Edge, Integer>> weights = new ArrayList<>();
        for (List<Pair<Integer, Integer>> cut : this.cuts.get(index).getValue()) { // List<Pair<Integer, List<List<Pair<Integer, Integer>>>>>
            if (cut.size() > this.cuts.get(index).getKey())
                continue;
            for (Pair<Integer, Integer> edge : cut) {
                for (Edge e : this.graphs.get(index).edges) {
                    if ((e.e1.v.id == edge.getKey() && e.e2.v.id == edge.getValue()) || (
                            e.e2.v.id == edge.getKey() && e.e1.v.id == edge.getValue())) {
                        weights.add(new Pair<>(e, SILA));
                    }
                }
            }
        }
        return weights;
    }
    public List<Pair<Integer, List<List<Pair<Integer, Integer>>>>> getCuts() {
        return this.cuts;
    }
}