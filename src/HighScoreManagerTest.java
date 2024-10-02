import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HighScoreManagerTest {

    @Test
    public void testAddScore() {
        // Create a HighScoreManager instance
        HighScoreManager highScoreManager = new HighScoreManager();

        // Define a GameConfig object with required parameters (assuming the constructor)
        GameConfig gameConfig = new GameConfig(10, 20, 1, true, true, false, "Player1", 0);

        // Add a valid score
        highScoreManager.addScore("Player1", 5000, gameConfig);

        // Assert that the high score list now contains the new score
        assertEquals(1, highScoreManager.getHighScores().size(), "High score list should have 1 entry.");

        // Assert that the added score is correct
        HighScore highScore = highScoreManager.getHighScores().get(0);
        assertEquals("Player1", highScore.getName(), "Player name should match.");
        assertEquals(5000, highScore.getScore(), "Score should match.");
    }

    @Test
    public void testAddNegativeScore() {
        // Create a HighScoreManager instance
        HighScoreManager highScoreManager = new HighScoreManager();

        // Define a GameConfig object with required parameters
        GameConfig gameConfig = new GameConfig(10, 20, 1, true, true, false, "Player1", 0);

        // Attempt to add an invalid negative score
        highScoreManager.addScore("Player1", -5000, gameConfig);

        // Assert that the high score list does not contain the invalid score
        assertEquals(0, highScoreManager.getHighScores().size(), "Negative scores should not be added.");
    }
}
