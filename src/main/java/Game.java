package game;

import game.events.GoalEvent;
import graphics.Renderer;
import graphics.Sprite;
import graphics.Timer;
import input.Input;
import input.KeyboardInput;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.common.Vec2;
import physics.BodyFullDefinition;
import physics.PhysicsEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;


public class Game extends Application {
    private static final String TITLE = "Head Soccer";
    private static final Vec2 GRAVITY = new Vec2(0, -9.81f);
    private Scene scene;
    private Canvas canvas = null;
    private GraphicsContext gc = null;
    private Renderer renderer = null;
    private PhysicsEngine physicsEngine = null;
    private Player player1, player2;
    private Entity ball;
    private Input input1 = null;
    private Input input2 = null;
    private Timer timer = null;
    private Bonus bonus = null;
    private float timeScale = 1.0f;


    @Override
    public void start(Stage primaryStage) throws Exception {
        InitWindow(primaryStage);
        Init();
        Loop();
    }

    private void InitWindow(Stage primaryStage) {
        Text text = new Text();
        timer = new Timer(new Vec2(0.43f,0.4f),text);

        primaryStage.setTitle(TITLE);
        Group root = new Group();
        canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        renderer = new Renderer(gc);
        physicsEngine = new PhysicsEngine(GRAVITY);

        root.getChildren().add(canvas);
        root.getChildren().add(text);
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void Init() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("resources//obrazek_small.png"));
        Sprite backgroundSprite = new Sprite(new Image(new FileInputStream("resources//boisko_final.png")), new Vec2(0.0f, 0.0f));

        Sprite ballSprite = new Sprite(image, new Vec2(5, 5));
        BodyFullDefinition ballDefinition = BodyDefinitionFactory.createBallDefinition();
        ball = new Entity(ballDefinition, ballSprite);

        Sprite emptySprite = new Sprite(null, new Vec2(5, 5));
        BodyFullDefinition groundDefinition = BodyDefinitionFactory.createGroundDefinition();
        Entity ground = new Entity(groundDefinition, emptySprite);

        BodyFullDefinition roofDefinition = BodyDefinitionFactory.createRoofDefinition();
        Entity roof = new Entity(roofDefinition, emptySprite);

        BodyFullDefinition leftWallDefinition = BodyDefinitionFactory.createWallDefinition(1);
        Entity leftWall = new Entity(leftWallDefinition, emptySprite);

        BodyFullDefinition rightWallDefinition = BodyDefinitionFactory.createWallDefinition(2);
        Entity rightWall = new Entity(rightWallDefinition, emptySprite);

        Image goalImage = new Image(new FileInputStream("resources//bramka_test.png"));
        Sprite goalSprite = new Sprite(goalImage, new Vec2(3, 3));
        BodyFullDefinition rightGoalDefinition = BodyDefinitionFactory.createGoalDefinition(Constants.RIGHT_SIDE);
        Entity rightGoal = new Entity(rightGoalDefinition, goalSprite);

        BodyFullDefinition leftGoalDefinition = BodyDefinitionFactory.createGoalDefinition(Constants.LEFT_SIDE);
        Entity leftGoal = new Entity(leftGoalDefinition, goalSprite);

        Image player1Image = new Image(new FileInputStream("resources//player1.png"));
        Sprite player1Sprite = new Sprite(player1Image, new Vec2(0.0f, 0.0f));
        BodyFullDefinition player1Definition = BodyDefinitionFactory.createPlayerDefinition(Constants.LEFT_SIDE);
        Entity leftPlayer = new Entity(player1Definition, player1Sprite);

        Image player2Image = new Image(new FileInputStream("resources//player2.png"));
        Sprite player2Sprite = new Sprite(player2Image, new Vec2(0.0f, 0.0f));
        BodyFullDefinition player2Definition = BodyDefinitionFactory.createPlayerDefinition(Constants.RIGHT_SIDE);
        Entity rightPlayer = new Entity(player2Definition, player2Sprite);

        bonus = Bonus.createRandomBonus();

        renderer.addRenderable(backgroundSprite);
        renderer.addRenderable(ball);
        renderer.addRenderable(rightGoal);
        renderer.addRenderable(leftGoal);
        renderer.addRenderable(timer);
        renderer.addRenderable(leftPlayer);
        renderer.addRenderable(rightPlayer);
        renderer.addRenderable(bonus);
        physicsEngine.addPhysicsObject(bonus);
        physicsEngine.addPhysicsObject(ball);
        physicsEngine.addPhysicsObject(rightGoal);
        physicsEngine.addPhysicsObject(leftGoal);
        physicsEngine.addPhysicsObject(ground);
        physicsEngine.addPhysicsObject(roof);
        physicsEngine.addPhysicsObject(leftWall);
        physicsEngine.addPhysicsObject(rightWall);
        physicsEngine.addPhysicsObject(leftPlayer);
        physicsEngine.addPhysicsObject(rightPlayer);

        physicsEngine.addContactListener(new GoalEvent(this));
        physicsEngine.addContactListener(Bonus.BonusContactListener(this));


        input1 = new KeyboardInput(scene, KeyCode.A, KeyCode.D, KeyCode.W);
        input2 = new KeyboardInput(scene, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);
        player1 = new Player(input1, leftPlayer, "Maciej");
        player2 = new Player(input2, rightPlayer, "Kaxaj");

    }

    private void Loop() {

        class GameLoop extends AnimationTimer {
            private long previousNanoTime;

            GameLoop(long startNanoTime) {
                this.previousNanoTime = startNanoTime;
            }

            @Override
            public void handle(long currentNanoTime) {
                float dt = timeScale * ((currentNanoTime - previousNanoTime) / 1000000000.0f);
                previousNanoTime = currentNanoTime;

                input1.update();
                input2.update();
                physicsEngine.update(dt);
                timer.update(dt);
                renderer.render();
            }
        };

        final long startNanoTime = System.nanoTime();
        new GameLoop(startNanoTime).start();
    }

    public void goal(Object dataA, Object dataB){
        if(dataA.toString().equals("Goal"+Constants.LEFT_SIDE) || dataB.toString().equals("Goal"+Constants.LEFT_SIDE)){
            player2.addScore();
        }else{
            player1.addScore();
        }

        resetPositions();

        if(player1.getScore() == Constants.MAX_SCORE || player2.getScore() == Constants.MAX_SCORE){
            resetPlayersScore();
        }

        System.out.println(player1.getScore() + " : " + player2.getScore());
    }

    private void resetPlayersScore(){
        player1.resetScore();
        player2.resetScore();
        timer.resetTime();
    }

    public void resetPositions (){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                player1.entity.resetBody();
                player2.entity.resetBody();
                ball.resetBody();
            }
        });
    }

    public void resetBonus(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(bonus!=null) {
                    physicsEngine.removePhysicsObject(bonus);
                    renderer.removeRenderable(bonus);
                }
            }
        });

        PauseTransition wait = new PauseTransition(Duration.seconds(new Random().nextInt(Constants.BONUS_MAX_WAIT_TIME) + 1));
        wait.setOnFinished((e) -> {
            bonus = Bonus.createRandomBonus();
            physicsEngine.addPhysicsObject(bonus);
            renderer.addRenderable(bonus);
        });
        wait.play();
    }

    public void setTimeScale(float scale){
        timeScale = scale;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
