package graphics;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.Color;
import org.jbox2d.common.Vec2;

public class Sprite extends GraphicsObject{
    private Image image;

    public Sprite(Image image, Vec2 position){
        super(position);
        this.image = image;
    }

    @Override
    public void render(GraphicsContext gc) {
        Image rotatedImage = getRotatedImage(getAngle());
        gc.drawImage(rotatedImage, getPosition().x, getPosition().y);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Image getRotatedImage(float angle){
        if(angle == 0.0f)
            return image;
        ImageView iv = new ImageView(image);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        params.setTransform(new Rotate(angle, image.getHeight() / 2, image.getWidth() / 2));
        params.setViewport(new Rectangle2D(0, 0, image.getHeight(), image.getWidth()));
        return iv.snapshot(params, null);
    }
}