
import java.awt.Rectangle;

public class Collidable
{
    private Rectangle rect;

    public Collidable(Rectangle rect)
    {
        this.rect = rect;
    }

    public Rectangle getRectangle()
    {
        return rect;
    }

    public void setRectangle(Rectangle rect)
    {
        this.rect = rect;
    }

    public boolean collidesWith(Collidable c)
    {
        return rect.intersects(c.getRectangle());
    }

    public int getWorldX()
    {
        return (int)rect.getX();
    }

    public int getWorldY()
    {
        return (int)rect.getY();
    }





}
