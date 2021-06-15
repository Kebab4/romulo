package romulo.format;

import romulo.Command;
import romulo.Utils;
import romulo.graph.Graph;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public interface MBA {
    List<Graph> loadModel(Scanner s) throws java.io.IOException;
    default void cutAndPosition(List<Graph> graphs) {
        if (System.getProperty("os.name").startsWith("Windows"))
            return;
        if (Command.Run("./lib/ba-graph/apps/showcutgraph/showcutgraph -i export/tmp.ba > export/tmp.cut") != 0)
            return;
        try {
            CUT cuts = new CUT(new Scanner(new File("export/tmp.cut")), graphs);
            for (int i = 0; i < graphs.size(); i++) {
                Utils.FilefromString(new DOT(graphs.get(i), cuts.getWeights(i)).toString(), "export/tmp.gv");
                Command.Run("neato -Tjson0 export/tmp.gv -o export/tmp.dotjson");
                Command.Run("neato -Tpdf export/tmp.gv -o export/tmp.pdf");
                new JSON("export/tmp.dotjson").toModel(graphs.get(i));
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}