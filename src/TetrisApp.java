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
    private GamePanel gamePanel;
    private GameLoop CurrentLoop;
    public TetrisApp(int boardWidth, int boardHeight, int gameLevel, boolean Music, boolean Sound, boolean isAIPlayer,boolean isExternal ,long seed,String playerTag, String playerType, boolean ExtendMode){
        musicPlayer = new SoundPlayer();
        musicPlayer.loadSound("src/TetrisConfiguration/Aerial City, Chika - Menu Music.wav");
        effectPlayer = new SoundPlayer();
        effectPlayer.loadSound("src/TetrisConfiguration/MovePieceAlternate.wav");
        this.sound = Sound;
        this.music = Music;
        GameBoard gameBoard = new GameBoard(boardWidth, boardHeight, seed);
        GamePanel gamePanel = new GamePanel(gameBoard);
        GameLoop gameLoop = new GameLoop(gameBoard, gamePanel, gameLevel, isAIPlayer, isExternal, this.effectPlayer, this, ExtendMode, playerType);  // Pass both gameBoard and gamePanel
        this.gamePanel = gamePanel;
        this.CurrentLoop = gameLoop;

        gamePanel.setPlayerTag(playerTag);
        gamePanel.setPlayerType(playerType);
        PanelReference = this;
        if(music){
            musicPlayer.loopSoundInThread(-30.0f);
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

//        JButton MainMenu = UtilityA.createButton("Main Menu");
//        this.add(MainMenu, BorderLayout.SOUTH);
//        MainMenu.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            JPanel parent = (JPanel) getParent();
//            CardLayout cardLayout = (CardLayout) parent.getLayout();
//            gameLoop.setPause();
//            MainMenuConfirmationScreen back_toMenu = new MainMenuConfirmationScreen(cardLayout, parent, musicPlayer, effectPlayer, PanelReference, gameLoop);
//            }
//        });

//         Add key bindings for player input
//        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("LEFT"), "moveLeft");
//        this.getActionMap().put("moveLeft", new javax.swing.AbstractAction() {
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                gameLoop.handleInput("left");
//                if (sound){
//                    effectPlayer.playSound(-20.0f);
//                }
//            }
//        });
//
//        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("RIGHT"), "moveRight");
//        this.getActionMap().put("moveRight", new javax.swing.AbstractAction() {
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                gameLoop.handleInput("right");
//                if (sound){
//                    effectPlayer.playSound(-20.0f);
//                }
//            }
//        });
//
//        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("DOWN"), "moveDown");
//        this.getActionMap().put("moveDown", new javax.swing.AbstractAction() {
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                gameLoop.handleInput("down");
//                if (sound){
//                    effectPlayer.playSound(-20.0f);
//                }
//            }
//        });
//
//        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("UP"), "rotate");
//        this.getActionMap().put("rotate", new javax.swing.AbstractAction() {
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                gameLoop.handleInput("rotate");
//                if (sound){
//                    effectPlayer.playSound(-20.0f);
//                }
//            }
//        });
//
//        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("P"), "pause");
//        this.getActionMap().put("pause", new javax.swing.AbstractAction() {
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                gameLoop.handleInput("pause");
//                pause = !pause;
//                pauseLabel.setVisible(pause);
//                layeredPane.revalidate();
//                layeredPane.repaint();
//
//            }
//        });
//        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("S"),"sound-off");
//        this.getActionMap().put("sound-off", new javax.swing.AbstractAction(){
//            public void actionPerformed(java.awt.event.ActionEvent e){
//                sound = !sound;
//            }
//        });
//
//
//        this.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("M"),"music-off");
//        this.getActionMap().put("music-off", new javax.swing.AbstractAction(){
//            public void actionPerformed(java.awt.event.ActionEvent e){
//                music = !music;
//                if (music){
//                    musicPlayer.loopSound(-30.0f);
//                }else{
//                    musicPlayer.stopSound();
//                }
//            }
//        });
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
    public GameLoop gameLoop(){
        return this.CurrentLoop;
    }
    public boolean sound(){
        return this.sound;
    }
    public boolean music(){
        return this.music;
    }
    public SoundPlayer effectPlayer(){
        return this.effectPlayer;
    }
    public SoundPlayer musicPlayer(){
        return this.musicPlayer;
    }
    public JPanel App(){
        return this;
    }
    public Boolean pause(){
        return this.pause;
    }
    public JLabel pauseLabel(){
        return this.pauseLabel;
    }
    public GamePanel gamePanel(){
        return this.gamePanel;
    }
    public JLayeredPane layeredPane(){
        return this.layeredPane;
    }

}


