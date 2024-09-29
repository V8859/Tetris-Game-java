import TetrisConfiguration.UtilityA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HighScoreScreen extends JPanel {
    private JTable highScoreTable;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"Position", "Name", "Score", "Config"};
    private HighScoreManager highScoreManager;
    private List<HighScore> highScores;
    private JPanel parent;
    private CardLayout cardLayout;

    private static HighScoreScreen instance;

    private HighScoreScreen() {
        initUI();  // Initialize the UI components
    }

    public static HighScoreScreen getInstance() {
        if (instance == null) {
            instance = new HighScoreScreen();
        }
        return instance;
    }

    public void setBaseParameters(JPanel parent, CardLayout cardLayout, HighScoreManager highScoreManager) {
        this.parent = parent;
        this.cardLayout = cardLayout;
        this.highScoreManager = highScoreManager;
        highScores = highScoreManager.getHighScores(); // Ensure this is not null
        if (highScores == null) {
            highScores = new ArrayList<>(); // Safeguard against null values
        }
        updateScoreDisplay(); // Refresh the display with the new high scores
    }

    private void initUI() {
        // Set layout once
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("HIGH SCORES", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Table for high scores
        tableModel = new DefaultTableModel(columnNames, 0);  // No preallocated rows
        highScoreTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(highScoreTable);

        // Add the table (with scroll pane) to the center of the screen
        this.add(scrollPane, BorderLayout.CENTER);

        // Clear Scores Button
        JButton clearButton = new JButton("Clear All Scores");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to clear all high scores?",
                        "Clear High Scores", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    clearHighScores();
                }
            }
        });

        // Back Button to return to Main Menu
        JButton backButton = UtilityA.createButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "Main Menu");
            }
        });

        // Create a container to hold the buttons side by side
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER));  // Center the buttons side by side
        buttonContainer.add(clearButton);  // Add Clear button
        buttonContainer.add(backButton);   // Add Back button

        // Add the button container to the bottom of the HighScoreScreen
        this.add(buttonContainer, BorderLayout.SOUTH);
    }

    // Load the high scores from HighScoreManager
    private void loadHighScores() {
        List<HighScore> highScores = highScoreManager.getHighScores();
        tableModel.setRowCount(0);  // Clear the table
        for (int i = 0; i < highScores.size(); i++) {
            HighScore score = highScores.get(i);
            tableModel.addRow(new Object[] {
                    i + 1,
                    score.name,
                    score.score,
                    formatConfig(score.config)  // Display the formatted config here
            });
        }
    }

    // Update the table with high scores
    public void updateScoreDisplay() {
        highScores = highScoreManager.getHighScores();
        tableModel.setRowCount(0);  // Clear existing rows
        for (int i = 0; i < highScores.size(); i++) {
            HighScore score = highScores.get(i);
            String configDisplay = formatConfig(score.config);
            Object[] row = {i + 1, score.name, score.score, configDisplay};
            tableModel.addRow(row);  // Add the new row with high score data
        }
    }

    // Format the configuration to be displayed in the high score table
    private String formatConfig(GameConfig config) {
        String playerType = config.playerOneType;
        String playMode = (config.playMode == 1) ? "Double" : "Single";
        return String.format("%dx%d (Level %d) %s %s",
                config.fieldWidth, config.fieldHeight, config.initLevel, playerType, playMode);
    }

    // Clear all high scores
    private void clearHighScores() {
        highScoreManager.getHighScores().clear();  // Clear the local list of high scores
        highScoreManager.saveHighScores();  // Save the empty list to file
        updateScoreDisplay();  // Refresh the display
    }
    public HighScoreManager scoreManager(){
        return highScoreManager;
    }
}
