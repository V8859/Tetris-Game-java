package TetrisConfiguration;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.jar.JarEntry;

public class Configuration extends JPanel {
    public Configuration() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Add some padding around components
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        JLabel ConfigTitle = new JLabel("Configuration");
        ConfigTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        ConfigTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(ConfigTitle, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        JPanel widthSliderPanel = UtilityA.createCustomSlider("Field Width(No. of Cells):    ", 5, 15, 5);
        this.add(widthSliderPanel, c);

        c.gridx=0;
        c.gridy=2;
        c.anchor = GridBagConstraints.WEST;
        JPanel heightSliderPanel = UtilityA.createCustomSlider("Field Height (No. of cells):  ", 15,30,15);
        this.add(heightSliderPanel,c);

        c.gridx=0;
        c.gridy=3;
//        c.anchor=GridBagConstraints.WEST;
        JPanel GameLevel = UtilityA.createCustomSlider("Game  Level:                         ", 1,10,1);
        this.add(GameLevel,c);

        c.gridx = 0;
        c.gridy = 4;
        JPanel music = UtilityA.createOptions("Music(On/Off): ");
        JCheckBox MusicState = (JCheckBox) music.getComponent(1);
        MusicState.setSelected(true);
        this.add(music, c);

        c.gridx = 0;
        c.gridy = 5;
        JPanel soundEffect = UtilityA.createOptions("Sound(On/Off):");
        JCheckBox soundState =(JCheckBox) soundEffect.getComponent(1);
        soundState.setSelected(true);
        this.add(soundEffect, c);




        c.gridx = 0;
        c.gridy = 6;
        JPanel AiPlay = UtilityA.createOptions("AI Play (On/Off):");
        this.add(AiPlay, c);



        c.gridx = 0;
        c.gridy = 7;
        JPanel extendMode = UtilityA.createOptions("Extend Mode(On/Off):");
        this.add(extendMode, c);


        c.gridx = 0;
        c.gridy = 8;
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
        JPanel widthSliderPanel = (JPanel) this.getComponent(1);
        JSlider widthSlider = (JSlider) widthSliderPanel.getComponent(2);
        return widthSlider.getValue();
    }

    public int getH() {
        JPanel heightSliderPanel = (JPanel) this.getComponent(2);
        JSlider heightSlider = (JSlider) heightSliderPanel.getComponent(2);
        return heightSlider.getValue();
    }

    public int getLvl() {
        JPanel GameLevel = (JPanel) this.getComponent(3);
        JSlider GameSlider = (JSlider) GameLevel.getComponent(2);
        return GameSlider.getValue();
    }

    public boolean getMusicState(){
        JPanel MusicStatePanel = (JPanel) this.getComponent(4);
        JCheckBox MusicState = (JCheckBox) MusicStatePanel.getComponent(1);
        return MusicState.isSelected();
    }
    public boolean getSoundState(){
        JPanel SoundStatePanel = (JPanel) this.getComponent(5);
        JCheckBox SoundState = (JCheckBox) SoundStatePanel.getComponent(1);
        return SoundState.isSelected();
    }
    public boolean getAIState(){
        JPanel AIStatePanel = (JPanel) this.getComponent(6);
        JCheckBox AIState = (JCheckBox) AIStatePanel.getComponent(1);
        return AIState.isSelected();
    }

    public boolean getExtendModeState(){
        JPanel ExtendModeStatePanel = (JPanel) this.getComponent(7);
        JCheckBox ExtendModeState = (JCheckBox) ExtendModeStatePanel.getComponent(1);
        return ExtendModeState.isSelected();
    }
}
//complete