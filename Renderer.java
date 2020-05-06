package sample.graphics;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    List <Renderable> renderables;
    GraphicsContext gc;

    public Renderer(GraphicsContext gc){
        renderables = new ArrayList<Renderable>();
        this.gc = gc;
    }

    public void render(){
        for(Renderable renderable: renderables){
            renderable.getGraphicsObject().render(gc);
        }
    }

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
    }

    public void removeRenderable(Renderable renderable){
        renderables.remove(renderable);
    }
}
