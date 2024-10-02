import TetrisConfiguration.UtilityA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?",
                        "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });
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
