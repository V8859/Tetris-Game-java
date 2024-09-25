import TetrisConfiguration.UtilityA;
import jdk.nio.mapmode.ExtendedMapMode;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TetrisMultiPlayer extends JPanel {

    private TetrisApp p1;
    private TetrisApp p2;
    private boolean gpause1;
    private JPanel panel;
    private JLayeredPane layeredPane;
    private OverlayPanel overlayPanel;
    private int TWidth;
    private int THeight;

    public TetrisMultiPlayer(int boardWidth, int boardHeight, int gameLevel, boolean Music, boolean Sound, boolean ExtendMode, String p1AI, String p2AI, long seed) {
        UtilityA.DynamicFrameAdjustment(boardHeight, boardWidth, ExtendMode, true);
        overlayPanel = new OverlayPanel();
        setLayout(new BorderLayout());
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300,300));
        this.panel = this;

        JPanel gameArea = new JPanel(new GridLayout(1,2));
        p1 = new TetrisApp(boardWidth, boardHeight, gameLevel, Music, Sound, checkAi(p1AI), seed, "Player 1", p1AI);
        gameArea.add(p1);
        if (ExtendMode){
            p2 = new TetrisApp(boardWidth, boardHeight, gameLevel, Music, Sound, checkAi(p1AI), seed, "Player 2", p2AI);
            gameArea.add(p2);
        }
        THeight = 1;
        TWidth = 1;
        try {
            JFrame parent = UtilityA.getFrameByTitle("Tetris");
            if (parent != null){
                THeight = parent.getHeight();
                TWidth = parent.getWidth();
            }else{
                throw new Exception("No parent frame found");
            }
        }catch (Exception e){
            System.out.println();
            e.printStackTrace();
        }
        gameArea.setBounds(0,0, TWidth,THeight);
        layeredPane.add(gameArea, JLayeredPane.DEFAULT_LAYER);
        this.add(layeredPane, BorderLayout.CENTER);
        JButton MainMenu = UtilityA.createButton("Main Menu");
        this.add(MainMenu, BorderLayout.SOUTH);
//        this.add(MainMenu, BorderLayout.SOUTH);
        MainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel parent = (JPanel) getParent();
                CardLayout cardLayout = (CardLayout) parent.getLayout();
                p1.gameLoop().setPause();
                if (ExtendMode){
                    p2.gameLoop().setPause();
                }
                MainMenuConfirmationScreen back_toMenu = new MainMenuConfirmationScreen(cardLayout, parent, p1, p2, ExtendMode, panel);
            }
        });

        overlayPanel.setBounds(0,0,TWidth,THeight);
        overlayPanel.setOpaque(false);
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);


        ControlScheme rs = new ControlScheme(p1, p2, this, ExtendMode);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setVisible(true);
    }
    public boolean checkAi(String status){
        if (Objects.equals(status, "Ai")){
            return true;
        }
        return false;
    }
    public boolean checkHuman(String status){
        if(Objects.equals(status, "Human")){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkExternal(String status){
        if(Objects.equals(status, "External")){
            return true;
        }else{
            return false;
        }
    }
    public OverlayPanel getOverlayPane(){
        return this.overlayPanel;
    }

    protected class OverlayPanel extends JPanel{
        private String music_status;
        private String sound_status;
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("HelveticaNeue", Font.BOLD, 18));
            g.drawString("Sound: "+sound_status, (TWidth/2)-80,20);
            g.drawString("Music: "+music_status, (TWidth/2)+20,20);
        }
        public void setMusic_status(String status){
            this.music_status=status;
            this.repaint();
        }
        public void setSound_status(String status){
            this.sound_status = status;
            this.repaint();
        }

    }

}