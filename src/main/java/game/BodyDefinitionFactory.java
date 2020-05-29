package game;

import com.sun.javafx.geom.Edge;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import physics.BodyFullDefinition;

import java.util.LinkedList;
import java.util.List;

public class BodyDefinitionFactory {

    public static BodyFullDefinition createBallDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(Constants.BALL_POS_X, Constants.BALL_POS_Y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = new CircleShape();
        fixtureDef.shape.m_radius = Constants.BALL_RADIUS;
        fixtureDef.density = Constants.BALL_DENSITY;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.7f;
        fixtureDef.userData = "Ball";

        List<FixtureDef> fixtureDefs = new LinkedList<>();
        fixtureDefs.add(fixtureDef);

        return new BodyFullDefinition(bodyDef, fixtureDefs);
    }

    public static BodyFullDefinition createGroundDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vec2(-30.0f, Constants.GROUND_LEVEL), new Vec2(30.0f, Constants.GROUND_LEVEL));
        fixtureDef.shape = edgeShape;
        fixtureDef.friction = 0.2f;

        List<FixtureDef> fixtureDefs = new LinkedList<>();
        fixtureDefs.add(fixtureDef);

        return new BodyFullDefinition(bodyDef, fixtureDefs);
    }

    public static BodyFullDefinition createWallDefinition(int side){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();

        if(side == Constants.LEFT_SIDE)
            edgeShape.set(new Vec2(Constants.LEFT_WALL_X, -10.0f), new Vec2(Constants.LEFT_WALL_X, 10.0f));
        else if(side == Constants.RIGHT_SIDE)
            edgeShape.set(new Vec2(Constants.RIGHT_WALL_X, -10.0f), new Vec2(Constants.RIGHT_WALL_X, 10.0f));

        fixtureDef.shape = edgeShape;
        fixtureDef.friction = 0.2f;
        List<FixtureDef> fixtureDefs = new LinkedList<>();
        fixtureDefs.add(fixtureDef);

        return new BodyFullDefinition(bodyDef, fixtureDefs);
    }

    public static BodyFullDefinition createRoofDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vec2(-30.0f, Constants.ROOF_LEVEL), new Vec2(30.0f, Constants.ROOF_LEVEL));
        fixtureDef.shape = edgeShape;
        fixtureDef.friction = 0.2f;

        List<FixtureDef> fixtureDefs = new LinkedList<>();
        fixtureDefs.add(fixtureDef);

        return new BodyFullDefinition(bodyDef, fixtureDefs);
    }

    public static BodyFullDefinition createGoalDefinition(int side){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        if(side == Constants.LEFT_SIDE)
            bodyDef.position.set(Constants.GOAL_LEFT_POS_X, Constants.GOAL_POS_Y);
        else if (side == Constants.RIGHT_SIDE)
            bodyDef.position.set(Constants.GOAL_RIGHT_POS_X, Constants.GOAL_POS_Y);

        FixtureDef crossbarFixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(Constants.GOAL_WIDTH/2.0f, Constants.GOAL_CROSSBAR_HEIGHT/2.0f);
        crossbarFixtureDef.shape = boxShape;

        FixtureDef goalAreaFixtureDef = new FixtureDef();
        PolygonShape areaShape = new PolygonShape();
        areaShape.setAsBox(Constants.GOAL_AREA_WIDTH/2.0f, Constants.GOAL_AREA_HEIGHT/2.0f);
        for (Vec2 vertex : areaShape.getVertices())
            vertex.addLocal(Constants.GOAL_AREA_MARGIN/2.0f, -Constants.GOAL_AREA_HEIGHT/2.0f);
        goalAreaFixtureDef.shape = areaShape;
        goalAreaFixtureDef.isSensor = true;
        goalAreaFixtureDef.userData = "Goal" + side;

        List<FixtureDef> fixtureDefs = new LinkedList<>();
        fixtureDefs.add(goalAreaFixtureDef);
        fixtureDefs.add(crossbarFixtureDef);

        return new BodyFullDefinition(bodyDef, fixtureDefs);
    }

    public static BodyFullDefinition createPlayerDefinition(int side){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        if(side == Constants.LEFT_SIDE)
            bodyDef.position.set(Constants.PLAYER_LEFT_POS_X, Constants.PLAYER_POS_Y);
        else if (side == Constants.RIGHT_SIDE)
            bodyDef.position.set(Constants.PLAYER_RIGHT_POS_X, Constants.PLAYER_POS_Y);
        bodyDef.fixedRotation = true;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Constants.PLAYER_RADIUS);
        fixtureDef.shape = circleShape;
        fixtureDef.density = Constants.PLAYER_DENSITY;
        fixtureDef.userData = "Player" + side;

        List<FixtureDef> fixtureDefs = new LinkedList<>();
        fixtureDefs.add(fixtureDef);

        return new BodyFullDefinition(bodyDef, fixtureDefs);
    }
}
