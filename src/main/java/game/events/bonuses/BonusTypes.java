package game.events.bonuses;

import communication.MessageType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BonusTypes {
    SLOW_ALL(1),
    SPEED_UP_ALL(2);

   private static final List<BonusTypes> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    private final int value;

    public static BonusTypes randomBonus()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    BonusTypes(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static BonusTypes getByInt(int value){
        for(BonusTypes type : values())
            if(type.getValue() == value)
                return type;
        throw new IllegalStateException("Illegal value");
    }
}
