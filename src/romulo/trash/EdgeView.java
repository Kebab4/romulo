package romulo.trash;

import java.util.*;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.ArcTo;

public class EdgeView extends javafx.scene.shape.Path {
    long v1, v2;

    /*Edge (Vertex v1, Vertex v2) {
        super(v1.getCenterX(), v1.getCenterY(), v2.getCenterX(), v2.getCenterY());
        this.v1 = v1;
        this.v2 = v2;
    }*/
    EdgeView(List<float[]> poss, long v1, long v2) {
        super();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(poss.get(0)[0]);
        moveTo.setY(poss.get(0)[1]);
        this.getElements().add(moveTo);
        System.out.println(poss);
        for (int i = poss.size()-1; i > 1; i--) {
            QuadCurveTo quadTo = new QuadCurveTo();
            quadTo.setControlX(poss.get(i)[0]);
            quadTo.setControlY(poss.get(i)[1]);
            quadTo.setX(poss.get(i-1)[0]);
            quadTo.setY(poss.get(i-1)[1]);
            this.getElements().add(quadTo);
            System.out.println(poss.get(i)[0] + " " +  poss.get(i)[1]);
        }
        this.v1 = v1;
        this.v2 = v2;
    }
}
