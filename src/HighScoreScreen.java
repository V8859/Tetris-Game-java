import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HighScoreScreen extends JPanel {
    private JTextPane scorePane;

    public HighScoreScreen() {
        setLayout(new BorderLayout());

        // High Scores Area
        scorePane = new JTextPane();
        scorePane.setEditable(false);

        // Set a retro-style font (Monospaced), bold, and bigger size
        Font retroFont = new Font("Monospaced", Font.BOLD, 24); // Adjust the font size as needed
        scorePane.setFont(retroFont);

        //background color
        scorePane.setBackground(Color.BLACK);
        scorePane.setForeground(Color.GREEN);

        // Center text alignment
        StyledDocument doc = scorePane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        add(new JScrollPane(scorePane), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton mainMenuButton = createButton("Main Menu");

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
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/highscores.txt"))) {
            StringBuilder scores = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                scores.append(line).append("\n");
            }
            scorePane.setText(scores.toString());
        } catch (IOException e) {
            scorePane.setText("No high scores available.");
        }
    }

    private JButton createButton(String text) {
        return new JButton(text);
    }
}
