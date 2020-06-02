package game.Client;

import communication.MessageParser;
import communication.MessageQueue;
import communication.MessageType;
import communication.ServerCommunicator;
import game.BodyDefinitionFactory;
import game.Constants;
import game.Entity;
import game.Player;
import graphics.Renderer;
import graphics.Sprite;
import graphics.Timer;
import input.Input;
import input.KeyboardInput;
import javafx.animation.AnimationTimer;
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
import org.jbox2d.common.Vec2;
import physics.BodyFullDefinition;
import physics.PhysicsEngine;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameClient extends Application {
    private static final String TITLE = Constants.TITLE;
    private Scene scene;
    private Renderer renderer = null;
    private Player player1, player2;
    private Entity ball;
    private Input input = null;
    private Timer timer = null;
    private int clientId = 1;
    private float timeScale = 1.0f;

    private final MessageQueue receivedMessages = new MessageQueue();
    private final MessageQueue sentMessages = new MessageQueue();


    @Override
    public void start(Stage primaryStage) throws Exception {
        InitWindow(primaryStage);
        Init();
        StartCommunicator();
        Loop();
    }

    private void InitWindow(Stage primaryStage) {
        Text text = new Text();
        timer = new Timer(new Vec2(0.43f,0.4f),text);

        primaryStage.setTitle(TITLE);
        Group root = new Group();
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        renderer = new Renderer(gc);

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


        renderer.addRenderable(backgroundSprite);
        renderer.addRenderable(ball);
        renderer.addRenderable(rightGoal);
        renderer.addRenderable(leftGoal);
        renderer.addRenderable(timer);
        renderer.addRenderable(leftPlayer);
        renderer.addRenderable(rightPlayer);

        PhysicsEngine physicsEngine = new PhysicsEngine(Constants.GRAVITY);
        physicsEngine.addPhysicsObject(ball);
        physicsEngine.addPhysicsObject(rightGoal);
        physicsEngine.addPhysicsObject(leftGoal);
        physicsEngine.addPhysicsObject(ground);
        physicsEngine.addPhysicsObject(roof);
        physicsEngine.addPhysicsObject(leftWall);
        physicsEngine.addPhysicsObject(rightWall);
        physicsEngine.addPhysicsObject(leftPlayer);
        physicsEngine.addPhysicsObject(rightPlayer);


        input = new KeyboardInput(scene, KeyCode.A, KeyCode.D, KeyCode.W);
        setInputCallbacks();
        player1 = new Player(null, leftPlayer, "Maciej");
        player2 = new Player(null, rightPlayer, "Kaxaj");

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

                input.update();
                attendReceivedMessages();
                renderer.render();

                long sleepTime = 30 - (long)(dt * 1000.0f);
                try {
                    if(sleepTime > 0){
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        final long startNanoTime = System.nanoTime();
        new GameLoop(startNanoTime).start();
    }

    private void StartCommunicator(){
        try {
            new Thread(new ServerCommunicator(sentMessages, receivedMessages)).start();
        } catch (IOException e) {
            System.out.println("Could not create server communicator");
            System.exit(-1);
        }
    }

    private void attendReceivedMessages(){
        while(!receivedMessages.isEmpty()){
            System.out.println("Parsing message");
            MessageParser.parseMessage(receivedMessages.front(), getMessageHandler());
        }
    }

    private void setInputCallbacks(){
        Integer[] args = {clientId};
        input.setJumpCallback(() -> sentMessages.add(MessageParser.parseArgs(MessageType.JUMP, args)));
        input.setMoveLeftCallback(() -> sentMessages.add(MessageParser.parseArgs(MessageType.MOVE_LEFT, args)));
        input.setMoveRightCallback(() -> sentMessages.add(MessageParser.parseArgs(MessageType.MOVE_RIGHT, args)));
    }

    private void resetPlayersScore(){
        player1.resetScore();
        player2.resetScore();
        timer.resetTime();
    }

    private void resetPositions (){
        Platform.runLater(() -> {
            player1.getEntity().resetBody();
            player2.getEntity().resetBody();
            ball.resetBody();
        });
    }

    private void reset(){
        resetPositions();
        resetPlayersScore();
    }


    private MessageParser.MessageHandler getMessageHandler(){
        return (type, intArgs, floatArgs) -> {
            switch(type){
                case SCORE:
                    scoreMessage(intArgs, floatArgs);
                    break;
                case BALL_POS:
                    ballPosMessage(intArgs, floatArgs);
                    break;
                case PLAYER_POS:
                    playerPosMessage(intArgs, floatArgs);
                    break;
                case TIME:
                    timeMessage(intArgs, floatArgs);
                    break;
                case SET_ID:
                    idMessage(intArgs, floatArgs);
                    break;
                default:
                    throw new IllegalStateException("Unsupported message type: " + type);
            }
        };
    }

    public void setTimeScale(float scale){
        timeScale = scale;
    }

    private void scoreMessage(int[] argsInt, float[] argsFloat){
        player1.setScore(argsInt[0]);
        player2.setScore(argsInt[1]);
    }

    private void ballPosMessage(int[] argsInt, float[] argsFloat){
        ball.setPosition(new Vec2(argsFloat[0], argsFloat[1]));
    }

    private void playerPosMessage(int[] argsInt, float[] argsFloat){
        Vec2 newPosition = new Vec2(argsFloat[1], argsFloat[2]);
        if(argsInt[0] == 1)
            player1.getEntity().setPosition(newPosition);
        else if(argsInt[0] == 2)
            player2.getEntity().setPosition(newPosition);
    }

    private void timeMessage(int[] argsInt, float[] argsFloat){
        timer.setTime(argsFloat[0]);
    }

    private void idMessage(int[] argsInt, float[] argsFloat) {
        clientId = argsInt[0];
    }

    public static void main(String[] args) {
        launch(args);
    }
}
