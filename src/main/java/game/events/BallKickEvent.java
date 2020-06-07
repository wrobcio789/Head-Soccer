package game.events;

import game.Constants;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import physics.PhysicsContactListener;

public class BallKickEvent implements PhysicsContactListener {
    @Override
    public void handle(Contact contact) {
        Object DataA = contact.m_fixtureA.m_userData;
        Object DataB = contact.m_fixtureB.m_userData;
        if(DataA == null || DataB == null)
            return;

        Vec2 playerVelocity;
        Body ballBody;
        if(DataA.toString().startsWith("Player") && DataB.toString().startsWith("Ball")){
            playerVelocity = contact.m_fixtureA.getBody().getLinearVelocity();
            ballBody = contact.m_fixtureB.getBody();
        } else if(DataA.toString().startsWith("Ball") && DataB.toString().startsWith("Player")) {
            playerVelocity = contact.m_fixtureB.getBody().getLinearVelocity();
            ballBody = contact.m_fixtureA.getBody();
        } else{
            return;
        }

        System.out.println("Applied force");
        ballBody.applyForceToCenter(Constants.BALL_KICK_VEC.mul(Math.abs(playerVelocity.x)));
    }
}
