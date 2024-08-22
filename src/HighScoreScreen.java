import TetrisConfiguration.UtilityA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class HighScoreScreen extends JPanel {
    private JTextArea scoreArea;

    public HighScoreScreen() {
//        setTitle("High Scores");
        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // High Scores Area
        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        add(new JScrollPane(scoreArea), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton mainMenuButton = UtilityA.createButton("Main Menu");

        buttonPanel.add(mainMenuButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load scores from the file
        loadScores();

        // Button Actions
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "Main Menu");
            }
        });

        setVisible(true);
    }

    private void loadScores() {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scoreArea.append(line + "\n");
            }
        } catch (IOException e) {
//            e.printStackTrace();
            scoreArea.setText("No high scores available.");
        }
    }
}