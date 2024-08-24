import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JPanel {
    public CustomProgressBar pbar;
    private Image splashImage;

    public SplashScreen() {
        splashImage = new ImageIcon("src/TetrisConfiguration/SplashBackground.png").getImage();
        setLayout(new GridBagLayout());
        this.setBackground(Color.black);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding around the progress bar
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel Title = new JLabel("Tetris");
        Title.setForeground(Color.white);
        Title.setFont(new Font("Verdana", Font.BOLD, 24));
        // add(Title, gbc);

        gbc.gridy = 1;
        gbc.weighty = 10;
        JPanel pbarPanel = new JPanel();
        pbarPanel.setLayout(new BorderLayout());
        pbarPanel.setPreferredSize(new Dimension(300, 50));

        pbar = new CustomProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(100);
        pbar.setStringPainted(false);
        pbarPanel.add(pbar, BorderLayout.CENTER);
        add(pbarPanel, gbc);

        // Add vertical glue to push the info labels to the bottom
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        add(Box.createVerticalGlue(), gbc);

        // Add the info labels at the bottom
        String info = "Authors: Basil Aziz | Jesse Barnett | Kartik Krishna Naidu";
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 0; // Reset weighty
        JLabel Info = createInfoLabel(info);
        add(Info, gbc);
        gbc.insets = new Insets(0, 0, 5, 0);

        gbc.gridy = 4; // Move to the next row for the second info label
        JLabel Info2 = createInfoLabel("Course code : 2805ICT | Group 20 ");
        add(Info2, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(splashImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private JLabel createInfoLabel(String text) {
        JLabel Info = new JLabel();
        Info.setForeground(Color.BLACK);
        Info.setBackground(Color.white);
        Info.setFont(new Font("Verdana", Font.BOLD, 15));
        Info.setText(text);
        return Info;
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
