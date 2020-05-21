package physics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public interface PhysicsObject {

    void CreateBody(World world);

    void DestroyBody(World world);

    Body getBody();
}
