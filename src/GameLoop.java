import TetrisConfiguration.SoundPlayer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.cert.Extension;
import javax.swing.Timer;

public class GameLoop {
    private GameBoard gameBoard;
    private GamePanel gamePanel;
    private Timer timer;
    private int totalScore;
    private int gameLevel;
    private int TotalLines;
    private int maxGameLevel;
    private int stepsPerMove;
    private int currentStep;
    private boolean isAIPlayer;
    private Socket socket;
    private Operation response;
    private boolean isExternalPlayer;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3000;
    private TetrisAI ai;

    // Fields to track AI's target move
    private Move aiTargetMove;
    private boolean aiMoveInProgress;
    private boolean aiDroppingPiece;  // Flag to handle piece drop
    private boolean eMoveInProgress;
    private boolean eDroppingPiece;
    private boolean frameCheck;
    private PureGame game;
    private SoundPlayer effectPlayer;
    private boolean sound;

    private long lastMoveTime;  // Tracks the last time AI made a move
    private static final long AI_MOVE_DELAY = 100;  // 0.1 seconds delay for AI commands

    public GameLoop(GameBoard gameBoard, GamePanel gamePanel, int gameLevel, boolean isAIPlayer, boolean isExternalPlayer, SoundPlayer effectPlayer ,TetrisApp app) {
        this.effectPlayer = effectPlayer;
        this.gameLevel = gameLevel;
        this.gameBoard = gameBoard;
        this.gamePanel = gamePanel;
        gamePanel.setLevel(gameLevel);
        gamePanel.setInitialLevel(gameLevel);
        this.maxGameLevel = 11;
        this.stepsPerMove = 24;  // Number of steps for smoother movement
        this.currentStep = 0;
        this.isAIPlayer = isAIPlayer;
        this.isExternalPlayer = isExternalPlayer;
        if (isExternalPlayer) {
//            externalMoveHandler = null;  // This will be set once we receive a move
            eMoveInProgress = false;
            eDroppingPiece = false;
            lastMoveTime = System.currentTimeMillis();

//            System.out.println("Testing");
            //TODO set External player in progres status's
        }

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
        if (isExternalPlayer) {
            handleEMove();

//            if (!externalMoveHandler.isMoveInProgress()) {
//                externalMoveHandler = null;  // Move is complete, reset handler
//            }
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
                    eMoveInProgress = false;
                    eDroppingPiece = false;
                    int score = gameBoard.getScore();
                    int lines = gameBoard.getLines();
                    if ((lines-TotalLines) >=10) {
                        gameLevel++;
                        TotalLines = lines;
                        int time = (int) (500 / (gameLevel * 0.4));
                        timer.setDelay(time / stepsPerMove);
                    }
                    gamePanel.setPartialMove(currentStep,stepsPerMove);
                    gamePanel.setScore(score);
                    gamePanel.setLines(lines);
                    gamePanel.setLevel(gameLevel);
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
                if (sound){
                    effectPlayer.playSound(-20.0f);
            }
            lastMoveTime = currentTime;  // Update last move time after moving right
            return;  // Exit after a single move (right)
        } else if (currentPiece.getX() > aiTargetMove.getColumn()) {
            // Move left only if within bounds and once per cycle
                gameBoard.movePieceLeft();
                if (sound){
                    effectPlayer.playSound(-20.0f);
            }

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
                    if (sound){
                        effectPlayer.playSound(-20.0f);
                    }
                lastMoveTime = currentTime;  // Update last move time after dropping the piece

            }
        }
    }

    boolean paused;
    public void handleInput(String input) {
        if (!(isAIPlayer || isExternalPlayer)){
        switch (input) {
            case "left":
                if (!paused) {
                    gameBoard.movePieceLeft();
                }
                break;

            case "right":
                if (!paused) {
                    gameBoard.movePieceRight();
                }
                break;

            case "down":
                // Move piece down faster when the down key is pressed // problematic dont do this here. updateGame instead
                if (!paused) {
                    for (int i = 0; i < stepsPerMove; ++i) {
                        updateGame();
                    }
                }
                break;

            case "rotate":
                if (!paused) {
                    gameBoard.rotatePiece();
                }
                break;

            case "pause":

                if (paused) {
                    timer.start();
                    paused = false;
                } else {
                    timer.stop();
                    paused = true;
                }
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

    private boolean isDialogOpen = false;
    private void showErrorDialog(String message) {
        if (isDialogOpen){
            return;
        }
        isDialogOpen = true;
        JFrame frame = new JFrame("Error");
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                isDialogOpen = false;
                ConnectToServer();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel(message));
        panel.add(okButton);
        frame.add(panel);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }



    public void ConnectToServer() {
            try {
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                if (socket != null) {
                    this.socket = socket;
                }
            } catch (IOException e) {
                e.printStackTrace();
                showErrorDialog("You need to start the Tetris server to play this mode.");
            }
    }


    public Operation getResponse(int width, int height, int [][] cells, int [][] currentShape, int [][]nextShape) {
        ConnectToServer();
        this.game = PureGame.getINSTANCE();
        updatePureGame(game, width, height, cells, currentShape, nextShape);
        String response = "";
        Gson gson = new Gson();
        var operation = new Operation();
        try{
            if (this.socket != null){
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                sendMessage(game, out);
                response =  in.readLine();
                operation = gson.fromJson(response, Operation.class);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return operation;
    }

    //TODO make method that returns
    //response -> String response =  in.readLine();

    public void sendMessage(PureGame game, PrintWriter out) throws IOException {
        Gson gson = new Gson();
        String jsonGameState = gson.toJson(game);
//        System.out.println(jsonGameState);
        out.println(jsonGameState);
    }

    public void updatePureGame(PureGame game, int width, int height,int [][] cells ,int[][] currentShape, int [][] nextShape){
        game.setWidth(width);
        game.setHeight(height);
        game.setCurrentShape(currentShape);
        game.setNextShape(nextShape);
        game.setCells(cells);
    }

    class Operation {
        private int opX = gameBoard.getWidth()/2-1;
        private int opRotate = 0;

        // Getters and setters
        public int getOpX() {
            return opX;
        }

        public void setOpX(int opX) {
            this.opX = opX;
        }

        public int getOpRotate() {
            return opRotate;
        }

        public void setOpRotate(int opRotate) {
            this.opRotate = opRotate;
        }
    }
    private void handleEMove() {
        long currentTime = System.currentTimeMillis();

        // Only calculate the best move once per piece, when the piece is spawned
        if (!eMoveInProgress) {
            response = getResponse(gameBoard.getWidth(),gameBoard.getHeight(), gameBoard.getBoard(), gameBoard.getCurrentPiece().getCurrentShape(), gameBoard.getNextPiece().getCurrentShape());
            System.out.println(response.getOpX());
            System.out.println(response.getOpRotate());
            eMoveInProgress = true;
            eDroppingPiece = false;  // Reset the dropping state when moving
        }

        // Ensure at least 0.5 seconds has passed since the last move
        if (currentTime - lastMoveTime < AI_MOVE_DELAY) {
            return;  // Skip this iteration if 0.5 seconds haven't passed yet
        }

        TetrisPiece currentPiece = gameBoard.getCurrentPiece();

        // Apply gradual rotation (only once per cycle)
        if (currentPiece.getCurrentRotation() != response.getOpRotate()) {
            currentPiece.rotate();  // Rotate one step at a time
            lastMoveTime = currentTime;  // Update last move time after rotation
            return;  // Exit after a single move (rotation)
        }

        // Apply gradual horizontal movement (only move left or right once per cycle)
        if (currentPiece.getX() < response.getOpX()) {
            // Move right only if within bounds and once per cycle
            gameBoard.movePieceRight();
            lastMoveTime = currentTime;  // Update last move time after moving right
            if (sound){
                effectPlayer.playSound(-20.0f);
            }
            return;  // Exit after a single move (right)
        } else if (currentPiece.getX() > response.getOpX()) {
            // Move left only if within bounds and once per cycle
            gameBoard.movePieceLeft();
            lastMoveTime = currentTime;  // Update last move time after moving left
            if (sound){
                effectPlayer.playSound(-20.0f);
            }
            return;  // Exit after a single move (left)
        }
        if (currentPiece.getCurrentRotation() == response.getOpRotate() &&
                currentPiece.getX() == response.getOpX()) {

            // Now start dropping the piece one step at a time
            eDroppingPiece = true;  // Start the drop process

            // Drop the piece one row every 0.5 seconds
            if (!gameBoard.canMovePieceDown()) {
                eMoveInProgress = false;  // AI move complete
                eDroppingPiece = false;  // Drop is complete
            } else {
                gameBoard.movePieceDown();  // Drop the piece one row at a time
                lastMoveTime = currentTime;  // Update last move time after dropping the piece
                if (sound){
                    effectPlayer.playSound(-20.0f);
                }

            }
        }
    }
    public void setSound(boolean sound){
        this.sound = sound;
    }

}
