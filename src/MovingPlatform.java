import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.*;

// class for moving stone platforms
public class MovingPlatform extends Collidable{

    private BufferedImage image;
    public static final int UP = 1;
    public static final int DOWN = 2;
    private int speed = 4 ;
    private int direction;
    private int distance_counter;
    public static final int MAX_DISTANCE = 560;



    public MovingPlatform(int c, int r, int direction){
    	// platform coordinates, width, and height
        super(new Rectangle(c*20,r*20,80,40));
        this.direction=direction;
        distance_counter=0;


        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("Images\\Rock.png"));
        }
        catch(Exception e)
        {
            System.out.println("Error loading platform image.");
            e.printStackTrace();
        }

    }
    public int getDirection()
    {
        return direction;
    }

    public int getDistance() {
        return distance_counter;
    }

    public BufferedImage getImage()
    {
        return image;
    }
  

    public void update(){
    	// changes direction at bounds
        if(direction==DOWN&&distance_counter>=MAX_DISTANCE)
        {
            direction=UP;
            distance_counter=0;
        }
        else if(direction==UP&&distance_counter>=MAX_DISTANCE)
        {
            direction=DOWN;
            distance_counter=0;
        }
        // set coordinates based on speed, direction, and current position
        else if(direction==DOWN)
        {
            this.setRectangle(new Rectangle((int)getRectangle().getX(),(int)getRectangle().getY()+speed,(int)getRectangle().getWidth(),(int)getRectangle().getHeight()));
            distance_counter+=speed;
        }
        else if(direction==UP)
        {
            this.setRectangle(new Rectangle((int)getRectangle().getX(),(int)getRectangle().getY()-speed,(int)getRectangle().getWidth(),(int)getRectangle().getHeight()));
            distance_counter+=speed;
        }
    }

    public int getHeight(){
        return 40;
    }

    public int getWidth(){
        return 80;
    }
    public int getSpeed(){
        return speed;
    }
}
