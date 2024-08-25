import TetrisConfiguration.Configuration;
import TetrisConfiguration.UtilityA;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_menu {
    public JPanel main_menu;
    public JPanel CardPanel;
    public CardLayout cardLayout;

    // Constructor for the main menu class
    public Main_menu() {
        cardLayout = new CardLayout();
        CardPanel = new JPanel(cardLayout);

        // Create the game panel

        // Set the default properties for all buttons
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(10, 20, 10, 20));

        main_menu = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Add vertical gap between buttons

        // Load the image
        ImageIcon titleIcon = new ImageIcon("src/TetrisConfiguration/TetrisWorld.png");
        JLabel titleLabel = new JLabel(titleIcon);

        // Add the title image to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        main_menu.add(titleLabel, gbc);

        JButton playButton = UtilityA.createButton("Play");
        JButton configButton = UtilityA.createButton("Configuration");
        JButton highscoresButton = UtilityA.createButton("Highscores");
        JButton exitButton = UtilityA.createButton("Exit");

        gbc.gridy++;
        main_menu.add(playButton, gbc);

        gbc.gridy++;
        main_menu.add(configButton, gbc);

        gbc.gridy++;
        main_menu.add(highscoresButton, gbc);

        gbc.gridy++;
        main_menu.add(exitButton, gbc);


        JTable controlsTable = getjTable();
        gbc.insets = new Insets(0, 0, 0, 0);

        controlsTable.setRowSelectionAllowed(false);
        controlsTable.setColumnSelectionAllowed(false);
        controlsTable.setCellSelectionEnabled(false);
        controlsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        controlsTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        controlsTable.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < controlsTable.getColumnModel().getColumnCount(); i++) {
            controlsTable.getColumnModel().getColumn(i).setResizable(false);
        }

        gbc.gridy++;
        main_menu.add(controlsTable.getTableHeader(), gbc);

        gbc.gridy++;
        main_menu.add(controlsTable, gbc);


        SplashScreen splash = new SplashScreen();
        Configuration gameConfig = new Configuration();
        HighScoreScreen highscores = new HighScoreScreen();

        // Add panels to the card panel
        CardPanel.add(main_menu, "Main Menu");
        CardPanel.add(splash, "Splash" );
        CardPanel.add(gameConfig,"Config");
        CardPanel.add(highscores, "highscore");
        // Add action listener to the play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(()->{
                    TetrisApp game = new TetrisApp(gameConfig.getW(), gameConfig.getH(), gameConfig.getLvl(), gameConfig.getMusicState(), gameConfig.getSoundState());
                    CardPanel.add(game, "Tetris Game");
                    cardLayout.show(CardPanel, "Tetris Game");
                    game.setFocusable(true);
                    game.requestFocusInWindow();
                });
//                For testing purposes
//                System.out.println(gameConfig.getW());
//                System.out.println(gameConfig.getH());
//                System.out.println(gameConfig.getLvl());
//                System.out.println(gameConfig.getMusicState());
//                System.out.println(gameConfig.getSoundState());
//                System.out.println(gameConfig.getAIState());
//                System.out.println(gameConfig.getExtendModeState());
            }
        });


        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardPanel, "Config");

            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExitScreen exit = new ExitScreen();

            }
        });
        highscoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardPanel, "highscore");
            }
        });
        cardLayout.show(CardPanel, "Splash");
        Timer timer = new Timer(100, new ActionListener() {
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

    private static JTable getjTable() {
        String[][] Data = {{"↑", "Rotate Block"}, {"↓", "Speed Up"}, {"←", "Move Left"}, {"→", "Move Right"},{"P","Pause"},{"M", "Mute/Unmute Music"},{"S", "Mute/Unmute Sound"}};
        String[] ColumnNames = {"Key", "Action"};

        DefaultTableModel model = new DefaultTableModel(Data, ColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };



        JTable controlsTable = new JTable(model);
        controlsTable.setBounds(30, 40, 200, 300);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < controlsTable.getColumnCount(); i++) {
            controlsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return controlsTable;
    }
}
