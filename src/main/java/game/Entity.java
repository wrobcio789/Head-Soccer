package game;

import graphics.GraphicsObject;
import graphics.Renderable;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import physics.BodyFullDefinition;
import physics.PhysicsObject;


public class Entity implements Renderable, PhysicsObject {
    private Body physicsBody;
    private BodyFullDefinition bodyFullDefinition;
    private GraphicsObject graphicsObject;

    public Entity(BodyFullDefinition bodyFullDefinition, GraphicsObject graphicsObject){
        this.bodyFullDefinition = bodyFullDefinition;
        this.graphicsObject = graphicsObject;
    }

    public Vec2 getPosition() {
        return physicsBody.getPosition();
    }

    @Override
    public void CreateBody(World world){
        physicsBody = world.createBody(bodyFullDefinition.getBodyDef());
        physicsBody.createFixture(bodyFullDefinition.getFixtureDef());
    }

    @Override
    public void DestroyBody(World world){
        world.destroyBody(physicsBody);
        physicsBody = null;
    }

    @Override
    public Body getBody(){
        return physicsBody;
    }

    @Override
    public GraphicsObject getGraphicsObject() {
        graphicsObject.setPosition(new Vec2(getPosition().x * Constants.PTM, Constants.SCREEN_HEIGHT - getPosition().y * Constants.PTM));
        graphicsObject.setAngle(-physicsBody.getAngle() * 180.0f / (float)(Math.PI));
        return graphicsObject;
    }
}
