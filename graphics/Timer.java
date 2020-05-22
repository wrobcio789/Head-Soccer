package graphics;

import javafx.scene.canvas.GraphicsContext;
import org.jbox2d.common.Vec2;

public class Timer extends GraphicsObject {
    private double baseTime = 0;
    private double time = 0;

    public Timer(Vec2 position) {
        super(position);
        resetTime();
    }

    public void resetTime(){
        baseTime = System.nanoTime();
        time = baseTime;
    }

    public double getTime(){return this.time-this.baseTime;}

    public void update(double dt){
        time += dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        //gc.drawImage(wImage, getPosition().x, getPosition().y);
    }
}
