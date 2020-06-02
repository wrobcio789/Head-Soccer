package physics;

import org.jbox2d.dynamics.contacts.Contact;

public interface PhysicsContactListener {

    void handle(Contact contact);
}
