package physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class PhysicsEngine {
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 6;

    private World world;

    public PhysicsEngine(Vec2 gravity){
        world = new World(gravity);
        world.setAllowSleep(true);
        world.setContinuousPhysics(true);
    }

    public void update(float dt){
        world.step(dt, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    public void setGravity(Vec2 gravity){
        world.setGravity(gravity);
    }

    public void addPhysicsObject(PhysicsObject physicsObject){
        physicsObject.CreateBody(world);
    }

    public void removePhysicsObject(PhysicsObject physicsObject){
        physicsObject.DestroyBody(world);
    }

}
