import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop {
    private GameBoard gameBoard;
    private GamePanel gamePanel;
    private Timer timer;
    private int totalScore;
    private int GameLevel;

    public GameLoop(GameBoard gameBoard, GamePanel gamePanel, int gameLevel) {
        this.GameLevel = gameLevel;
        this.gameBoard = gameBoard;
        this.gamePanel = gamePanel;

        // Set up the game loop timer to move the piece down every 500ms
        timer = new Timer(Math.floorDiv(3000,gameLevel), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();



    }
    public void updateGame() {
        int BaseScore = 500;
        if (!gameBoard.movePieceDown()) {
            if (gameBoard.isGameOver()) {
                timer.stop();  // Stop the game loop if the game is over
                System.out.println("Game Over");
            } else {
                gameBoard.spawnNewPiece();// Spawn a new piece when the current one can no longer move down
                // Increment gameLevel every 500 score added.
                int score = gameBoard.getScore();
                if (score%BaseScore == 0){
                    GameLevel++;
                    timer.setDelay(Math.floorDiv(3000,GameLevel/2));
                }
                gamePanel.setScore(score);
            }
        }
        gamePanel.repaint();  // Repaint the game panel after updating the game state
    }

    boolean paused;
    public void handleInput(String input) {
        switch (input) {
            case "left":
                if(!paused){
                    gameBoard.movePieceLeft();
                }
                break;

            case "right":
                if(!paused){
                    gameBoard.movePieceRight();
                }
                break;

            case "down":
//                gameBoard.movePieceDown();  // Move piece down faster when the down key is pressed // problematic dont do this here. updateGame instead
                if(!paused){
                    updateGame();
                }
                break;

            case "rotate":
                if(!paused){
                    gameBoard.rotatePiece();
                }
                break;

            case "pause":

                if(paused){
                    timer.start();
                    paused = false;
                }else {
                    timer.stop();
                    paused = true;
                }
        }
        gamePanel.repaint();  // Repaint the game panel after handling input
    }

}
