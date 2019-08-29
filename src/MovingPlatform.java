import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.*;
import java.io.*;

public class MovingPlatform extends Collidable{

    private BufferedImage image;
    public static final int UP = 1;
    public static final int DOWN = 2;
    private int speed = 4 ;
    private int direction;
    private int distance_counter;
    public static final int MAX_DISTANCE = 560;



    public MovingPlatform(int c, int r, int direction){
        super(new Rectangle(c*20,r*20,80,40));//platform width and height debatable
        this.direction=direction;
        distance_counter=0;


        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("Images\\Rock.png"));
          //  System.out.println("rock was loaded");
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
        else if(direction==DOWN)
        {
            this.setRectangle(new Rectangle((int)getRectangle().getX(),(int)getRectangle().getY()+speed,(int)getRectangle().getWidth(),(int)getRectangle().getHeight()));
        //    Collidable g = game.getPlayer().checkOnGround();
         //   if(g instanceof MovingPlatform) {
         //       game.getPlayer().setRectangle(new Rectangle(game.getPlayer().getWorldX(),g.getWorldX()+speed-Player.HEIGHT,Player.WIDTH,Player.HEIGHT));
        //    }
            distance_counter+=speed;
        }
           // System.out.println("X set to: "+getWorldX()+" Y set to: "+getWorldY());
        else if(direction==UP)
        {
            this.setRectangle(new Rectangle((int)getRectangle().getX(),(int)getRectangle().getY()-speed,(int)getRectangle().getWidth(),(int)getRectangle().getHeight()));
          //  System.out.println("X set to: "+getWorldX()+" Y set to: "+getWorldY());
        //    Collidable g = game.getPlayer().checkOnGround();
            distance_counter+=speed;
         //   if(g instanceof MovingPlatform) {
        //        game.getPlayer().setRectangle(new Rectangle(game.getPlayer().getWorldX(),g.getWorldX()-speed-Player.HEIGHT,Player.WIDTH,Player.HEIGHT));
        //    }
        }



        //if distance_counter>=MAX then reverse distance of the platform. set distance counter to 0.
        //if up, add speed to platform's y. setRectangle(new Rectangle((int)getRectangle().getX(),(int)getRectangle().getY()+speed)
        //if down, subtract speed from platform's y
        //if right, add speed to platform's x
        //if left, subtract speed from platform's x.
        //add speed to the distance counter.
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
