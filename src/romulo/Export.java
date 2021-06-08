package romulo;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class Export {
    public static String BAfromModel(Graph g) {
        String s = "1\n1\n" + g.size + "\n";
        for (List<Integer> l : g.incidence) {
            for (Integer i : l) {
                s += i + " ";
            }
            s += "\n";
        }
        return s;
    }
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
}
