import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My first JFrame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        GameForm rr = new GameForm();

        JPanel panel = new JPanel(new GridBagLayout()); // Use GridBagLayout

//        LineDrawing lineDrawing = new LineDrawing();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // Center component

//        panel.add(lineDrawing, gbc); // Add LineDrawing to panel with constraints

//        panel.add(rr);
        frame.getContentPane().add(panel);
        frame.setSize(1920, 4040);
        frame.setVisible(true);
    }
}