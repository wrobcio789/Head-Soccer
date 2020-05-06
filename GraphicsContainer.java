package sample.graphics;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class GraphicsContainer extends GraphicsObject{
    List<GraphicsObject> graphicsObjects;
    GraphicsContainer(List<GraphicsObject> graphicsObjectsInput){
        graphicsObjects = new ArrayList<GraphicsObject>();
        for(GraphicsObject graphicObject: graphicsObjectsInput){
            this.graphicsObjects.add(graphicObject);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for(GraphicsObject graphicObject: graphicsObjects){
            graphicObject.render(gc);
        }
    }
}