import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Configuration extends JPanel {
    private int H; // Height of game area
    private int L; // Level of the game
    private JLabel cWidth;

    public Configuration() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Add some padding around components
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JPanel widthSliderPanel = UtilityA.createCustomSlider("Field Width(No. of Cells):    ", 5, 15, 5);
        this.add(widthSliderPanel, c);

        c.gridx=0;
        c.gridy=1;
        c.anchor = GridBagConstraints.WEST;
        JPanel heightSliderPanel = UtilityA.createCustomSlider("Field Height (No. of cells):  ", 15,30,15);
        this.add(heightSliderPanel,c);

        c.gridx=0;
        c.gridy=2;
//        c.anchor=GridBagConstraints.WEST;
        JPanel GameLevel = UtilityA.createCustomSlider("Game  Level:                         ", 1,10,1);
        this.add(GameLevel,c);

        c.gridx = 0;
        c.gridy = 3;
        JPanel music = UtilityA.createOptions("Music(On/Off): ");
        this.add(music, c);

        c.gridx = 0;
        c.gridy = 4;
        JPanel soundEffect = UtilityA.createOptions("Sound(On/Off):");
        this.add(soundEffect, c);




        c.gridx = 0;
        c.gridy = 5;
        JPanel AiPlay = UtilityA.createOptions("AI Play (On/Off):");
        this.add(AiPlay, c);



        c.gridx = 0;
        c.gridy = 6;
        JPanel extendMode = UtilityA.createOptions("Extend Mode(On/Off):");
        this.add(extendMode, c);


        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2; // Span across 2 columns
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton backButton = UtilityA.createButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "Main Menu");
            }
        });
        this.add(backButton, c);
    }

    public int getW() {
        JPanel widthSliderPanel = (JPanel) this.getComponent(0);
        JSlider widthSlider = (JSlider) widthSliderPanel.getComponent(2);
        return widthSlider.getValue();
    }

    public int getH() {
        return H;
    }

    public int getL() {
        return L;
    }
}
