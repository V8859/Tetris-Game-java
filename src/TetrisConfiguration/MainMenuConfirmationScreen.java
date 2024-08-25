package TetrisConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuConfirmationScreen extends JFrame {
//    private JFrame mainframe;
    public  MainMenuConfirmationScreen(CardLayout cardLayout, JPanel parent, SoundPlayer musicPlayer, SoundPlayer effectPlayer, JPanel game) {
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
        JButton yesButton = UtilityA.createButton("Yes");
        JButton noButton = UtilityA.createButton("No");

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parent, "Main Menu");
                effectPlayer.stopSound();
                musicPlayer.stopSound();
                dispose();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new Main_menu(); // Go back to the main menu
                game.setFocusable(true);
                game.requestFocusInWindow();
                dispose();
            }
        });

        setVisible(true);
    }

}
