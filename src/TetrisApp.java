import TetrisConfiguration.MainMenuConfirmationScreen;
import TetrisConfiguration.SoundPlayer;
import TetrisConfiguration.UtilityA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TetrisApp extends JPanel {
    private boolean pause;
    private JLabel pauseLabel;
    private JLayeredPane layeredPane;
    private SoundPlayer soundPlayer;
    private SoundPlayer effectPlayer;
    public TetrisApp(int boardWidth, int boardHeight, int gameLevel, boolean Music, boolean Sound){
        GameBoard gameBoard = new GameBoard(boardWidth, boardHeight);
        GamePanel gamePanel = new GamePanel(gameBoard);
        GameLoop gameLoop = new GameLoop(gameBoard, gamePanel, gameLevel);  // Pass both gameBoard and gamePanel
//        soundPlayer = new SoundPlayer();
//        soundPlayer.loadSound("src/music.wav");
        effectPlayer = new SoundPlayer();
        effectPlayer.loadSound("src/TetrisConfiguration/MovePiece.wav");
        if(Music){
            soundPlayer.playSound();
            soundPlayer.loopSound();
        }


        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 800));


        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800,800));
        this.add(layeredPane, BorderLayout.CENTER);
        gamePanel.setBounds(0,0, 800,800);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        pauseLabel= new JLabel("Paused");
        pauseLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setBackground(Color.white);
        pauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pauseLabel.setBounds(0,0,800,800);
        pauseLabel.setVisible(false);
        layeredPane.add(pauseLabel, JLayeredPane.PALETTE_LAYER);

        JButton MainMenu = UtilityA.createButton("Main Menu");
        this.add(MainMenu, BorderLayout.SOUTH);
        MainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            JPanel parent = (JPanel) getParent();
            CardLayout cardLayout = (CardLayout) parent.getLayout();
            MainMenuConfirmationScreen back_toMenu = new MainMenuConfirmationScreen(cardLayout, parent );
            }
        });

        // Add key bindings for player input
        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        this.getActionMap().put("moveLeft", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("left");
                if (Sound){
                    effectPlayer.playSound();
                }
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        this.getActionMap().put("moveRight", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("right");
                if (Sound){
                    effectPlayer.playSound();
                }
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("DOWN"), "moveDown");
        this.getActionMap().put("moveDown", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("down");
                if (Sound){
                    effectPlayer.playSound();
                }
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("UP"), "rotate");
        this.getActionMap().put("rotate", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("rotate");
                if (Sound){
                    effectPlayer.playSound();
                }
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("P"), "pause");
        this.getActionMap().put("pause", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("pause");
                pause = !pause;
                pauseLabel.setVisible(pause);
                layeredPane.revalidate();
                layeredPane.repaint();

            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getSize();
                layeredPane.setPreferredSize(size);
                gamePanel.setBounds(0,0, size.width, size.height);
                pauseLabel.setBounds(0,0, size.width, size.height);
                layeredPane.revalidate();
                layeredPane.repaint();
                super.componentResized(e);
            }
        });
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.setVisible(true);
    }

}
