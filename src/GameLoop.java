import TetrisConfiguration.SoundPlayer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean isExternalPlayer;
    private Socket socket;
    private HandleMoveFacade handleMoveFacade;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3000;
    private TetrisAI ai;
    private SoundPlayer effectPlayer;
    private boolean sound;
    private long lastMoveTime;
    private TetrisApp app;
    private PureGame game;
    private HighScoreManager ScoreManager;
    private boolean ExtendMode;
    private String playerType;

    public GameLoop(GameBoard gameBoard, GamePanel gamePanel, int gameLevel, boolean isAIPlayer, boolean isExternalPlayer, SoundPlayer effectPlayer, TetrisApp app, boolean ExtendMode, String playerType) {
        this.effectPlayer = effectPlayer;
        this.gameLevel = gameLevel;
        this.gameBoard = gameBoard;
        this.gamePanel = gamePanel;
        this.ExtendMode = ExtendMode;
        this.playerType = playerType;
        gamePanel.setLevel(gameLevel);
        gamePanel.setInitialLevel(gameLevel);
        this.maxGameLevel = 11;
        this.stepsPerMove = 24;
        this.currentStep = 0;
        this.isAIPlayer = isAIPlayer;
        this.isExternalPlayer = isExternalPlayer;
        this.handleMoveFacade = new HandleMoveFacade(gameBoard, effectPlayer, sound);
        this.app = app;  // Assign the app reference

        if (isAIPlayer) {
            ai = new TetrisAI();  // Initialize AI
            lastMoveTime = System.currentTimeMillis();  // Initialize the last move time
        }

        // Set up the game loop timer
        int time = (int) (500 / (gameLevel * 0.4));
        timer = new Timer(time / stepsPerMove, e -> updateGame()); //lambda
        timer.start();
    }

    public void updateGame() {
        long currentTime = System.currentTimeMillis();

        // Handle AI Player moves using the facade
        if (isAIPlayer) {
            handleMoveFacade.handleAIMove(ai, currentTime);  // Use facade for AI moves
        }

        // Handle External Player moves using the facade
        if (isExternalPlayer) {
            if (!handleMoveFacade.isExternalMoveInProgress()) {
                // Only call getResponse when no move is in progress for the external player
                GameLoop.Operation externalMoveResponse = getResponse(
                        gameBoard.getWidth(),
                        gameBoard.getHeight(),
                        gameBoard.getBoard(),
                        gameBoard.getCurrentPiece().getCurrentShape(),
                        gameBoard.getNextPiece().getCurrentShape()
                );
                handleMoveFacade.handleEMove(externalMoveResponse, currentTime);  // Use facade for external player moves
            } else {
                // Continue handling the current move if it's still in progress
                handleMoveFacade.handleEMove(null, currentTime);
            }
        }

        // Handle piece dropping logic as usual
        if (currentStep < stepsPerMove - 1 && gameBoard.canMovePieceDown()) {
            currentStep++;
        } else {
            currentStep = 0;
            if (!gameBoard.movePieceDown()) {
                if (gameBoard.isGameOver()) {
                    timer.stop();
                    gamePanel.repaint();
                    gamePanel.requestFocusInWindow();
                    gamePanel.setGameOver(true);
                    updateScore();
                } else {
                    if (!gameBoard.spawnNewPiece()) {
                        timer.stop();
                        updateScore();
                        gamePanel.setGameOver(true);
                        gamePanel.repaint();
                    }
                    handleMoveFacade.resetMoveState();  // Reset state after spawning a new piece

                    int score = gameBoard.getScore();
                    int lines = gameBoard.getLines();
                    if ((lines - TotalLines) >= 10) {
                        gameLevel++;
                        TotalLines = lines;
                        int time = (int) (500 / (gameLevel * 0.4));
                        timer.setDelay(time / stepsPerMove);
                    }
                    gamePanel.setPartialMove(currentStep, stepsPerMove);
                    gamePanel.setScore(score);
                    gamePanel.setLines(lines);
                    gamePanel.setLevel(gameLevel);
                }
            }
        }

        gamePanel.setPartialMove(currentStep, stepsPerMove);
        gamePanel.repaint();
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
    private boolean connected = false;
    private void showErrorDialog(String message) {
        if (isDialogOpen){
            return;
        }
        isDialogOpen = true;
        JFrame frame = new JFrame("Error");
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> { //lambda
            frame.dispose();
            isDialogOpen = false;
            if (!connected) {
                ConnectToServer();
            }
        });


        JPanel panel = new JPanel();
        panel.add(new JLabel(message));
        panel.add(okButton);
        frame.add(panel);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }



    public void ConnectToServer() {
            try {
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                if (socket != null) {
                    this.socket = socket;
                    connected = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                if(!isDialogOpen){
                    showErrorDialog("You need to start the Tetris server to play this mode.");
                }
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
    public void setSound(boolean sound){
        this.sound = sound;
    }

    private void updateScore(){
        HighScoreScreen highScoreScreen = HighScoreScreen.getInstance();
        HighScoreManager scoreManager = highScoreScreen.scoreManager();
        ArrayList<HighScore> rr = (ArrayList<HighScore>) scoreManager.getHighScores();
        if (rr.size() < 10 || rr.get(rr.size() - 1).getScore() < gameBoard.getScore()){
            int GameType = (ExtendMode) ? 1:0;
            String name = inputName();
            GameConfig config = new GameConfig(gameBoard.getWidth(), gameBoard.getHeight(), gamePanel.getInitialLevel(), app.music(), app.sound(), ExtendMode, playerType, GameType );
            scoreManager.addScore(name, gameBoard.getScore(), config);
            highScoreScreen.updateScoreDisplay();
        }
    }

    private String inputName() {
        // Create a panel to hold the input field and button
        JPanel panel = new JPanel(new FlowLayout());

        // Create a label and text field for name input
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        // Add components to the panel
        panel.add(nameLabel);
        panel.add(nameField);

        // Loop until a valid name is entered
        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Enter Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                if (!name.isEmpty()) {
                    return name;
                } else {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Handle cancel action if needed
                return null; // or you can throw an exception or handle it differently
            }
        }
    }

}
