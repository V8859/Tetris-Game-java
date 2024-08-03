import javax.swing.*;
import java.awt.*;

public class main_menu_test {
    public static void main(String[] args) {
                JFrame frame = new JFrame("My first JFrame");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Main_menu r = new Main_menu();

                frame.add(r.CardPanel);

                JPanel panel = new JPanel(new GridBagLayout()); // Use GridBagLayout

                LineDrawing lineDrawing = new LineDrawing();

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.CENTER; // Center component

//                panel.add(lineDrawing, gbc); // Add LineDrawing to panel with constraints

//                frame.getContentPane().add(panel);
                frame.setSize(800, 800);
                frame.setVisible(true);
    }
}
