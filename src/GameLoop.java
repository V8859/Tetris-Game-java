import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

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
    private TetrisAI ai;

    // Fields to track AI's target move
    private Move aiTargetMove;
    private boolean aiMoveInProgress;
    private boolean aiDroppingPiece;  // Flag to handle piece drop

    private long lastMoveTime;  // Tracks the last time AI made a move
    private static final long AI_MOVE_DELAY = 100;  // 0.1 seconds delay for AI commands

    public GameLoop(GameBoard gameBoard, GamePanel gamePanel, int gameLevel, boolean isAIPlayer) {
        this.gameLevel = gameLevel;
        this.gameBoard = gameBoard;
        this.gamePanel = gamePanel;
        this.maxGameLevel = 11;
        this.stepsPerMove = 24;  // Number of steps for smoother movement
        this.currentStep = 0;
        this.isAIPlayer = isAIPlayer;
        if (isAIPlayer) {
            ai = new TetrisAI();  // Initialize AI
            aiMoveInProgress = false;  // Flag to track if AI is still moving the piece
            aiDroppingPiece = false;  // Flag to track if AI is dropping the piece
            lastMoveTime = System.currentTimeMillis();  // Initialize the last move time
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

        if (currentStep < stepsPerMove - 1 && gameBoard.canMovePieceDown() && !aiDroppingPiece) {
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
                    // Reset AI state after spawning a new piece
                    aiMoveInProgress = false;
                    aiDroppingPiece = false;  // Reset dropping flag for the new piece
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

    private void handleAIMove() {
        long currentTime = System.currentTimeMillis();

        // Only calculate the best move once per piece, when the piece is spawned
        if (!aiMoveInProgress) {
            aiTargetMove = ai.findBestMove(gameBoard, gameBoard.getCurrentPiece());
            aiMoveInProgress = true;
            aiDroppingPiece = false;  // Reset the dropping state when moving
        }

        // Ensure at least 0.5 seconds has passed since the last move
        if (currentTime - lastMoveTime < AI_MOVE_DELAY) {
            return;  // Skip this iteration if 0.5 seconds haven't passed yet
        }

        TetrisPiece currentPiece = gameBoard.getCurrentPiece();

        // Apply gradual rotation (only once per cycle)
        if (currentPiece.getCurrentRotation() != aiTargetMove.getRotation()) {
            currentPiece.rotate();  // Rotate one step at a time
            lastMoveTime = currentTime;  // Update last move time after rotation
            return;  // Exit after a single move (rotation)
        }

        // Apply gradual horizontal movement (only move left or right once per cycle)
        if (currentPiece.getX() < aiTargetMove.getColumn()) {
            // Move right only if within bounds and once per cycle
            gameBoard.movePieceRight();
            lastMoveTime = currentTime;  // Update last move time after moving right
            return;  // Exit after a single move (right)
        } else if (currentPiece.getX() > aiTargetMove.getColumn()) {
            // Move left only if within bounds and once per cycle
            gameBoard.movePieceLeft();
            lastMoveTime = currentTime;  // Update last move time after moving left
            return;  // Exit after a single move (left)
        }

        // Check if the AI has reached the target position
        if (currentPiece.getCurrentRotation() == aiTargetMove.getRotation() &&
                currentPiece.getX() == aiTargetMove.getColumn()) {

            // Now start dropping the piece one step at a time
            aiDroppingPiece = true;  // Start the drop process

            // Drop the piece one row every 0.5 seconds
            if (!gameBoard.canMovePieceDown()) {
                aiMoveInProgress = false;  // AI move complete
                aiDroppingPiece = false;  // Drop is complete
            } else {
                gameBoard.movePieceDown();  // Drop the piece one row at a time
                lastMoveTime = currentTime;  // Update last move time after dropping the piece
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
