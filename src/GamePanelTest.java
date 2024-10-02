import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePanelTest {

    @Test
    public void testGetInitialLevel() {

        GameBoard gameBoard = new GameBoard(10, 20, System.currentTimeMillis());
        GamePanel gamePanel = new GamePanel(gameBoard);
        gamePanel.setInitialLevel(3);
        assertEquals(3, gamePanel.getInitialLevel(), "Initial level should be 3.");
    }
}