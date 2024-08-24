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

    public GamePanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setBackground(Color.BLACK);

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.gameOver = false;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
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

        drawBoard(g);  // Draw the grid first
        drawScore(g);

        TetrisPiece currentPiece = gameBoard.getCurrentPiece();
        if (currentPiece != null) {
            drawPiece(g, currentPiece.getCurrentShape(), currentPiece.getX(), currentPiece.getY(), currentPiece.getColor());
        }

        // If the game is over, display "Game Over" text
        if (gameOver) {
            g.setColor(new Color(255, 0, 0, 128));
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
                    g.setColor(colors[y][x]);
                    g.fillRect(xOffset + x * cellSize, yOffset + y * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    private void drawPiece(Graphics g, int[][] piece, int x, int y, Color color) {
        g.setColor(color);
        currentColor = color;
        int xOffset = getXOffset();
        int yOffset = getYOffset();
        int margin = 1;

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != 0) {
                    g.fillRect(
                            xOffset + (x + j) * cellSize + margin,
                            yOffset + (y + i) * cellSize + margin,
                            cellSize - 2 * margin,
                            cellSize - 2 * margin
                    );
                }
            }
        }
    }

    private void drawScore(Graphics g) {
        // Example score
        g.setColor(Color.WHITE);  // Set color for the text
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Set font for the score
        g.drawString("Score: " + score, 10, 30);  // Draw score at a fixed position (left-aligned)
    }
    public void setScore(int score){
        this.score = score;
    }



}
