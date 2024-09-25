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
    private boolean isAIPlayer;
    private TetrisAI ai;  // Add an AI instance

    // Fields to track AI's target move
    private Move aiTargetMove;
    private boolean aiMoveInProgress;

    public GameLoop(GameBoard gameBoard, GamePanel gamePanel, int gameLevel, boolean isAIPlayer) {
        this.gameLevel = gameLevel;
        this.gameBoard = gameBoard;
        this.gamePanel = gamePanel;
        this.maxGameLevel = 10;
        this.stepsPerMove = 24;  // Number of steps for smoother movement
        this.currentStep = 0;
        this.isAIPlayer = isAIPlayer;
        gamePanel.setLevel(gameLevel);
        gamePanel.setInitialLevel(gameLevel);

        if (isAIPlayer) {
            ai = new TetrisAI();  // Initialize AI
            aiMoveInProgress = false;  // Flag to track if AI is still moving the piece
        }

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
        if (isAIPlayer) {
            handleAIMove();  // Handle gradual AI movement
        }

        if (currentStep < stepsPerMove - 1 && gameBoard.canMovePieceDown()) {
            currentStep++;
        } else {
            currentStep = 0;
            if (!gameBoard.movePieceDown()) {
                if (gameBoard.isGameOver()) {
                    timer.stop();
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
                        int time = (int) (500 / (gameLevel * 10));
                        int t2 = (int) (stepsPerMove);
                        timer.setDelay(time / t2);
                    }
                    gamePanel.setLines(gameBoard.getLines());
                    gamePanel.setScore(score);
                    gamePanel.setLevel(gameLevel);
                }
            }
        }
        gamePanel.setPartialMove(currentStep, stepsPerMove);
        gamePanel.repaint();
    }

    private void handleAIMove() {
        if (!aiMoveInProgress) {
            // If AI move isn't in progress, calculate the best move
            aiTargetMove = ai.findBestMove(gameBoard, gameBoard.getCurrentPiece());
            aiMoveInProgress = true;
        } else {
            TetrisPiece currentPiece = gameBoard.getCurrentPiece();

            // Apply gradual rotation
            if (currentPiece.getCurrentRotation() != aiTargetMove.getRotation()) {
                currentPiece.rotate();  // Rotate one step at a time
            }

            // Apply gradual horizontal movement
            if (currentPiece.getX() < aiTargetMove.getColumn() && gameBoard.movePieceRight()) {
                // Move right only if within bounds
                gameBoard.movePieceRight();
            } else if (currentPiece.getX() > aiTargetMove.getColumn() && gameBoard.movePieceLeft()) {
                // Move left only if within bounds
                gameBoard.movePieceLeft();
            }

            // Check if the AI has reached the target position
            if (currentPiece.getCurrentRotation() == aiTargetMove.getRotation() &&
                    currentPiece.getX() == aiTargetMove.getColumn()) {
                aiMoveInProgress = false;  // AI move complete
            }
        }
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
                  // Move piece down faster when the down key is pressed // problematic dont do this here. updateGame instead
                if(!paused) {
                    for (int i = 0; i < stepsPerMove; ++i){
                        updateGame();
                    }
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
