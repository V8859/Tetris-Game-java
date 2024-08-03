import javax.swing.*;
import java.awt.*;

public class gamePanel extends JPanel {

    // Constructor for the gamePanel class
    public gamePanel() {
        // Set the layout for the game panel
        setLayout(new BorderLayout());

        // Create and add the LineDrawing component
        LineDrawing board = new LineDrawing();
        add(board, BorderLayout.CENTER);
    }

    // Override the paintComponent method if needed
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Custom drawing code here
    }
}