package game;

import game.events.bonuses.BonusTypes;
import graphics.Sprite;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import physics.PhysicsContactListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Bonus extends Entity {

    public Bonus(BonusTypes type){
        super(BodyDefinitionFactory.createBonusDefinition(getRandomPosition(), type), createSprite(type));
    }

    public static Bonus createRandomBonus(){
        BonusTypes bonusType = BonusTypes.randomBonus();
        return new Bonus(bonusType);
    }

    private static Vec2 getRandomPosition(){
        Random random = new Random();
        float x = random.nextFloat() * (Constants.BONUS_MAX_X - Constants.BONUS_MIN_X) + Constants.BONUS_MIN_X;
        float y = random.nextFloat() * (Constants.BONUS_MAX_Y - Constants.BONUS_MIN_Y) + Constants.BONUS_MIN_Y;
        return new Vec2(x, y);
    }

    private static Sprite createSprite(BonusTypes type) {
        String path = null;
        switch(type){
            case SLOW_ALL:
                path = "resources/bonuses/red_watch.png";
                break;
            case SPEED_UP_ALL:
                path = "resources/bonuses/green_watch.png";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException ignored) {
            System.out.println("Bonus resources are not complete.");
            System.exit(1);
        }
        return new Sprite(image, new Vec2(0.0f, 0.0f));
    }

    public static PhysicsContactListener BonusContactListener(Game game){
        return new PhysicsContactListener() {
            @Override
            public void handle(Contact contact) {
                Object DataA = contact.m_fixtureA.m_userData;
                Object DataB = contact.m_fixtureB.m_userData;
                if(DataA == null || DataB == null)
                    return;

                String bonusTypeString = null;
                if(DataA.toString().startsWith("Bonus") && DataB.toString().equals("Ball")){
                    bonusTypeString = DataA.toString().substring(5);
                } else if(DataB.toString().startsWith("Bonus") && DataA.toString().equals("Ball")){
                    bonusTypeString = DataB.toString().substring(5);
                } else{
                    return;
                }
                game.resetBonus();
                BonusTypes type = BonusTypes.valueOf(bonusTypeString);
                BonusEffect(type, game);
            }
        };
    }

    private static void BonusEffect(BonusTypes type, Game game){
        PauseTransition bonusResetter;
        switch(type){
            case SPEED_UP_ALL:
                game.setTimeScale(2.0f);
                bonusResetter = new PauseTransition(Duration.seconds(5));
                bonusResetter.setOnFinished((e) -> { game.setTimeScale(1.0f); });
                bonusResetter.play();
                break;
            case SLOW_ALL:
                game.setTimeScale(0.5f);
                bonusResetter = new PauseTransition(Duration.seconds(5));
                bonusResetter.setOnFinished((e) -> { game.setTimeScale(1.0f); });
                bonusResetter.play();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
