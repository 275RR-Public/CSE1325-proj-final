package utils;

public final class Constants {

    //static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    //public static final int screen_width = (int) screen_size.getWidth();
    //public static final int screen_height = (int) screen_size.getHeight();

    // game board
    public static final int BASE_TILE_X = 32;   // game is 32 pixel art
    public static final int BASE_TILE_Y = 32;   // each tile is 32x32

    public static final int TILE_ROWS = 20;     // number of game tiles (x,y)
    public static final int TILE_COLS = 20;
    public static final int PIXELS_X = 640;     // tile x row (32 x 20) = 640
    public static final int PIXELS_Y = 640;     // tile x col
    public static final int GRID_LENGTH = 400;  // row x col (20 x 20) = 400

    // default creature stats
    public static final int CREATURE_HP = 20;
    public static final int CREATURE_AC = 10;
    public static final int STAT_POINTS = 10;
    public static final int CREATURE_MOVES = 5;
    public static final int CREATURE_ACTS = 1;
    
    // game directories
    public static final String MAIN_DIR = "GameFiles";
    public static final String SUB_DIR = "data";

    // file extensions
    public static final String SAVE_EXT = ".csv";
    public static final String IMG_EXT_PNG = ".png";
    public static final String IMG_EXT_JPG = ".jpg";

    // colored text in terminal
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    // music
    public static final int DEFAULT_SONG = 0;
    public static final float DEFAULT_VOL = .30f;
    // ui vol {10%, 20%, 30%, ... , 100%}
    public static final int DEFAULT_VOL_UI = 2; // 2 -> 30% -> .30f

    // RESOURCES ARE NOT PATHS - ALWAYS USE "/"

    // res folders
    public static final String DATA_RES = "res/data/";
    public static final String BGM_RES = "res/bgm/";
    public static final String IMG_GAME_RES = "res/img/Game/";
    public static final String IMG_MONSTER_RES = "res/img/Monster/";
    public static final String IMG_PLAYER_RES = "res/img/Player/";
    public static final String IMG_UI_RES = "res/img/UI/";
    public static final String IMG_UI_SCALED_RES = "res/img/UI/scaled/";

    // res game data
    public static final String WEAPONS = DATA_RES + "weapons" + SAVE_EXT;
    public static final String MONSTERS = DATA_RES + "monsters" + SAVE_EXT;

    // res ui
    public static final String PROFILE_RES = IMG_UI_RES + "profile" + IMG_EXT_PNG;
    public static final String PROFILE1_RES = IMG_UI_RES + "profile1" + IMG_EXT_PNG;

    public static final String BACK_RES = IMG_UI_SCALED_RES + "backg" + IMG_EXT_PNG;
    public static final String PLAY_RES = IMG_UI_SCALED_RES + "playg" + IMG_EXT_PNG;
    public static final String PAUSE_RES = IMG_UI_SCALED_RES + "pauseg" + IMG_EXT_PNG;
    public static final String NEXT_RES = IMG_UI_SCALED_RES + "nextg" + IMG_EXT_PNG;

    public static final String KEY_T_RES = IMG_UI_SCALED_RES + "LetterT" + IMG_EXT_PNG;
    public static final String KEY_A_RES = IMG_UI_SCALED_RES + "LetterA" + IMG_EXT_PNG;
    public static final String KEY_D_RES = IMG_UI_SCALED_RES + "LetterD" + IMG_EXT_PNG;
    public static final String KEY_SPACE_RES = IMG_UI_SCALED_RES + "Space" + IMG_EXT_PNG;
    public static final String MOUSE_RES = IMG_UI_SCALED_RES + "mouseg" + IMG_EXT_PNG;

    // res game
    public static final String GRASS1_RES = IMG_GAME_RES + "Grass1Tile32" + IMG_EXT_PNG;
    public static final String GRASS2_RES = IMG_GAME_RES + "Grass2Tile32" + IMG_EXT_PNG;
    public static final String GRASS3_RES = IMG_GAME_RES + "Grass3Tile32" + IMG_EXT_PNG;
    public static final String GRASS4_RES = IMG_GAME_RES + "Grass4Tile32" + IMG_EXT_PNG;
    public static final String GRASS5_RES = IMG_GAME_RES + "Grass5Tile32" + IMG_EXT_PNG;

    
}
