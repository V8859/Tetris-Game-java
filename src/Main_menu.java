import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main_menu {
    public JPanel main_menu;
    public JPanel CardPanel;
    public CardLayout cardLayout;

    // Constructor for the main menu class
    public Main_menu() {
        cardLayout = new CardLayout();
        CardPanel = new JPanel(cardLayout);

        // Create the game panel
        gamePanel game = new gamePanel();

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

        SplashScreen splash = new SplashScreen();
        GameForm game1 = new GameForm();
        // Add panels to the card panel
        CardPanel.add(main_menu, "Main Menu");
        CardPanel.add(game1, "Tetris Game");
        CardPanel.add(splash, "Splash" );

        // Add action listener to the play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardPanel, "Tetris Game");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(exitButton);
                parentFrame.dispose();
            }
        });
        cardLayout.show(CardPanel, "Splash");
        Timer timer = new Timer(50, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress++;
                splash.pbar.setValue(progress);

                if(progress>=100){
                    ((Timer) e.getSource()).stop();
                    cardLayout.show(CardPanel, "Main Menu");
                }
            }
        });
        timer.start();
    }

    private void buttonConfig(JButton btn){
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
    };


    private JButton createButton(String text) {
        JButton button = new JButton(text);
        buttonConfig(button);


        // Add mouse listener for hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonConfig(button);
            }
        });

        return button;
    }
}
