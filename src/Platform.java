import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.*;


// class for static dirt platforms
public class Platform extends Collidable
{
    private BufferedImage image;

    public Platform(int c, int r)
    {
    	// platform coordinates, width, and height
        super(new Rectangle(c*20,r*20,20,20));

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

