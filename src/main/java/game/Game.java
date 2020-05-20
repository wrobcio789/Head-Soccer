package game;

import graphics.Renderer;
import graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jbox2d.common.Vec2;
import physics.BodyFullDefinition;
import physics.PhysicsEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Game extends Application {
    private static final String TITLE = "Head Soccer";
    private static final Vec2 GRAVITY = new Vec2(0, -0.5f);
    private Canvas canvas = null;
    private GraphicsContext gc = null;
    private Renderer renderer = null;
    private PhysicsEngine physicsEngine = null;
    private Entity ground;

    @Override
    public void start(Stage primaryStage) throws Exception {
        InitWindow(primaryStage);
        Init();
        Loop();
    }

    private void InitWindow(Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        Group root = new Group();
        canvas = new Canvas(750, 420);
        gc = canvas.getGraphicsContext2D();
        renderer = new Renderer(gc);
        physicsEngine = new PhysicsEngine(GRAVITY);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void Init() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("C://Users//Admin//Documents//Studia//Przetwarzanie rozproszone//Projekt//Head-Soccer//resources//obrazek_small.png"));

        Sprite ballSprite = new Sprite(image, new Vec2(5, 5));
        BodyFullDefinition ballDefinition = BodyDefinitionFactory.createBallDefinition();
        Entity entity = new Entity(ballDefinition, ballSprite);

        Sprite emptySprite = new Sprite(null, new Vec2(5, 5));
        BodyFullDefinition groundDefinition = BodyDefinitionFactory.createGroundDefinition();
        ground = new Entity(groundDefinition, emptySprite);


        renderer.addRenderable(entity);
        physicsEngine.addPhysicsObject(entity);
        physicsEngine.addPhysicsObject(ground);
    }

    private void Loop() {

        class GameLoop extends AnimationTimer {
            private long startNanoTime;
            private long previousNanoTime;

            GameLoop(long startNanoTime) {
                this.startNanoTime = startNanoTime;
                this.previousNanoTime = startNanoTime;
            }

            @Override
            public void handle(long currentNanoTime) {
                float dt = (currentNanoTime - previousNanoTime) / 1000000000.0f;
                startNanoTime = currentNanoTime;
                physicsEngine.update(dt);
                renderer.render();
            }
        };

        final long startNanoTime = System.nanoTime();
        new GameLoop(startNanoTime).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
