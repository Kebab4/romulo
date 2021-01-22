package romulo;

import romulo.glyph.*;

public interface GlyphVisitor<T> {
    T visit(GlyphVertex component);

    // actions on scene objects
}
