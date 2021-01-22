package romulo;

import romulo.glyph.*;
import romulo.glyphvisitor.*;
import java.util.*;
import java.io.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.event.*;


import org.json.JSONObject;
import org.json.JSONArray;
import org.json.ReadJSONString;

public class Graph {
    Map<Shape, Glyph> glyphs = new HashMap();
    Pane pane;
    
    Graph() {
        this.pane = new Pane();
        /*
        Glyph v = new GlyphVertex(60,60,50);
        pane.getChildren().add(v.shape);
        glyphs.put(shape, v);
*/
        /*
        shape.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            v.accept(new MoveGlyphVisitor(50,50));
        });
        */
        String resourceName = "../xmls/graph3.dotjson";
        InputStream is = ReadJSONString.class.getResourceAsStream(resourceName);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }

        try (FileReader reader = new FileReader("../../xmls/graph3.dotjson"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONObject graph = (JSONObject) obj;
            JSONArray objects = (JSONArray) graph.getJSONArray("objects");
             
            //Iterate over employee array
            for (int i = 0; i < objects.lenght(); i++) {
                JSONObject vertex = objects.getJSONObject(i);
                String name = vertex.getString("name");
                if (name.substring(0,1) == "%") {
                    continue;
                }
                String pos = vertex.getString("pos");
                String width = vertex.getString("width");
                String[] posarr = pos.split(",");

                Glyph v = new GlyphVertex(Integer.parseInt(posarr[0]),Integer.parseInt(posarr[1]), Integer.parseInt(width));
                pane.getChildren().add(v.shape);
                glyphs.put(shape, v);

            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // manages interaktivity with graph
        // manages export
    }
}
