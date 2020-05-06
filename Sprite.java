package sample.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite extends GraphicsObject{
    Image image;
    double posX;
    double posY;

    public Sprite(Image image, double posX, double posY){
        this.image = image;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public Image getImage() {
        return image;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
