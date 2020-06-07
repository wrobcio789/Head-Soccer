package game.events;

import game.server.GameServer;
import org.jbox2d.dynamics.contacts.Contact;
import physics.PhysicsContactListener;

public class GoalEvent implements PhysicsContactListener{
    GameServer game;
    public GoalEvent(GameServer game) {
        this.game = game;
    }

    @Override
    public void handle(Contact contact) {
        Object DataA = contact.m_fixtureA.m_userData;
        Object DataB = contact.m_fixtureB.m_userData;
        if(DataA == null || DataB == null)
            return;

        String goalData;
        if(DataA.toString().startsWith("Goal") && DataB.toString().equals("Ball")){
            goalData = DataA.toString();
        } else if(DataB.toString().startsWith("Goal") && DataA.toString().equals("Ball")) {
            goalData = DataB.toString();
        } else{
            return;
        }

        System.out.println("goal");
        int side = Integer.parseInt(goalData.substring("Goal".length()));
        game.goal(side);
    }
}
