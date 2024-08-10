import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class gamePanel extends JPanel{
    private int boardWidth;
    private int boardHeight;


    // Constructor
    public gamePanel(int width, int height) {
        // Set up the panel's properties, such as size, background color, etc.
        setBackground(Color.BLACK);  // Example: setting the background color to black
        setFocusable(true); // Ensures the panel can receive keyboard inputs
        this.boardWidth = width;
        this.boardHeight = height;
    }

    // Override paintComponent method
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawScore(g);
        // Example: Drawing a sample piece at a fixed position
        int[][] currentPiece = {
                {1, 1},
                {1, 1}
        };
        int pieceX = 4;  // Position on the board (column)
        int pieceY = 0;  // Position on the board (row)

        drawPiece(g, currentPiece, pieceX, pieceY);
    }

    private void drawBoard(Graphics g) {
        int cellSize = 30;  // Each cell is 30x30 pixels

        // Draw the grid
        g.setColor(Color.GRAY);  // Set color for grid lines
        for (int x = 0; x <= boardWidth; x++) {
            g.drawLine(x * cellSize, 0, x * cellSize, boardHeight * cellSize); // Vertical lines
        }
        for (int y = 0; y <= boardHeight; y++) {
            g.drawLine(0, y * cellSize, boardWidth * cellSize, y * cellSize); // Horizontal lines
        }
    }

    private void drawScore(Graphics g) {
        int score = 12345;  // Example score

        g.setColor(Color.WHITE);  // Set color for the text
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Set font for the score
        g.drawString("Score: " + score, 350, 30);  // Draw score at position (350, 30)
    }

    private void drawPiece(Graphics g, int[][] piece, int x, int y) {
        g.setColor(Color.RED);  // Set the color for the piece
        int cellSize = 30;  // Same size as defined earlier

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != 0) {
                    g.fillRect((x + j) * cellSize, (y + i) * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}
