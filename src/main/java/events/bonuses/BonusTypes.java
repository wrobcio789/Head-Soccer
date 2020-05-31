package game.events.bonuses;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BonusTypes {
    SLOW_ALL,
    SPEED_UP_ALL;

   private static final List<BonusTypes> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BonusTypes randomBonus()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
