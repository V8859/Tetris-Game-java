import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop {
    private GameBoard gameBoard;
    private GamePanel gamePanel;
    private Timer timer;
    private int totalScore;
    private int gameLevel;
    private int maxGameLevel;
    private int stepsPerMove;
    private int currentStep;

    public GameLoop(GameBoard gameBoard, GamePanel gamePanel, int gameLevel) {
        this.gameLevel = gameLevel;
        this.gameBoard = gameBoard;
        this.gamePanel = gamePanel;
        this.maxGameLevel = 11;
        this.stepsPerMove = 24; // Number of steps for smoother movement
        this.currentStep = 0;

        // Set up the game loop timer
        int time = (int) (500 / (gameLevel * 0.4));
        timer = new Timer(time / stepsPerMove, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();
    }

    public void updateGame() {
        if (currentStep < stepsPerMove - 1 && gameBoard.canMovePieceDown()) {
            currentStep++;
        } else {
            currentStep = 0;
            if (!gameBoard.movePieceDown()) {
                if (gameBoard.isGameOver()) {
                    timer.stop();
                    System.out.println("Game Over");
                    gamePanel.setGameOver(true);
                    gamePanel.repaint();
                } else {
                    if (!gameBoard.spawnNewPiece()) {
                        timer.stop();
                        gamePanel.setGameOver(true);
                        gamePanel.repaint();
                    }
                    int score = gameBoard.getScore();
                    if (score % 500 == 0 && score > totalScore && gameLevel < maxGameLevel) {
                        gameLevel++;
                        totalScore = score;
                        int time = (int) (500 / (gameLevel * 0.4));
                        timer.setDelay(time / stepsPerMove);
                    }
                    gamePanel.setScore(score);
                }
            }
        }
        gamePanel.setPartialMove(currentStep, stepsPerMove);
        gamePanel.repaint();
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
                    for(int i = 0; i < stepsPerMove; ++i)
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
    public void setPause(){
        this.timer.stop();
    }
    public void unPause(){
        this.timer.start();
    }

}
