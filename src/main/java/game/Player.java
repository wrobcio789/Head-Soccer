package game;
import input.Input;
import org.jbox2d.common.Vec2;

public class Player {
    int score = 0;
    final String name;
    private Input input;
    private Entity entity;

    public Player(Input input, Entity entity , String name){
        this.name = name;
        this.input = input;
        this.entity = entity;
        setCallbacks();
    }

    private void setCallbacks(){
        if(input != null) {
            input.setJumpCallback(this::Jump);
            input.setMoveLeftCallback(this::MoveLeft);
            input.setMoveRightCallback(this::MoveRight);
        }
    }

    public void MoveLeft(){
        entity.getBody().applyForce(new Vec2(-4.0f, 0.0f), entity.getBody().getWorldCenter());
    }

    public void MoveRight(){
        entity.getBody().applyForce(new Vec2(4.0f, 0.0f), entity.getBody().getWorldCenter());
    }

    public void Jump(){
        if(entity.getPosition().y <= Constants.GROUND_LEVEL + Constants.PLAYER_RADIUS + Constants.EPSILON)
            entity.getBody().applyForce(new Vec2(0.00f, 250.0f), entity.getBody().getWorldCenter());
    }

    public void addScore(){
        score += 1;
    }

    public void resetScore(){
        score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public Entity getEntity(){
        return entity;
    }
}