import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class gamePanel extends JPanel{
    private int boardWidth;
    private int boardHeight;
    private int gridCellSize;

    // Constructor
    public gamePanel(int width, int height) {
        // Set up the panel's properties, such as size, background color, etc.
        setBackground(Color.BLACK);  // Example: setting the background color to black
        setFocusable(true); // Ensures the panel can receive keyboard inputs
        this.boardWidth = width;
        this.boardHeight = height;
        this.gridCellSize = 25;

    }

    // Override paintComponent method
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawScore(g);
        // Example: Drawing a sample piece at a fixed position
        int[][] currentPiece = {
                {1, 0, 1},
                {1, 1, 1}
        };
        int pieceX = 2;  // Position on the board (column)
        int pieceY = 0;  // Position on the board (row)

        drawPiece(g, currentPiece, pieceX, pieceY);
    }

    private void drawBoard(Graphics g) {
        int xOffset = (getWidth() - boardWidth * gridCellSize) / 2;
        int yOffset = 50;

        // Draw the grid
        g.setColor(Color.GRAY);  // Set color for grid lines
        g.setColor(Color.GRAY);  // Set color for grid lines
        for (int x = 0; x <= boardWidth; x++) {
            g.drawLine(xOffset + x * gridCellSize, yOffset, xOffset + x * gridCellSize, yOffset + boardHeight * gridCellSize);
        }
        for (int y = 0; y <= boardHeight; y++) {
            g.drawLine(xOffset, yOffset + y * gridCellSize, xOffset + boardWidth * gridCellSize, yOffset + y * gridCellSize);
        }
    }

    private void drawScore(Graphics g) {
        int score = 12345;  // Example score

        g.setColor(Color.WHITE);  // Set color for the text
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Set font for the score
        g.drawString("Score: " + score, 350, 30);  // Draw score at position (350, 30)
    }

    private void drawPiece(Graphics g, int[][] piece, int x, int y) {
        int xOffset = (getWidth() - boardWidth * gridCellSize) / 2;
        int yOffset = 50;

        g.setColor(Color.RED);  // Set the color for the piece
        int margin = 1;  // Margin inside each cell to avoid overlap

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != 0) {
                    g.fillRect(
                            xOffset + (x + j) * gridCellSize + margin,
                            yOffset + (y + i) * gridCellSize + margin,
                            gridCellSize - 2 * margin,
                            gridCellSize - 2 * margin
                    );
                }
            }
        }
    }
}
