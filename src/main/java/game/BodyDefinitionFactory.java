package game;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import physics.BodyFullDefinition;

public class BodyDefinitionFactory {

    public static BodyFullDefinition createBallDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(0.03f, 2.5f);

        Shape shape = new CircleShape();
        shape.m_radius = 0.5f;

        float density = 0.1f;
        return new BodyFullDefinition(bodyDef, shape, density);
    }

    public static BodyFullDefinition createGroundDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vec2(-30.0f, 0.2f), new Vec2(30.0f, 0.2f));

        float density = 1.0f;
        return new BodyFullDefinition(bodyDef, edgeShape, density);
    }
}
