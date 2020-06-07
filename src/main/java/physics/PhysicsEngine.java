package physics;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.LinkedList;
import java.util.List;

public class PhysicsEngine {
    static final int VELOCITY_ITERATIONS = 10;
    static final int POSITION_ITERATIONS = 10;

    private final World world;
    private final List<PhysicsContactListener> contactListeners = new LinkedList<>();

    public PhysicsEngine(Vec2 gravity){
        world = new World(gravity);
        world.setAutoClearForces(true);
        world.setAllowSleep(false);
        world.setContinuousPhysics(true);
        world.setContactListener(new PhysicsEngineContactListener());
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

    public void addContactListener(PhysicsContactListener contactListener){
        contactListeners.add(contactListener);
    }

    public void removeContactListener(PhysicsContactListener contactListener) {
        contactListeners.remove(contactListener);
    }


    private class PhysicsEngineContactListener implements ContactListener{

        @Override
        public void beginContact(Contact contact) {
            for(PhysicsContactListener contactListener : contactListeners)
                contactListener.handle(contact);
        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold manifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse contactImpulse) {

        }
    }

}
