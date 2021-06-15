package romulo;

import javafx.util.Pair;
import romulo.graph.Edge;
import romulo.graph.Graph;
import romulo.graph.Vertex;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void FilefromString(String s, String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(s);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static List<Integer> getListNumbers(String s, String regexSep) {
        List<java.lang.Integer> list = new ArrayList<>();
        for (String field : s.split(regexSep)) { // susedia vrchola
            list.add(java.lang.Integer.parseInt(field));
        }
        return list;
    }
}

