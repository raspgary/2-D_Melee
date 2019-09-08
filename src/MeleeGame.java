import java.util.*;
import java.io.*;
import java.awt.*;

// class that acts as the rule/referee of the game
public class MeleeGame {
    private Collidable[][] grid;
    private int numRows;
    private int numColumns;
    private double speed = 0.75;     //   speed at which game scrolls
    private Player player1;
    private Player player2;

    private int worldX;
    private int worldY;
    private int status = PLAYING;

    private int waitCount=0;

    // status of the game
    public static final int PLAYING = 0;
    public static final int RED_WINS =1;
    public static final int BLUE_WINS =2;
    public static final int TIE=3;

    // loads the level at the beginning
    public MeleeGame(){
        loadLevel();
    }

    public Player getPlayer1() {
        return player1;
    }

    public int getStatus() {
        return status;
    }

    public Player getPlayer2() {
        return player2;
    }


    // sets the knockback valocity from getting hit based on damage
    public void knockBack(Player p, int direction, boolean block)
    {
        if(direction==Player.LEFT && !block)
        {
            p.setVelocityY((-10.0*(((double)p.getDamage()/200.0)+1.0)));
            p.setVelocityX((-10.0*(((double)p.getDamage()/200.0)+1.0)));
        }

        else if(direction==Player.RIGHT && !block)
        {
            p.setVelocityY((-10.0*(((double)p.getDamage()/200.0)+1.0)));
            p.setVelocityX((10.0*(((double)p.getDamage()/200.0)+1.0)));
        }
        // hitting player gets knocked back if other player is blocking
        else if(direction==Player.LEFT && block){
            p.setVelocityY((-8.0));
            p.setVelocityX((-8.0));
        }
        else if(direction==Player.RIGHT && block){
            p.setVelocityY((-8.0));
            p.setVelocityX((8.0));
        }
    }
    
