public class HighScore {
    String name;
    int score;
    GameConfig config;

    public HighScore(String name, int score, GameConfig config) {
        this.name = name;
        this.score = score;
        this.config = config;
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for config
    public GameConfig getConfig() {
        return config;
    }
}
