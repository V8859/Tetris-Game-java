import TetrisConfiguration.UtilityA;
import jdk.nio.mapmode.ExtendedMapMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TetrisMultiPlayer extends JPanel {

    private TetrisApp p1;
    private TetrisApp p2;
    private boolean gpause1;
    private JPanel panel;
    public TetrisMultiPlayer(int boardWidth, int boardHeight, int gameLevel, boolean Music, boolean Sound, boolean ExtendMode, String p1AI, String p2AI, long seed) {
        UtilityA.DynamicFrameAdjustment(boardHeight, boardWidth, ExtendMode, true);
        setLayout(new BorderLayout());
        this.panel = this;

        JPanel gameArea = new JPanel(new GridLayout(1,2));
        p1 = new TetrisApp(boardWidth, boardHeight, gameLevel, Music, Sound, checkAi(p1AI), seed, "Player 1", p1AI);
        gameArea.add(p1);
        if (ExtendMode){
            p2 = new TetrisApp(boardWidth, boardHeight, gameLevel, Music, Sound, checkAi(p1AI), seed, "Player 2", p2AI);
            gameArea.add(p2);
        }
        this.add(gameArea, BorderLayout.CENTER);
        JButton MainMenu = UtilityA.createButton("Main Menu");
        this.add(MainMenu, BorderLayout.SOUTH);
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
}