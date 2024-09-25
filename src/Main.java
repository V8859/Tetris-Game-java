import TetrisConfiguration.UtilityA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Main_menu r = new Main_menu();

        frame.add(r.CardPanel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                UtilityA.centerFrame(frame);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // Center component

//                panel.add(lineDrawing, gbc); // Add LineDrawing to panel with constraints

//                frame.getContentPane().add(panel);
        frame.setSize(800, 1000);
        frame.setVisible(true);
    }
}
