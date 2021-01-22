package romulo.glyphvisitor;

import romulo.*;
import romulo.glyph.*;

public class MoveGlyphVisitor implements GlyphVisitor<Void> {
    private int moveX, moveY;

    public MoveGlyphVisitor(int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    @Override
    public Void visit(GlyphVertex component) {
        component.shape.setCenterX(component.shape.getCenterX() + this.moveX);
        component.shape.setCenterY(component.shape.getCenterY() + this.moveY);
        return null;
    }
}
