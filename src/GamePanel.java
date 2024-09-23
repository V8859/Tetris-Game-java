import TetrisConfiguration.UtilityA;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameBoard gameBoard;
    private final int cellSize = 25;  // Each cell is 30x30 pixels
    private final int scoreHeight = 50;  // Space reserved for the score display
    private int score;
    private boolean gameOver;
    private Color currentColor;
    private Image splashImage;
    private int partialMoveStep;
    private int totalSteps;


    public GamePanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        //splashImage = new ImageIcon("src/TetrisConfiguration/RainWallpaper.png").getImage();

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.gameOver = false;
        this.partialMoveStep = 0;
        this.totalSteps = 24;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void setPartialMove(int step, int totalSteps) {
        this.partialMoveStep = step;
        this.totalSteps = totalSteps;
    }


    private int getXOffset() {
        int panelWidth = getWidth();
        return (panelWidth - gameBoard.getBoard()[0].length * cellSize) / 2;
    }

    private int getYOffset() {
        return scoreHeight;  // Position the board just below the score
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the splash image as the background
//        g.drawImage(splashImage, 0, 0, getWidth(), getHeight(), this);

        drawComplexBackground(g);

        drawBoard(g);  // Draw the grid first
        drawScore(g);

        TetrisPiece currentPiece = gameBoard.getCurrentPiece();
        if (currentPiece != null) {
            drawPiece(g, currentPiece.getCurrentShape(), currentPiece.getX(), currentPiece.getY(), currentPiece.getColor());
        }

        // If the game is over, display "Game Over" text
        if (gameOver) {
            g.setColor(new Color(255, 0, 0, 122));
            g.setFont(new Font("Monospaced", Font.BOLD, 48));
            FontMetrics fm = g.getFontMetrics();
            String gameOverText = "GAME OVER";
            int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
            int y = getHeight() / 2;
            g.drawString(gameOverText, x, y);
        }
    }

    private void drawBoard(Graphics g) {
        int xOffset = getXOffset();
        int yOffset = getYOffset();
        int[][] board = gameBoard.getBoard();
        Color [][] colors = gameBoard.getColors();

        g.setColor(Color.GRAY);  // Set color for grid lines
        for (int x = 0; x <= board[0].length; x++) {
            g.drawLine(xOffset + x * cellSize, yOffset, xOffset + x * cellSize, yOffset + board.length * cellSize);
        }
        for (int y = 0; y <= board.length; y++) {
            g.drawLine(xOffset, yOffset + y * cellSize, xOffset + board[0].length * cellSize, yOffset + y * cellSize);
        }

        // Draw placed blocks on the board
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] != 0) {
                    int xPos = xOffset + x * cellSize;
                    int yPos = yOffset + y * cellSize;
                    g.setColor(colors[y][x]);
                    g.fillRect(xPos,yPos, cellSize, cellSize);
                    int size =cellSize;
                    int smallsize = (size +22)/ 2 ;
                    int smallxPos = xPos + (size - smallsize) / 2;
                    int smallyPos = yPos + (size - smallsize) / 2;
                    g.setColor(Color.black.brighter().darker().brighter().brighter());
                    g.fillRect(smallxPos, smallyPos, smallsize, smallsize);

                    int smallSize = (size +18)/ 2 ;
                    int smallXPos = xPos + (size - smallSize) / 2;
                    int smallYPos = yPos + (size - smallSize) / 2;
                    g.setColor(colors[y][x].darker().darker());
                    g.fillRect(smallXPos, smallYPos, smallSize, smallSize);
                }
            }
        }
    }

    private void drawPiece(Graphics g, int[][] piece, int x, int y, Color color) {
        g.setColor(color);
        int xOffset = getXOffset();
        int yOffset = getYOffset();
        int margin = 1;

        // Calculate the partial offset for smoother movement
        int partialYOffset = (int) ((cellSize * partialMoveStep) / (double) totalSteps);

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != 0) {
                    int xPos = xOffset + (x + j) * cellSize + margin;
                    int yPos = yOffset + (y + i) * cellSize + margin + partialYOffset;

                    int size = cellSize -2 *margin;
                    int centerX = xPos + size/2;
                    int centerY = yPos + size/2;

                    g.setColor(color.darker().darker());
                    g.fillRect(xPos, yPos, size, size);


                    int smallsize = (size +22)/ 2 ;
                    int smallxPos = xPos + (size - smallsize) / 2;
                    int smallyPos = yPos + (size - smallsize) / 2;
                    g.setColor(Color.black.brighter().darker().brighter().brighter());
                    g.fillRect(smallxPos, smallyPos, smallsize, smallsize);

                    int smallSize = (size +18)/ 2 ;
                    int smallXPos = xPos + (size - smallSize) / 2;
                    int smallYPos = yPos + (size - smallSize) / 2;
                    g.setColor(color.brighter().darker().brighter().darker());
                    g.fillRect(smallXPos, smallYPos, smallSize, smallSize);
                }
            }
        }
    }

    private void drawScore(Graphics g) {
        // Example score
  // Draw score at a fixed position (left-aligned)
    }
    private void drawComplexBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Draw a gradient background
        Color color1 = Color.BLUE;
        Color color2 = Color.CYAN;
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);  // Set color for the text
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Set font for the score
        g.drawString("Score: " + score, 10, 30);


        // Calculate the board area
        int xOffset = getXOffset();
        int yOffset = getYOffset();
        int boardWidth = gameBoard.getBoard()[0].length * cellSize;
        int boardHeight = gameBoard.getBoard().length * cellSize;

        // Draw a solid color rectangle to cover the board area
        g2d.setColor(Color.BLACK); // Set the color to match the board background
        g2d.fillRect(xOffset, yOffset, boardWidth, boardHeight);
    }



    public void setScore(int score){
        this.score = score;
    }
}
