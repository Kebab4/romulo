package romulo.graph;

import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.TextBoundsType;
public class Text extends javafx.scene.text.Text {
    javafx.scene.text.Text text;
    int font;
    public String meno;

    public Text(String meno, Vertex v) {
        this.meno = meno;
        javafx.scene.text.Text text = new javafx.scene.text.Text(String.valueOf(meno));
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setFont(Font.font ("Verdana", 5));
        this.font = 5;
        text.setFill(Color.YELLOW);
        text.xProperty().bind(v.centerXProperty().add(-text.getLayoutBounds().getWidth()/2).add(v.translateXProperty()));
        text.yProperty().bind(v.centerYProperty().add(text.getLayoutBounds().getHeight()/2).add(v.translateYProperty()));
        text.setTextAlignment(TextAlignment.CENTER);
        System.out.println(text.getX() + " " + text.getY());
        System.out.println(text.getText());
        this.text = text;
        v.g.addText(this);
    }

    public void scale(double times) {
        text.setFont(Font.font ("Verdana", this.font * times));
        this.font *= times;
    }
}
