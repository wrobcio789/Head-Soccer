package physics;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import java.util.List;

public class BodyFullDefinition {
    private BodyDef bodyDef;
    private List<FixtureDef> fixtureDefs;

    public BodyFullDefinition(BodyDef bodyDef, List<FixtureDef> fixtureDefs){
        this.bodyDef = bodyDef;
        this.fixtureDefs = fixtureDefs;
    }

    public BodyDef getBodyDef(){
        return bodyDef;
    }

    public List<FixtureDef> getFixtureDefs() {
        return fixtureDefs;
    }
}
