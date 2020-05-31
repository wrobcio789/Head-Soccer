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
        time = 0;
    }

    public double getTime(){return this.time;}

    public void update(float dt){
        time += dt;
        String tmp = String.format("%05.2f", time);
        text.setText(tmp.substring(0,2) + ':' + tmp.substring(3,5));
    }

    @Override
    public void render(GraphicsContext gc) {
    }
}
