public class GameConfig {
    public int playMode;
    public int playerType;
    int fieldWidth;
    int fieldHeight;
    int initLevel;
    boolean isMusicOn;
    boolean isSoundOn;
    boolean isExtendMode;
    int playerOneType;
    int playerTwoType;

    public GameConfig(int fieldWidth, int fieldHeight, int initLevel, boolean isMusicOn, boolean isSoundOn,
                      boolean isExtendMode, int playerOneType, int playerTwoType) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.initLevel = initLevel;
        this.isMusicOn = isMusicOn;
        this.isSoundOn = isSoundOn;
        this.isExtendMode = isExtendMode;
        this.playerOneType = playerOneType;
        this.playerTwoType = playerTwoType;
    }
}

