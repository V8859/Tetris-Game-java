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
        // Define the size of the grid cells
        int cellSize = 30;  // Each cell is 30x30 pixels

        // Draw the grid
        g.setColor(Color.GRAY);  // Set color for grid lines
        for (int x = 0; x <= this.boardWidth; x++) {
            g.drawLine(x * cellSize, 0, x * cellSize, this.boardHeight * cellSize); // Vertical lines
        }
        for (int y = 0; y <= this.boardHeight; y++) {
            g.drawLine(0, y * cellSize, this.boardWidth * cellSize, y * cellSize); // Horizontal lines
        }
    }
}
