package graphics;

import game.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jbox2d.common.Vec2;

public class ScoreBoard extends GraphicsObject {
    private float time = 0;
    private Text text1 = null;
    private Text text2 = null;

    public ScoreBoard(Vec2 position, Text text1, Text text2) {
        super(position);
        this.text1 = text1;
        this.text2 = text2;
        text1.setX(Constants.SCOREBOARD_LEFT_X);
        text1.setY(Constants.SCOREBOARD_Y);
        text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, Constants.SCOREBOARD_FONT_SIZE));
        text2.setX(Constants.SCOREBOARD_RIGHT_X);
        text2.setY(Constants.SCOREBOARD_Y);
        text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, Constants.SCOREBOARD_FONT_SIZE));
        resetScore();
    }

    public void setScore(int score1, int score2){
        text1.setText(String.valueOf(score1));
        text2.setText(String.valueOf(score2));
    }

    public void resetScore(){
        text1.setText("0");
        text2.setText("0");
    }

    @Override
    public void render(GraphicsContext gc) {
    }
}
