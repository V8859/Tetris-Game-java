
import javax.swing.*;

public class GameForm extends JPanel{
    private JPanel MyGameForm;
    private JPanel GameArea;
    public JPanel CardPanel;

    public GameForm() {
        setContentPane(MyGameForm);
        setContentPane(GameArea);
        setTitle("Game Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        new GameForm();

    }
}