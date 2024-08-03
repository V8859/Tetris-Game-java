
import javax.swing.*;
import java.awt.*;

public class GameForm extends JPanel{
    private JPanel MyGameForm;
    private JPanel GameArea;

    public GameForm() {
        setLayout(new BorderLayout());
        MyGameForm.setLayout(new BorderLayout());

        add(MyGameForm);

        MyGameForm.add(GameArea);
        setVisible(true);
    }
}