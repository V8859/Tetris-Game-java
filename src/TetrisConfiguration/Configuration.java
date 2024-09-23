package TetrisConfiguration;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.jar.JarEntry;

public class Configuration extends JPanel {
    public Configuration() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Add some padding around components
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        JLabel ConfigTitle = new JLabel("Configuration");
        ConfigTitle.setForeground(Color.WHITE);
        ConfigTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        ConfigTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(ConfigTitle, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        JPanel widthSliderPanel = UtilityA.createCustomSlider("Field Width(No. of Cells):    ", 5, 15, 5);
        JSlider widthSlider = (JSlider) widthSliderPanel.getComponent(2);
        widthSlider.setValue(10);
        this.add(widthSliderPanel, c);

        c.gridx=0;
        c.gridy=2;
        c.anchor = GridBagConstraints.WEST;
        JPanel heightSliderPanel = UtilityA.createCustomSlider("Field Height (No. of cells):  ", 15,30,15);
        JSlider heightSlider = (JSlider) heightSliderPanel.getComponent(2);
        heightSlider.setValue(20);
        this.add(heightSliderPanel,c);

        c.gridx=0;
        c.gridy=3;
//        c.anchor=GridBagConstraints.WEST;
        JPanel GameLevel = UtilityA.createCustomSlider("Game  Level:", 1,10,1);
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
//        this.add(AiPlay, c);



        c.gridx = 0;
        c.gridy = 7;
        JPanel extendMode = UtilityA.createOptions("Extend Mode(On/Off):");
        JCheckBox ExtendModeState = (JCheckBox) extendMode.getComponent(1);

        this.add(extendMode, c);
        c.gridx = 0;
        c.gridy = 8;
        JPanel playerOne = UtilityA.PlayerSetting("Player One");
        this.add(playerOne, c);

        c.gridx = 0;
        c.gridy = 9;
        JPanel playerTwo = UtilityA.PlayerSetting("Player Two");
        disablePanel(playerTwo);
        this.add(playerTwo, c);

        ExtendModeState.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    EnablePanel(playerTwo);
                }else{
                    disablePanel(playerTwo);
                }
            }
        });

        c.gridx = 0;
        c.gridy = 20;
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

    public boolean getExtendModeState(){
        JPanel ExtendModeStatePanel = (JPanel) this.getComponent(6);
        JCheckBox ExtendModeState = (JCheckBox) ExtendModeStatePanel.getComponent(1);
        return ExtendModeState.isSelected();
    }

    public void disablePanel(JPanel playerPanel){
        Component [] components = playerPanel.getComponents();
        playerPanel.setEnabled(false);
        for (Component component: components){
            component.setEnabled(false);
        }
    }

    public void EnablePanel(JPanel playerPanel){
        Component [] components = playerPanel.getComponents();
        playerPanel.setEnabled(true);
        for (Component component: components){
            component.setEnabled(true);
        }
    }

    public String getP1Status(){
        JPanel p1Panel = (JPanel) this.getComponent(7);
        Component[] components = p1Panel.getComponents();
        int i = 0;
        for (Component component : components) {
            if (i != 0) {

            JRadioButton comp = (JRadioButton) component;
            if (comp.isSelected()) {
                return (String) comp.getText();
            }
        }i++;
        }
        return "s";
    }


    public String getP2Status(){
        JPanel p2Panel = (JPanel) this.getComponent(8);
        Component[] components = p2Panel.getComponents();
        int i = 0;
        for (Component component : components){
            if (i!=0){
                JRadioButton comp = (JRadioButton) component;
                if (comp.isSelected()){
                    return (String) comp.getText();
                    }
                }
                i++;
            }

        return "s";
    }


}
//complete