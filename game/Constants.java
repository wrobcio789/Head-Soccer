package game;

public class Constants {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final int PTM = 100;

    public static final float WORLD_WIDTH = (float)SCREEN_WIDTH/(float)PTM;
    public static final float WORLD_HEIGHT = (float)SCREEN_HEIGHT/(float)PTM;

    public static final float GROUND_LEVEL = 0.6f;

    public static final float BALL_POS_X = WORLD_WIDTH/2.0f;
    public static final float BALL_POS_Y = 3.0f;
    public static final float BALL_RADIUS = 0.3f;
    public static final float BALL_DENSITY = 0.1f;

    public static final float PLAYER_RADIUS = 0.4f;
    public static final float PLAYER_POS_Y = GROUND_LEVEL + PLAYER_RADIUS;
    public static final float PLAYER_LEFT_POS_X = 2.0f;
    public static final float PLAYER_RIGHT_POS_X = WORLD_WIDTH - PLAYER_LEFT_POS_X;
    public static final float PLAYER_DENSITY = 1.0f;

    public static final float GOAL_HEIGHT = 2.0f;
    public static final float GOAL_WIDTH = 1.0f;
    public static final float GOAL_CROSSBAR_HEIGHT = 0.1f;
    public static final float GOAL_AREA_MARGIN = BALL_RADIUS;
    public static final float GOAL_AREA_HEIGHT = 1.9f;
    public static final float GOAL_AREA_WIDTH = GOAL_WIDTH - BALL_RADIUS;
    public static final float GOAL_RIGHT_POS_X = WORLD_WIDTH - GOAL_WIDTH/2.0f;
    public static final float GOAL_LEFT_POS_X = GOAL_WIDTH/2.0f;
    public static final float GOAL_POS_Y = GROUND_LEVEL + GOAL_HEIGHT - GOAL_CROSSBAR_HEIGHT/2.0f;

    public static final int LEFT_SIDE = 1;
    public static final int RIGHT_SIDE = 2;

    public static final int MAX_SCORE = 3;

    public static final float EPSILON = 0.015f;
}
