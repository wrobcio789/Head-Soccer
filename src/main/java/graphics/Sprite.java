package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jbox2d.common.Vec2;

public class Sprite extends GraphicsObject{
    private Image image;

    public Sprite(Image image, Vec2 position){
        super(position);
        this.image = image;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getPosition().x * 100, 420 - getPosition().y * 100);
        System.out.println(getPosition().x * 100 + " , " + (420 - (getPosition().y * 100)));
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
