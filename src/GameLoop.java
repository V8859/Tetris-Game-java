import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop {
    private GameBoard gameBoard;
    private Timer timer;

    public GameLoop(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        // Set up the game loop timer
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();
    }

    private void updateGame() {
        if (!gameBoard.movePieceDown()) {
            if (gameBoard.isGameOver()) {
                timer.stop();
                System.out.println("Game Over");
            } else {
                gameBoard.spawnNewPiece(); // Spawn a new piece when the current one can no longer move down
            }
        }
    }

    public void handleInput(String input) {
        switch (input) {
            case "left":
                gameBoard.movePieceLeft();
                break;
            case "right":
                gameBoard.movePieceRight();
                break;
            case "down":
                gameBoard.movePieceDown();
                break;
            case "rotate":
                gameBoard.rotatePiece();
                break;
        }
    }
}
