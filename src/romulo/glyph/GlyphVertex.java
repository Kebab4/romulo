package romulo.glyph;

import romulo.*;
import javafx.scene.shape.*;

public class GlyphVertex implements Glyph {
    public Circle shape;
    private int x, y, d;

    public GlyphVertex(int x, int y, int d) {
        this.shape = new Circle(x, y, d);
        this.x = x;
        this.y = y;
        this.d = d;
    }

    @Override
    public <T> T accept(GlyphVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
