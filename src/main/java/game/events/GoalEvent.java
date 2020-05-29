package game.events;

import game.Game;
import org.jbox2d.dynamics.contacts.Contact;
import physics.PhysicsContactListener;

public class GoalEvent implements PhysicsContactListener{
    Game game;
    public GoalEvent(Game game) {
        this.game = game;
    }

    @Override
    public void handle(Contact contact) {
        Object DataA = contact.m_fixtureA.m_userData;
        Object DataB = contact.m_fixtureB.m_userData;
        if(DataA == null || DataB == null)
            return;

        if((DataA.toString().startsWith("Goal") && DataB.toString().equals("Ball")) ||
                (DataB.toString().startsWith("Goal") && DataA.toString().equals("Ball"))) {
            System.out.println("goal");
            game.goal(DataA, DataB);
        }
    }
}
