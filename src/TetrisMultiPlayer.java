import TetrisConfiguration.UtilityA;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisMultiPlayer extends JPanel {

    private TetrisApp p1;
    private TetrisApp p2;
    private boolean gpause1;
    private JPanel panel;
    public TetrisMultiPlayer(int boardWidth, int boardHeight, int gameLevel, boolean Music, boolean Sound, boolean ExtendMode) {
        setLayout(new BorderLayout());
        this.panel = this;
        JPanel gameArea = new JPanel(new GridLayout(1,2));
        p1 = new TetrisApp(boardWidth, boardHeight, gameLevel, Music, Sound);
        if (ExtendMode){
            p2 = new TetrisApp(boardWidth, boardHeight, gameLevel, Music, Sound);
            gameArea.add(p2);
        }
        gameArea.add(p1);
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
}