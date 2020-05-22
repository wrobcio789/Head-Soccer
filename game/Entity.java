package game;

import graphics.GraphicsObject;
import graphics.Renderable;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
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

    public BodyFullDefinition getBodyFullDefinition(){  return bodyFullDefinition; }

    @Override
    public void CreateBody(World world){
        physicsBody = world.createBody(bodyFullDefinition.getBodyDef());
        for(FixtureDef fixtureDef : bodyFullDefinition.getFixtureDefs())
            physicsBody.createFixture(fixtureDef);
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
        graphicsObject.setPosition(transformPhysicsToGraphicsCoordinates(getPosition()));
        graphicsObject.setAngle(-physicsBody.getAngle() * 180.0f / (float)(Math.PI));
        return graphicsObject;
    }

    private Vec2 transformPhysicsToGraphicsCoordinates(Vec2 physicsPosition){
        AABB fixtureAsBox = physicsBody.getFixtureList().getAABB(0);
        Vec2 shiftedPosition = new Vec2(physicsPosition.x - fixtureAsBox.getExtents().x, physicsPosition.y + fixtureAsBox.getExtents().y);
        return new Vec2(shiftedPosition.x * Constants.PTM, Constants.SCREEN_HEIGHT - shiftedPosition.y * Constants.PTM);
    }

    void resetBody(){
        World world = getBody().getWorld();
        world.destroyBody(getBody());
        CreateBody(world);
    }
}
