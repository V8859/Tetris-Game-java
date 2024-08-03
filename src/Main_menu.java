import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Main_menu {
    public JPanel main_menu;

    // Constructor for the main menu class
    public Main_menu() {
        // Set the default properties for all buttons
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(10, 20, 10, 20));

        main_menu = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Add vertical gap between buttons

        JButton playButton = createButton("Play");
        JButton configButton = createButton("Configuration");
        JButton highscoresButton = createButton("Highscores");
        JButton exitButton = createButton("Exit");

        // Add buttons to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        main_menu.add(playButton, gbc);

        gbc.gridy++;
        main_menu.add(configButton, gbc);

        gbc.gridy++;
        main_menu.add(highscoresButton, gbc);

        gbc.gridy++;
        main_menu.add(exitButton, gbc);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add mouse listener for hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }
        });

        return button;
    }
}