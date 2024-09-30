import TetrisConfiguration.ComponentFactory;
import TetrisConfiguration.SoundPlayer;
import TetrisConfiguration.UtilityA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuConfirmationScreen extends JFrame {
//    private JFrame mainframe;
    private boolean YesOrNo;
    public  MainMenuConfirmationScreen(CardLayout cardLayout, JPanel parent, TetrisApp p1, TetrisApp p2, boolean ExtendMode, JPanel game) {
        setTitle("Exit Confirmation");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Label
        JLabel exitLabel = new JLabel("Are you sure you want to go back to Main Menu?", SwingConstants.CENTER);
        add(exitLabel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton yesButton = ComponentFactory.generateButton("Yes");
        JButton noButton = ComponentFactory.generateButton("No");

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        yesButton.addActionListener(e -> { //Lambda
            cardLayout.show(parent, "Main Menu");
            p1.effectPlayer().stopSound();
            p1.musicPlayer().stopSound();
            if (ExtendMode) {
                p2.effectPlayer().stopSound();
                p2.musicPlayer().stopSound();
            }
            dispose();
            UtilityA.DynamicFrameAdjustment(1000, 800, false, false);
        });

        noButton.addActionListener(e -> { //Lambda
            // Go back to the game and unpause
            p1.gameLoop().unPause();
            p1.pauseLabel().setVisible(false);
            if (ExtendMode) {
                p2.gameLoop().unPause();
                p2.pauseLabel().setVisible(false);
            }
            game.setFocusable(true);
            game.requestFocusInWindow();
            dispose();
        });

        setVisible(true);

    }

}
