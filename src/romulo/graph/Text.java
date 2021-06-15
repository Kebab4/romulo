package romulo.graph;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.TextBoundsType;
public class Text extends javafx.scene.text.Text {
    int font;
    public Vertex parent;

    public Text(String meno, Vertex v) {
        this.setText(meno);
        this.parent = v;
        this.setBoundsType(TextBoundsType.VISUAL);
        this.font = 8;
        this.setFont(Font.font ("Verdana", this.font));
        this.setFill(Color.BLACK);
        this.xProperty().bind(v.centerXProperty().add(-this.getLayoutBounds().getWidth()/2).add(v.translateXProperty()));
        this.yProperty().bind(v.centerYProperty().add(this.getLayoutBounds().getHeight()/2).add(v.translateYProperty()));
        this.setTextAlignment(TextAlignment.CENTER);
        this.setCursor(Cursor.HAND);
        this.setOnMousePressed(v.circleOnMousePressedEventHandler);
        this.setOnMouseDragged(v.circleOnMouseDraggedEventHandler);
    }

    public void scale(double times) {
        this.setFont(Font.font ("Verdana", this.font * times));
        this.font *= times;
    }
}
