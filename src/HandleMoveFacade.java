import java.awt.Color;
import TetrisConfiguration.SoundPlayer;
import java.net.Socket;

public class HandleMoveFacade {

    private GameBoard gameBoard;
    private SoundPlayer effectPlayer;
    private boolean sound;

    // AI related flags and states
    private boolean aiMoveInProgress = false;
    private boolean aiDroppingPiece = false;
    private Move aiTargetMove;
    private TetrisAI ai;
    private long lastMoveTime;
    private static final long AI_MOVE_DELAY = 100;
    private boolean eMoveInProgress;
    public boolean isExternalMoveInProgress() {
        return eMoveInProgress;
    }
    private boolean eDroppingPiece = false;
    private GameLoop.Operation response;
    private Socket socket;
    private String SERVER_HOST = "localhost";
    private int SERVER_PORT = 3000;
    private PureGame game;

    public HandleMoveFacade(GameBoard gameBoard, SoundPlayer effectPlayer, boolean sound) {
        this.gameBoard = gameBoard;
        this.effectPlayer = effectPlayer;
        this.sound = sound;
    }

    // Handle AI Move logic
    public void handleAIMove(TetrisAI ai, long currentTime) {
        this.ai = ai;

        if (!aiMoveInProgress) {
            aiTargetMove = ai.findBestMove(gameBoard, gameBoard.getCurrentPiece());
            aiMoveInProgress = true;
            aiDroppingPiece = false;
        }

        if (currentTime - lastMoveTime < AI_MOVE_DELAY) {
            return;  // Skip this iteration if delay not passed
        }

        TetrisPiece currentPiece = gameBoard.getCurrentPiece();

        // Rotation handling
        if (currentPiece.getCurrentRotation() != aiTargetMove.getRotation()) {
            currentPiece.rotate();
            lastMoveTime = currentTime;
            return;
        }

        // Horizontal movement handling
        if (currentPiece.getX() < aiTargetMove.getColumn()) {
            gameBoard.movePieceRight();
            playEffect();
            lastMoveTime = currentTime;
            return;
        } else if (currentPiece.getX() > aiTargetMove.getColumn()) {
            gameBoard.movePieceLeft();
            playEffect();
            lastMoveTime = currentTime;
            return;
        }

        // Dropping piece handling
        if (currentPiece.getCurrentRotation() == aiTargetMove.getRotation() &&
                currentPiece.getX() == aiTargetMove.getColumn()) {

            aiDroppingPiece = true;

            if (!gameBoard.canMovePieceDown()) {
                aiMoveInProgress = false;
                aiDroppingPiece = false;
            } else {
                gameBoard.movePieceDown();
                playEffect();
                lastMoveTime = currentTime;
            }
        }
    }
    public void handleEMove(GameLoop.Operation response, long currentTime) {
        // Only update the response if it's not null (i.e., when a new move is requested)
        if (response != null) {
            System.out.println("External Response X: " + response.getOpX() + " Rotate: " + response.getOpRotate());
            this.response = response;  // Store the response to handle it later
            eMoveInProgress = true;     // Mark the move as in progress
        }

        // Proceed with the existing move if it's in progress
        if (this.response != null) {
            TetrisPiece currentPiece = gameBoard.getCurrentPiece();

            // Apply gradual rotation (only once per cycle)
            if (currentPiece.getCurrentRotation() != this.response.getOpRotate()) {
                currentPiece.rotate();
                lastMoveTime = currentTime;  // Update last move time after rotation
                return;  // Exit after rotation
            }

            // Apply gradual horizontal movement (only move left or right once per cycle)
            if (currentPiece.getX() < this.response.getOpX()) {
                gameBoard.movePieceRight();
                playEffect();
                lastMoveTime = currentTime;  // Update last move time after moving right
                return;  // Exit after moving right
            } else if (currentPiece.getX() > this.response.getOpX()) {
                gameBoard.movePieceLeft();
                playEffect();
                lastMoveTime = currentTime;  // Update last move time after moving left
                return;  // Exit after moving left
            }

            // If the piece is at the target position and rotation, start dropping the piece
            if (currentPiece.getCurrentRotation() == this.response.getOpRotate() &&
                    currentPiece.getX() == this.response.getOpX()) {

                eDroppingPiece = true;  // Start dropping the piece

                if (!gameBoard.canMovePieceDown()) {
                    eMoveInProgress = false;  // Move complete
                    eDroppingPiece = false;   // Drop complete
                } else {
                    gameBoard.movePieceDown();  // Drop the piece one row at a time
                    playEffect();
                    lastMoveTime = currentTime;  // Update last move time after dropping
                }
            }
        }
    }

    private void playEffect() {
        if (sound) {
            effectPlayer.playSound(-20.0f);
        }
    }

    // Reset AI and External Player state for new pieces
    public void resetMoveState() {
        aiMoveInProgress = false;
        aiDroppingPiece = false;
        eMoveInProgress = false;
        eDroppingPiece = false;
    }
}
