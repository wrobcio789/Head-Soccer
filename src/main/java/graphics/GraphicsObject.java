package graphics;

import javafx.scene.canvas.GraphicsContext;
import org.jbox2d.common.Vec2;

public abstract class GraphicsObject {
    private Vec2 position;

    public GraphicsObject(Vec2 position) {
        this.position = position;
    }

    public void render(GraphicsContext gc) {}

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public Vec2 getPosition() {
        return position;
    }
}
