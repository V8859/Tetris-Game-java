package TetrisConfiguration;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UtilityA {

    private static void buttonConfig(JButton btn){
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Verdana", Font.BOLD, 13));
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        buttonConfig(button);

        // Add mouse listener for hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }
        });

        return button;
    }

    public static JPanel createCustomSlider(String labelText, int min, int max, int initial) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.black);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Add some padding around components
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.white);
        label.setPreferredSize(new Dimension(150, label.getPreferredSize().height));
        panel.add(label, c);

        c.gridx = 3;
        c.gridy = 0;
        JLabel valueLabel = new JLabel("" + initial);
        valueLabel.setForeground(Color.white);
        panel.add(valueLabel, c);

        c.gridx = 1;
        c.gridy = 0;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridwidth = 6;

        JSlider slider = new JSlider(min, max, initial);
        slider.setBackground(Color.black);
        slider.setForeground(Color.WHITE);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setPreferredSize(new Dimension(300, 50));

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valueLabel.setText("" + slider.getValue());
            }
        });

        panel.add(slider, c);

        return panel;
    }
    public static JPanel createOptions(String LabelText){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.black);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Add some padding around components
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(LabelText);
        label.setForeground(Color.white);
        label.setPreferredSize(new Dimension(150, label.getPreferredSize().height));
        panel.add(label,c);

        c.gridx = 1;
        c.gridy = 0;
        JCheckBox tickbox = new JCheckBox();
        tickbox.setBackground(Color.black);
        tickbox.setForeground(Color.CYAN);
        tickbox.setPreferredSize(new Dimension(300,50));
        panel.add(tickbox, c);


        c.gridx=2;
        c.gridy =0;
        JLabel currentStatus = new JLabel();
        currentStatus.setForeground(Color.white);
        currentStatus.setText("On");
        panel.add(currentStatus);

        tickbox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tickbox.isSelected()){
                    currentStatus.setText("On");
                }else {
                    currentStatus.setText("Off");
                }
            }
        });
        return panel;
    }

    public static JPanel PlayerSetting(String LabelText){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.black);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Add some padding around components
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(LabelText);
        label.setForeground(Color.white);
        label.setPreferredSize(new Dimension(150, label.getPreferredSize().height));
        panel.add(label,c);

        c.gridx = 1;
        c.gridy = 0;
        JRadioButton Human = new JRadioButton("Human", true);
        Human.setBackground(Color.black);
        Human.setForeground(Color.CYAN);
        panel.add(Human, c);


        c.gridx = 2;
        c.gridy = 0;
        JRadioButton Ai = new JRadioButton("Ai");
        Ai.setBackground(Color.black);
        Ai.setForeground(Color.CYAN);
        panel.add(Ai, c);


        c.gridx = 3;
        c.gridy = 0;
        JRadioButton External = new JRadioButton("External");
        External.setBackground(Color.black);
        External.setForeground(Color.CYAN);
        panel.add(External, c);

        ButtonGroup bg = new ButtonGroup();
        bg.add(Human);
        bg.add(Ai);
        bg.add(External);

        return panel;
    }

    public static void DynamicFrameAdjustment(int height, int width, boolean ExtendMode, boolean gamePurpose){
        JFrame parentFrame = getFrameByTitle("Tetris");
        int wid;
        int hei;
        double adjuster = 1;
        double hdjuster = 1;
        if (gamePurpose){
            if (width<10){
                adjuster = (40 * ((double) 4 /width));
                System.out.println(adjuster);
            }
            if (height <24){
                hdjuster = (40* ((double) 4/height));

            }

            wid = width *(60 + (int) adjuster);
            hei = height *(30 + (int)hdjuster);
            if (ExtendMode){
                wid = wid*2;
            }
        }else{
            wid = width;
            hei = height;
        }

        try {
            if (parentFrame != null){
                parentFrame.setSize(new Dimension(wid, hei ));
            }else{
                throw new Exception("No such frame exists");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JFrame getFrameByTitle(String title) {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof JFrame && title.equals(frame.getTitle())) {
                return (JFrame) frame;
            }
        }
        return null;
    }
    public static void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y-10);
    }


}
