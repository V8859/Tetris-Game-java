import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Main_menu r = new Main_menu();

        frame.add(r.CardPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // Center component

//                panel.add(lineDrawing, gbc); // Add LineDrawing to panel with constraints

//                frame.getContentPane().add(panel);
        frame.setSize(800, 1000);
        frame.setVisible(true);
    }
}
