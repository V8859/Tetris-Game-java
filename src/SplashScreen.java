import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SplashScreen extends JPanel {
    public CustomProgressBar pbar;

    public SplashScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding around the progress bar
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel pbarPanel = new JPanel();
        pbarPanel.setLayout(new BorderLayout());
        pbarPanel.setPreferredSize(new Dimension(300, 50));

        pbar = new CustomProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(100);
        pbar.setStringPainted(false);
        pbarPanel.add(pbar, BorderLayout.CENTER);

        add(pbarPanel, gbc);
    }

    // Custom Progress Bar Class
    class CustomProgressBar extends JProgressBar {
        private final Color[] colors = {
                Color.CYAN, Color.GREEN, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.RED, Color.MAGENTA
        };

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int progress = getValue();
            int colorIndex = (progress / 10) % colors.length;
            g.setColor(colors[colorIndex]);
            int width = (int) (getWidth() * (progress / 100.0));
            g.fillRect(0, 0, width, getHeight());
        }
    }
}
