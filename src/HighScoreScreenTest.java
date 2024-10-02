import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;

public class HighScoreScreenTest {

    @Test
    public void testUpdateScoreDisplay() {
        // Initialize HighScoreManager and other required components
        HighScoreManager highScoreManager = new HighScoreManager();
        JPanel parentPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();

        // Initialize the HighScoreScreen with the correct constructor
        HighScoreScreen highScoreScreen = HighScoreScreen.getInstance();
        highScoreScreen.setBaseParameters(parentPanel, cardLayout, highScoreManager);
        // Add a score to the HighScoreManager
        GameConfig gameConfig = new GameConfig(10, 20, 1, true, true, false, "Player1", 0);
        highScoreManager.addScore("Player1", 5000, gameConfig);

        // Update the score display
        highScoreScreen.updateScoreDisplay();

        // Check if the table model has the correct number of rows
        assertEquals(1, highScoreScreen.getTableModel().getRowCount(), "The table should contain one entry.");

        // Check if the correct player name and score are displayed in the table
        assertEquals("Player1", highScoreScreen.getTableModel().getValueAt(0, 1), "The player's name should be displayed correctly.");
        assertEquals(5000, highScoreScreen.getTableModel().getValueAt(0, 2), "The player's score should be displayed correctly.");
    }
}
