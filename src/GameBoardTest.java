import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void testIsGameOver_initialState() {
        // Initialize GameBoard with a standard width and height, and a seed for random generation
        GameBoard gameBoard = new GameBoard(10, 20, System.currentTimeMillis());

        // Check that the game is not over at the start
        assertFalse(gameBoard.isGameOver(), "Game should not be over at the start.");
    }

    @Test
    void testIsGameOver_afterPlacingBlockInTopRow() {
        // Create the game board with 10 width and 20 height
        GameBoard gameBoard = new GameBoard(10, 20, System.currentTimeMillis());

        // Simulate filling up the board to trigger a game-over condition
        // Manually place blocks in the top row to simulate game over
        int[][] board = gameBoard.getBoard();
        for (int col = 0; col < 10; col++) {
            board[0][col] = 1;  // Assume 1 means the block is occupied
        }

        // Now, check if the game is over after placing a block in the top row
        boolean isGameOver = gameBoard.isGameOver();
        assertTrue(isGameOver, "Game should be over when a piece is placed in the top row.");
    }
}
