package communication;

public enum MessageType {
    SCORE(1),
    PLAYER_POS(2),
    BALL_POS(3),
    TIME(4),
    SET_ID(5),
    ADDBONUS(6),
    REMOVEBONUS(7),

    MOVE_LEFT(8),
    MOVE_RIGHT(9),
    JUMP(10);

    private final int value;

    MessageType(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static MessageType getByInt(int value){
        for(MessageType type : values())
            if(type.getValue() == value)
                return type;
        throw new IllegalStateException("Illegal value");
    }
}
