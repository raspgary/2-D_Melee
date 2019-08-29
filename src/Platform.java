
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.*;
import java.io.*;

public class Platform extends Collidable
{
    private BufferedImage image;
    private boolean jumpTransparency = false; //if false means player can not jump onto platform from underneath

    public Platform(int c, int r)
    {
        super(new Rectangle(c*20,r*20,20,20)); //platform width and height debatable

        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("Images\\platform.png"));
        }
        catch(Exception e)
        {
            System.out.println("Error loading platform image.");
            e.printStackTrace();
        }
    }

    public BufferedImage getImage()
    {
        return image;
    }
}

