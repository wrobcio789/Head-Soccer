package physics;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;

public class BodyFullDefinition {
    private BodyDef bodyDef;
    private Shape shape;
    private float density;

    public BodyFullDefinition(BodyDef bodyDef, Shape shape, float density){
        this.bodyDef = bodyDef;
        this.shape = shape;
        this.density = density;
    }

    public BodyDef getBodyDef(){
        return bodyDef;
    }

    public Shape getShape() {
        return shape;
    }

    public float getDensity() {
        return density;
    }
}
