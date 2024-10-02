package TetrisConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class BComponent {
    public abstract <T> T createComponent();
}

class ButtonComponent extends BComponent {
    private String text;

    public ButtonComponent(String text) {
        this.text = text;
    }

    private static void buttonConfig(JButton btn) {
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Verdana", Font.BOLD, 13));
    }

    @Override
    public JButton createComponent() {
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
}

class SliderComponent extends BComponent {
    private String labelText;
    private int min;
    private int max;
    private int initial;

    public SliderComponent(String labelText, int min, int max, int initial) {
        this.labelText = labelText;
        this.min = min;
        this.max = max;
        this.initial = initial;
    }

    @Override
    public JPanel createComponent() {
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

        JSlider slider = new JSlider(min, max, initial);
        slider.setBackground(Color.black);
        slider.setForeground(Color.WHITE);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setPreferredSize(new Dimension(300, 50));

        slider.addChangeListener(e -> valueLabel.setText("" + slider.getValue())); //lambda

        panel.add(slider, c);

        return panel;
    }
}

class OptionsComponent extends BComponent {
    private String labelText;

    public OptionsComponent(String labelText) {
        this.labelText = labelText;
    }

    @Override
    public JPanel createComponent() {
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

        c.gridx = 1;
        c.gridy = 0;
        JCheckBox tickbox = new JCheckBox();
        tickbox.setBackground(Color.black);
        tickbox.setForeground(Color.CYAN);
        tickbox.setPreferredSize(new Dimension(300, 50));
        panel.add(tickbox, c);

        c.gridx = 2;
        c.gridy = 0;
        JLabel currentStatus = new JLabel();
        currentStatus.setForeground(Color.white);
        currentStatus.setText("On");
        panel.add(currentStatus);

        tickbox.addChangeListener(e -> { //lambda
            if (tickbox.isSelected()) {
                currentStatus.setText("On");
            } else {
                currentStatus.setText("Off");
            }
        });

        return panel;
    }
}

class PlayerComponent extends BComponent {
    private String labelText;

    public PlayerComponent(String labelText) {
        this.labelText = labelText;
    }

    @Override
    public JPanel createComponent() {
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

        c.gridx = 1;
        c.gridy = 0;
        JRadioButton human = new JRadioButton("Human", true);
        human.setBackground(Color.black);
        human.setForeground(Color.CYAN);
        panel.add(human, c);

        c.gridx = 2;
        c.gridy = 0;
        JRadioButton ai = new JRadioButton("Ai");
        ai.setBackground(Color.black);
        ai.setForeground(Color.CYAN);
        panel.add(ai, c);

        c.gridx = 3;
        c.gridy = 0;
        JRadioButton external = new JRadioButton("External");
        external.setBackground(Color.black);
        external.setForeground(Color.CYAN);
        panel.add(external, c);

        ButtonGroup bg = new ButtonGroup();
        bg.add(human);
        bg.add(ai);
        bg.add(external);

        return panel;
    }
}
