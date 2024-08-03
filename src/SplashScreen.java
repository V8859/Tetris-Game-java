import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SplashScreen extends JPanel {
    public JProgressBar pbar ;

    public SplashScreen(){
        setLayout(new BorderLayout());
        pbar = new JProgressBar();
        pbar.setBounds(20,20,20,20);
        pbar.setMinimum(0);
        pbar.setMaximum(100);
        pbar.setStringPainted(true);
        add(pbar, BorderLayout.CENTER);
    }
}
