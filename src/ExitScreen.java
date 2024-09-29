import TetrisConfiguration.UtilityA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitScreen extends JFrame{
    public ExitScreen() {
        setTitle("Exit Confirmation");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Label
        JLabel exitLabel = new JLabel("Are you sure you want to exit?", SwingConstants.CENTER);
        add(exitLabel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton yesButton = UtilityA.createButton("Yes");
        JButton noButton = UtilityA.createButton("No");

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        yesButton.addActionListener(e -> System.exit(0)); //lambda

        noButton.addActionListener(e -> { //lambda
            new Main_menu();  // Go back to the main menu
            dispose();
        });


        setVisible(true);
    }
}