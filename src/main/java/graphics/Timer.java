package graphics;

import game.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jbox2d.common.Vec2;

public class Timer extends GraphicsObject {
    private float time = 0;
    private Text text = null;

    public Timer(Vec2 position, Text text) {
        super(position);
        this.text = text;
        text.setX(Constants.TIMER_POS_X);
        text.setY(Constants.TIMER_POS_Y);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, Constants.TIMER_FONT_SIZE));
        resetTime();
    }

    public void resetTime(){
        time = 50;
    }

    public float getTime(){return this.time;}


    public void update(){ // wersja kliencka
        if(time < 100.0f){
            text.setX(Constants.TIMER_POS_X);
            String tmp = String.format("%05.2f", time);
            text.setText(tmp.substring(0,2) + ':' + tmp.substring(3,5));
        }else{
            text.setX(Constants.TIMER_POS_X - Constants.TIMER_FONT_SIZE / 3);
            String tmp = String.format("%06.2f", time);
            text.setText(tmp.substring(0,3) + ':' + tmp.substring(4,6));
        }
    }

    public void update(float dt){ // wersja serwerowa
        time -= dt;

        if(time < 100.0f){
            text.setX(Constants.TIMER_POS_X);
            String tmp = String.format("%05.2f", time);
            text.setText(tmp.substring(0,2) + ':' + tmp.substring(3,5));
        }else{
            text.setX(Constants.TIMER_POS_X - Constants.TIMER_FONT_SIZE / 3);
            String tmp = String.format("%06.2f", time);
            text.setText(tmp.substring(0,3) + ':' + tmp.substring(4,6));
        }
    }

    public void setTime(float time){
        this.time = time;
    }

    @Override
    public void render(GraphicsContext gc) {
    }
}
