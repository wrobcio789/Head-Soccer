package game;

import graphics.Renderer;
import graphics.Sprite;
import input.KeyboardInput;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.jbox2d.common.Vec2;
import physics.BodyFullDefinition;
import physics.PhysicsEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Game extends Application {
    private static final String TITLE = "Head Soccer";
    private static final Vec2 GRAVITY = new Vec2(0, -9.81f);
    private Scene scene;
    private Canvas canvas = null;
    private GraphicsContext gc = null;
    private Renderer renderer = null;
    private PhysicsEngine physicsEngine = null;
    private Entity ground;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        InitWindow(primaryStage);
        Init();
        Loop();
    }

    private void InitWindow(Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        Group root = new Group();
        canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        renderer = new Renderer(gc);
        physicsEngine = new PhysicsEngine(GRAVITY);

        root.getChildren().add(canvas);
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void Init() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("resources//obrazek_small.png"));
        Sprite backgroundSprite = new Sprite(new Image(new FileInputStream("resources//boisko_small.png")), new Vec2(0.0f, 0.0f));

        Sprite ballSprite = new Sprite(image, new Vec2(5, 5));
        BodyFullDefinition ballDefinition = BodyDefinitionFactory.createBallDefinition();
        Entity ball = new Entity(ballDefinition, ballSprite);

        Sprite emptySprite = new Sprite(null, new Vec2(5, 5));
        BodyFullDefinition groundDefinition = BodyDefinitionFactory.createGroundDefinition();
        ground = new Entity(groundDefinition, emptySprite);

        renderer.addRenderable(backgroundSprite);
        renderer.addRenderable(ball);
        physicsEngine.addPhysicsObject(ball);
        physicsEngine.addPhysicsObject(ground);

        player = new Player(new KeyboardInput(scene, KeyCode.A, KeyCode.D, KeyCode.W), ball, "Dobre ciasto");
    }

    private void Loop() {

        class GameLoop extends AnimationTimer {
            private long previousNanoTime;

            GameLoop(long startNanoTime) {
                this.previousNanoTime = startNanoTime;
            }

            @Override
            public void handle(long currentNanoTime) {
                float dt = (currentNanoTime - previousNanoTime) / 1000000000.0f;
                previousNanoTime = currentNanoTime;

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
