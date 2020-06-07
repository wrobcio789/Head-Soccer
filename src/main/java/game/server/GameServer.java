package game.server;

import communication.MessageParser;
import communication.MessageQueue;
import communication.MessageType;
import game.*;
import game.events.BallKickEvent;
import game.events.GoalEvent;
import graphics.Renderer;
import graphics.Sprite;
import graphics.Timer;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.common.Vec2;
import physics.BodyFullDefinition;
import physics.PhysicsEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;


public class GameServer extends Application {
    private static final String TITLE = "Head Soccer";
    private Renderer renderer = null;
    private PhysicsEngine physicsEngine = null;
    private Player player1, player2;
    private Entity ball;
    private Timer timer = null;
    private float timeScale = 1.0f;
    private Bonus bonus;

    private final boolean[] playerMoved = new boolean[Constants.MAX_PLAYERS];
    private final MessageQueue receivedMessages = new MessageQueue();
    private final MessageQueue[] sentMessages = {new MessageQueue(), new MessageQueue()};


    @Override
    public void start(Stage primaryStage) throws Exception {
        InitWindow(primaryStage);
        InitGame();
        StartCommunicators();
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
        physicsEngine = new PhysicsEngine(Constants.GRAVITY);

        root.getChildren().add(canvas);
        root.getChildren().add(text);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void InitGame() throws FileNotFoundException {
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

        Image leftGoalImage = new Image(new FileInputStream("resources//goal_left.png"));
        Sprite leftGoalSprite = new Sprite(leftGoalImage, new Vec2(3, 3));
        BodyFullDefinition leftGoalDefinition = BodyDefinitionFactory.createGoalDefinition(Constants.LEFT_SIDE);
        Entity leftGoal = new Entity(leftGoalDefinition, leftGoalSprite);

        Image rightGoalImage = new Image(new FileInputStream("resources//goal_right.png"));
        Sprite rightGoalSprite = new Sprite(rightGoalImage, new Vec2(3, 3));
        BodyFullDefinition rightGoalDefinition = BodyDefinitionFactory.createGoalDefinition(Constants.RIGHT_SIDE);
        Entity rightGoal = new Entity(rightGoalDefinition, rightGoalSprite);

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
        physicsEngine.addContactListener(new BallKickEvent());
        physicsEngine.addContactListener(Bonus.BonusContactListener(this));

        player1 = new Player(null, leftPlayer, "Maciej");
        player2 = new Player(null, rightPlayer, "Kaxaj");
    }

    private void StartCommunicators(){
        new Thread(new ClientsManager(receivedMessages, sentMessages, this::resetGame)).start();
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

                attendReceivedMessages();
                sendRegularMessages();

                physicsEngine.update(dt);
                timer.update(dt);
                renderer.render();
            }
        };

        final long startNanoTime = System.nanoTime();
        new GameLoop(startNanoTime).start();
    }

    public void goal(int side) {
        if(side == Constants.LEFT_SIDE) {
            player2.addScore();
        } else {
            player1.addScore();
        }
        resetPositions();
        checkScores();
    }

    public void checkScores(){
        if(player1.getScore() == Constants.MAX_SCORE || player2.getScore() == Constants.MAX_SCORE){
            resetPlayersScore();
        }

        System.out.println(player1.getScore() + " : " + player2.getScore());
    }

    private void attendReceivedMessages(){
        resetPlayerMoves();
        while(receivedMessages.hasFront()){
            MessageParser.parseMessage(receivedMessages.front(), getMessageHandler());
        }
    }

    private void sendRegularMessages() {
        for(int i = 0; i < sentMessages.length; i++) {
            MessageQueue messageQueue = sentMessages[i];
            byte[] message = MessageParser.parseArgs(MessageType.SET_ID, new Object[] { i + 1 });
            messageQueue.add(message);
            message = MessageParser.parseArgs(MessageType.TIME, new Object[]{timer.getTime()});
            messageQueue.add(message);
            message = MessageParser.parseArgs(MessageType.PLAYER_POS, new Object[]{1, player1.getEntity().getPosition().x, player1.getEntity().getPosition().y });
            messageQueue.add(message);
            message = MessageParser.parseArgs(MessageType.PLAYER_POS, new Object[]{2, player2.getEntity().getPosition().x, player2.getEntity().getPosition().y });
            messageQueue.add(message);
            message = MessageParser.parseArgs(MessageType.BALL_POS, new Object[]{ ball.getPosition().x, ball.getPosition().y, ball.getBody().getAngle() });
            messageQueue.add(message);
            message = MessageParser.parseArgs(MessageType.SCORE, new Object[] { player1.getScore(),  player2.getScore() });
            messageQueue.add(message);

        }
    }

    private void resetPlayersScore(){
        player1.resetScore();
        player2.resetScore();
        timer.resetTime();
    }

    public void resetPositions (){
        Platform.runLater(() -> {
            player1.getEntity().resetBody();
            player2.getEntity().resetBody();
            ball.resetBody();
        });
    }

    private void resetGame(){
        resetPlayersScore();
        resetPlayersScore();
        resetBonus();
    }

    public MessageParser.MessageHandler getMessageHandler(){
        return (type, intArgs, floatArgs) -> {
            switch(type){
                case MOVE_RIGHT:
                    moveRightMessage(intArgs, floatArgs);
                    break;
                case MOVE_LEFT:
                    moveLeftMessage(intArgs, floatArgs);
                    break;
                case JUMP:
                    jumpMessage(intArgs, floatArgs);
                    break;
                default:
                    throw new IllegalStateException("Unsupported message type.");
            }
        };
    }

    public void setTimeScale(float scale){
        timeScale = scale;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void moveRightMessage(int[] intArgs, float[] floatArgs){
        if(intArgs[0] == 1)
            player1.MoveRight();
        else if(intArgs[0] == 2)
            player2.MoveRight();
    }

    public void moveLeftMessage(int[] intArgs, float[] floatArgs){
        if(intArgs[0] == 1)
            player1.MoveLeft();
        else if(intArgs[0] == 2)
            player2.MoveLeft();
    }

    public void jumpMessage(int[] intArgs, float[] floatArgs){
        if(intArgs[0] == 1)
            player1.Jump();
        else if(intArgs[0] == 2)
            player2.Jump();
    }

    public void resetBonus(){
        removeBonus();
        createNewBonusInTheFuture();
    }

    private void removeBonus(){
        Platform.runLater(() -> {
            if(bonus!=null) {
                sendMessage(MessageType.REMOVEBONUS, new Object[] {});
                physicsEngine.removePhysicsObject(bonus);
                renderer.removeRenderable(bonus);
            }
        });
    }

    private void createNewBonusInTheFuture(){
        PauseTransition wait = new PauseTransition(Duration.seconds(new Random().nextInt(Constants.BONUS_MAX_WAIT_TIME) + 1));
        wait.setOnFinished((e) -> {
            createNewBonus();
        });
        wait.play();
    }

    private void createNewBonus(){
        bonus = Bonus.createRandomBonus();
        physicsEngine.addPhysicsObject(bonus);
        renderer.addRenderable(bonus);
        sendMessage(MessageType.ADDBONUS, new Object[] {bonus.getType().getValue(), bonus.getPosition().x, bonus.getPosition().y});
    }

    private void sendMessage(MessageType type, Object[] args){
        byte[] message = MessageParser.parseArgs(type, args);
        for (MessageQueue sentMessage : sentMessages) {
            sentMessage.add(message);
        }
    }

    private void resetPlayerMoves(){
        player1.resetMoves();
        player2.resetMoves();
    }
}
