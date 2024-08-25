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
    private SoundPlayer musicPlayer;
    private SoundPlayer effectPlayer;
    private boolean sound;
    private boolean music;
    private JPanel PanelReference;
    public TetrisApp(int boardWidth, int boardHeight, int gameLevel, boolean Music, boolean Sound){
        GameBoard gameBoard = new GameBoard(boardWidth, boardHeight);
        GamePanel gamePanel = new GamePanel(gameBoard);
        GameLoop gameLoop = new GameLoop(gameBoard, gamePanel, gameLevel);  // Pass both gameBoard and gamePanel
        this.sound = Sound;
        this.music = Music;
        musicPlayer = new SoundPlayer();
        musicPlayer.loadSound("src/TetrisConfiguration/Aerial City, Chika - Menu Music.wav");
        effectPlayer = new SoundPlayer();
        effectPlayer.loadSound("src/TetrisConfiguration/MovePieceAlternate.wav");
        PanelReference = this;
        if(music){
            musicPlayer.loopSound(-30.0f);
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
            MainMenuConfirmationScreen back_toMenu = new MainMenuConfirmationScreen(cardLayout, parent, musicPlayer, effectPlayer, PanelReference);
            }
        });

        // Add key bindings for player input
        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        this.getActionMap().put("moveLeft", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("left");
                if (sound){
                    effectPlayer.playSound(-20.0f);
                }
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        this.getActionMap().put("moveRight", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("right");
                if (sound){
                    effectPlayer.playSound(-20.0f);
                }
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("DOWN"), "moveDown");
        this.getActionMap().put("moveDown", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("down");
                if (sound){
                    effectPlayer.playSound(-20.0f);
                }
            }
        });

        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("UP"), "rotate");
        this.getActionMap().put("rotate", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gameLoop.handleInput("rotate");
                if (sound){
                    effectPlayer.playSound(-20.0f);
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
        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("S"),"sound-off");
        this.getActionMap().put("sound-off", new javax.swing.AbstractAction(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                sound = !sound;
            }
        });


        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("M"),"music-off");
        this.getActionMap().put("music-off", new javax.swing.AbstractAction(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                music = !music;
                if (music){
                    musicPlayer.loopSound(-30.0f);
                }else{
                    musicPlayer.stopSound();
                }
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
