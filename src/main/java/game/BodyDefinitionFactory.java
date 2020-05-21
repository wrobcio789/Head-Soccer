package game;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import physics.BodyFullDefinition;

public class BodyDefinitionFactory {

    public static BodyFullDefinition createBallDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(0.03f, 2.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = new CircleShape();
        fixtureDef.shape.m_radius = 0.5f;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;

        return new BodyFullDefinition(bodyDef, fixtureDef);
    }

    public static BodyFullDefinition createGroundDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vec2(-30.0f, Constants.GROUND_LEVEL), new Vec2(30.0f, Constants.GROUND_LEVEL));
        fixtureDef.shape = edgeShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.2f;

        return new BodyFullDefinition(bodyDef, fixtureDef);
    }

    public static BodyFullDefinition createGoalDefinition(boolean Side){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(0.5f, 1.0f);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(1.0f, 0.5f);
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;

        return new BodyFullDefinition(bodyDef, fixtureDef);
    }

    public static BodyFullDefinition createPlayerDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(3.0f, 0.2f);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(1.0f, 0.5f);
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;

        return new BodyFullDefinition(bodyDef, fixtureDef);
    }
}
