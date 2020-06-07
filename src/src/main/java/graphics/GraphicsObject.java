package graphics;

import javafx.scene.canvas.GraphicsContext;
import org.jbox2d.common.Vec2;

public abstract class GraphicsObject implements Renderable{
    private Vec2 position;
    private float angle;

    public GraphicsObject(Vec2 position) {
        this.position = position;
    }

    public void render(GraphicsContext gc) {}

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vec2 getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    @Override
    public GraphicsObject getGraphicsObject() {
        return this;
    }
}