    // check if player hit you or you hit a player while they blocked
    public void hitDetection()
    {
        if(player1.isHit(player2.getMoveRect())&& player2.hitOtherDude(player1.getRectangle()) && player1.getDirection()==Player.LEFT && (player2.getMoveStatus()!=Player.BLOCK_LEFT && player2.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player1.getMoveStatus()!=Player.BLOCK_LEFT || (player1.getMoveStatus()==Player.BLOCK_LEFT && player2.getDirection()==Player.LEFT)) {
                player1.endLeft();
                player1.setHitStatus(Player.IS_HIT_LEFT);
                if(player2.getMoveStatus()==Player.PUNCH_LEFT || player2.getMoveStatus()==Player.PUNCH_RIGHT){
                    player1.getHit(5);
                }
                else{ 
                    player1.getHit(3);
                }
                player1.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player1, player2.getDirection(), false);
            }
            else if(player1.getMoveStatus()==Player.BLOCK_LEFT && player2.getDirection()!=Player.LEFT){
                player2.endRight();
                player2.setHitStatus(Player.IS_HIT_RIGHT);
                player2.getHit(0);
                knockBack(player2, player1.getDirection(), true);
            }
        }
        else if(player1.isHit(player2.getMoveRect())&& player2.hitOtherDude(player1.getRectangle())&& player1.getDirection()==Player.RIGHT && (player2.getMoveStatus()!=Player.BLOCK_LEFT && player2.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player1.getMoveStatus()!=Player.BLOCK_RIGHT || (player1.getMoveStatus()==Player.BLOCK_RIGHT && player2.getDirection()==Player.RIGHT)) {
                player1.endRight();
                player1.setHitStatus(Player.IS_HIT_RIGHT);
                if(player2.getMoveStatus()==Player.PUNCH_LEFT || player2.getMoveStatus()==Player.PUNCH_RIGHT){
                    player1.getHit(5);
                }
                //player kicked you
                else{
                    player1.getHit(3);
                }
                player1.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player1, player2.getDirection(), false);
            }
            else if(player1.getMoveStatus()==Player.BLOCK_RIGHT && player2.getDirection()!=Player.RIGHT){
                player2.endLeft();
                player2.setHitStatus(Player.IS_HIT_LEFT);
                player2.getHit(0);
                knockBack(player2, player1.getDirection(), true);
            }
        }
        if(player2.isHit(player1.getMoveRect())&& player1.hitOtherDude(player2.getRectangle()) && player2.getDirection()==Player.LEFT && (player1.getMoveStatus()!=Player.BLOCK_LEFT && player1.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player2.getMoveStatus()!=Player.BLOCK_LEFT || (player2.getMoveStatus()==Player.BLOCK_LEFT && player1.getDirection()==Player.LEFT)){
                player2.endLeft();
                player2.setHitStatus(Player.IS_HIT_LEFT);
                if(player1.getMoveStatus()==Player.PUNCH_LEFT || player1.getMoveStatus()==Player.PUNCH_RIGHT){
                    player2.getHit(5);
                }
                else{
                    player2.getHit(3);
                }
                player2.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player2, player1.getDirection(), false);
            }
            else if(player2.getMoveStatus()==Player.BLOCK_LEFT  && player1.getDirection()!=Player.LEFT){
                player1.endRight();
                player1.setHitStatus(Player.IS_HIT_RIGHT);
                player1.getHit(0);
                knockBack(player1, player2.getDirection(), true);
            }
        }
        else if(player2.isHit(player1.getMoveRect())&& player1.hitOtherDude(player2.getRectangle()) && player2.getDirection()==Player.RIGHT && (player1.getMoveStatus()!=Player.BLOCK_LEFT && player1.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player2.getMoveStatus()!=Player.BLOCK_RIGHT || (player2.getMoveStatus()==Player.BLOCK_RIGHT && player1.getDirection()==Player.RIGHT)) {
                player2.endRight();
                player2.setHitStatus(Player.IS_HIT_RIGHT);
                if(player1.getMoveStatus()==Player.PUNCH_LEFT || player1.getMoveStatus()==Player.PUNCH_RIGHT){
                    player2.getHit(5);
                }
                else{
                    player2.getHit(3);
                }
                player2.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player2, player1.getDirection(), false);
            }
            else if(player2.getMoveStatus()==Player.BLOCK_RIGHT && player1.getDirection()!=Player.RIGHT){
                player1.endLeft();
                player1.setHitStatus(Player.IS_HIT_LEFT);
                player1.getHit(0);
                knockBack(player1, player2.getDirection(), true);
            }
        }

    }


    // updating platforms and players
    public void update()
    {
        if(status != PLAYING) {
            return;
        }
        if(waitCount<100)
        {
            waitCount++;
        }
        else
        {
        	// controls the scrolling speed of the map
            worldY-=speed;
        }

        hitDetection();
        updateMovingPlatforms();
        fallIntoPlatforms();
        fallIntoPlatforms2();
        player1.update();
        player2.update();


      
    }

    public Collidable get(int c, int r)
    {
        if(c<0 || c>=grid[0].length || r<0 || r>=grid.length)
            return null;
        else
            return grid[r][c];
    }

    // load level by streaming a .txt file
    public void loadLevel(){
        try{

            Scanner fromFile = new Scanner(new File("LEVELv5.txt"));
            Scanner fromText = new Scanner(fromFile.nextLine()).useDelimiter("[x]");
            numColumns=fromText.nextInt();
            numRows=fromText.nextInt(); //only 48 rows showing at once. 20 pixels at a time
            System.out.println("numColumns is :"+numColumns);
            System.out.println("numRows is :"+numRows);
            System.out.println("loadLevel WORKED!!!!!!");
            grid = new Collidable[numRows][numColumns];

            for(int r=0; r<grid.length; r++)
            {
                fromText = new Scanner(fromFile.nextLine());

                for(int c=0; c<grid[0].length; c++)
                {
                    int num = fromText.nextInt();
                    if(num == 1) {
                        grid[r][c] = new Platform(c, r);
                    }
                    else if(num == 3) {
                        grid[r][c] = new MovingPlatform(c, r, MovingPlatform.DOWN);                  
                    }
                    else if(num == 4) {
                        grid[r][c] = new MovingPlatform(c, r, MovingPlatform.UP);                       
                    }
                }
            }
            worldX=0;
            worldY=numRows*20-920;
            player1 = new Player(this, 100, numRows*20-80, Player.BLUE);
            player2 = new Player(this, 1100, numRows*20-80, Player.RED);
        }
        catch(Exception e){
            System.out.println("Error loading level");
            e.printStackTrace();
        }
    }

    // move the platforms and fix player feet
    public void updateMovingPlatforms(){
        for(Collidable[] row: grid)
        {
            for(Collidable item: row)
            {
                if(item instanceof MovingPlatform) {
                    ((MovingPlatform) (item)).update();
                    if(item.collidesWith(player1.getFeetCheck()))
                    {
                        player1.setRectangle(new Rectangle((int) player1.getRectangle().getX(), 
                        		(item.getWorldY() - player1.HEIGHT), (int) player1.getRectangle().getWidth(), (int) player1.getRectangle().getHeight()));
                    }
                    if(item.collidesWith(player2.getFeetCheck()))
                    {
                        player2.setRectangle(new Rectangle((int) player2.getRectangle().getX(), 
                        		(item.getWorldY() - player2.HEIGHT), (int) player2.getRectangle().getWidth(), (int) player2.getRectangle().getHeight()));
                    }

                }
            }
        }
    }

    // makes sure player 1 doesn't fall beyond platform
    public void fallIntoPlatforms(){
        boolean fellIntoAPlatform = false;
        Rectangle old = player1.getRectangle();
        player1.fall();

        for(Collidable[] row: grid)
        {
            for(Collidable item: row)
            {
                if(item instanceof MovingPlatform && player1.checkOnHead()!=null){
                    return;
                }
                if(item instanceof MovingPlatform && item.collidesWith(player1.getFeetCheck())) {
                        player1.setRectangle(new Rectangle((int) player1.getRectangle().getX(), 
                        		(item.getWorldY() - player1.HEIGHT), (int) player1.getRectangle().getWidth(), (int) player1.getRectangle().getHeight()));
                        fellIntoAPlatform = true;
                }
            }
        }

        if(!fellIntoAPlatform)
            player1.setRectangle(old);
    }

    // makes sure player 2 doesn't fall beyond platform
    public void fallIntoPlatforms2(){
        boolean fellIntoAPlatform = false;
        Rectangle old = player2.getRectangle();
        player2.fall();

        for(Collidable[] row: grid)
        {
            for(Collidable item: row)
            {
                if(item instanceof MovingPlatform && player2.checkOnHead()!=null){
                    return;
                }
                if(item instanceof MovingPlatform && item.collidesWith(player2.getFeetCheck())) {
                    player2.setRectangle(new Rectangle((int) player2.getRectangle().getX(), 
                    		(item.getWorldY() - player2.HEIGHT), (int) player2.getRectangle().getWidth(), (int) player2.getRectangle().getHeight()));
                    fellIntoAPlatform = true;
                }
            }
        }

        if(!fellIntoAPlatform)
            player2.setRectangle(old);
    }

    public Collidable[][] getGrid(){
        return grid;
    }

    public ArrayList<Collidable> getCollidables(){
        ArrayList<Collidable> items = new ArrayList<Collidable>();
        for(Collidable[] row: grid)
        {
            for(Collidable item: row)
            {
                if(item!= null)
                    items.add(item);
            }
        }
        return items;
    }

    public ArrayList<Collidable> getValidCollidables(int lowerLimit, int upperLimit){
        ArrayList<Collidable> collidables = new ArrayList<Collidable>();
        for(int r=lowerLimit; r<=upperLimit; r++)
        {

            for(int c=0; c<grid[0].length; c++)
            {
                if(grid[r][c] instanceof Platform){
                    collidables.add(grid[r][c]);
                }
            }
        }
        return collidables;
    }

    public ArrayList<MovingPlatform> getMovingPlat(){
        ArrayList<MovingPlatform> items = new ArrayList<MovingPlatform>();
        for(Collidable[] row: grid)
        {
            for(Collidable item: row)
            {
                if(item instanceof MovingPlatform)
                    items.add((MovingPlatform)item);
            }
        }
        return items;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {

        this.speed = speed;
    }
}
