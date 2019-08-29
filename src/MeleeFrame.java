import java.awt.*;
import java.net.MalformedURLException;
import javax.swing.*;

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

        /*// creates the JFrame
        super("Super Asian Bros Melee");

        // Sets the close button to exit the program
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // makes the window not able to be resized
        setResizable(false);
        pack();

        MeleePanel p = new MeleePanel();

        Insets frameInsets = getInsets();

        // Gets the Graphics environment
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // gets the graphics device
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        // turns on full screen mode for this window
        gd.setFullScreenWindow(this);

        // creates the desired desired display mode the parameters are (int width, int height, int bitDepth, int refreshRate)
        // so the screen will be 800x600 with 32 bit color and a refresh rate of 60
        DisplayMode dm = new DisplayMode(800,600,32,60);

        // sets the display mode
        gd.setDisplayMode(dm);

        int frameWidth = p.getWidth()
                + (frameInsets.left + frameInsets.right);
        int frameHeight = p.getHeight()
                + (frameInsets.top + frameInsets.bottom);

        setPreferredSize(new Dimension(frameWidth, frameHeight));

        // turns off the windows frame
        setUndecorated(true);


        setLayout(null);
        add(p);
        pack();
        // shows the frame
        setVisible(true);*/
    }

}
