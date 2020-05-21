package physics;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

public class BodyFullDefinition {
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    public BodyFullDefinition(BodyDef bodyDef, FixtureDef fixtureDef){
        this.bodyDef = bodyDef;
        this.fixtureDef = fixtureDef;
    }

    public BodyDef getBodyDef(){
        return bodyDef;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }
}
