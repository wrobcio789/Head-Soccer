package game;
import input.Input;
import org.jbox2d.common.Vec2;

public class Player {
    int score = 0;
    final String name;
    Input input;
    Entity entity;

    public Player(Input input, Entity entity , String name){
        this.name = name;
        this.input = input;
        this.entity = entity;
        setCallbacks();
    }

    private void setCallbacks(){
        input.setJumpCallback(this::Jump);
        input.setMoveLeftCallback(this::MoveLeft);
        input.setMoveRightCallback(this::MoveRight);
    }

    private void MoveLeft(){
        entity.getBody().applyForce(new Vec2(-4.0f, 0.0f), entity.getBody().getWorldCenter());
    }

    private void MoveRight(){
        entity.getBody().applyForce(new Vec2(4.0f, 0.0f), entity.getBody().getWorldCenter());
    }

    private void Jump(){
        //if(entity.getPosition().y <= Constants.GROUND_LEVEL + Constants.BALL_RADIUS + Constants.EPSILON)
            entity.getBody().applyForce(new Vec2(0.00f, 20.0f), entity.getBody().getWorldCenter());
    }

    public void addScore(){
        score += 1;
    }

    public void resetScore(){
        score = 0;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }
}