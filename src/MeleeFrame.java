import java.awt.*;
import java.net.MalformedURLException;
import javax.swing.*;

// creates the frame of the executable
public class MeleeFrame extends JFrame
{
    public MeleeFrame() throws MalformedURLException {
        super("Super 2-D Bros Melee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();

        MeleePanel p = new MeleePanel();

        Insets frameInsets = getInsets();
        int frameWidth = p.getWidth()
                + (frameInsets.left + frameInsets.right);
        int frameHeight = p.getHeight()
                + (frameInsets.top + frameInsets.bottom);

        setPreferredSize(new Dimension(frameWidth, frameHeight));

        setLayout(null);
        add(p);
        pack();
        setVisible(true);
    }

}
