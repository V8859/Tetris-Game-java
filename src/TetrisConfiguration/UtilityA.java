package TetrisConfiguration;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UtilityA {

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
