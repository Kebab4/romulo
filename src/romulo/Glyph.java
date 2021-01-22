package romulo;

public interface Glyph {
    <T> T accept(GlyphVisitor<T> visitor);

    // scene objects
}
