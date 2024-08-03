import javax.swing.*;
import java.awt.*;

class LineDrawing extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(100, 100, 100, 500);
        g.drawLine(400, 100, 400, 500);
        g.drawLine(100,500, 400,500);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 600); // Set the preferred size of LineDrawing
    }
}






public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My first JFrame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout()); // Use GridBagLayout

        LineDrawing lineDrawing = new LineDrawing();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // Center component

        panel.add(lineDrawing, gbc); // Add LineDrawing to panel with constraints

        frame.getContentPane().add(panel);

        frame.setSize(1920, 1090);
        frame.setVisible(true);
    }
}