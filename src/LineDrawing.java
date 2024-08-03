import javax.swing.*;
import java.awt.*;

public class LineDrawing extends JComponent {
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
