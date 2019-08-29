import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.applet.*;
import java.net.URL;
/**
 * Created by othscs018 on 8/30/2016.
 */
public class MeleeGame {
    //private player1 player1;
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


    public static final int PLAYING = 0;
    public static final int RED_WINS =1;
    public static final int BLUE_WINS =2;
    public static final int TIE=9;
    public static final int WAITING_FOR_BLUE		= 3;
    public static final int WAITING_RESTART_RED 	= 4;
    public static final int WAITING_RESTART_BLUE 	= 5;
    public static final int player1_LEFT			= 6;
    public static final int RED			= 7;
    public static final int BLUE		= 8;
    private InputStream inputStream_hurt;
    private AudioStream audioStream_hurt;
    private InputStream inputStream_hit;
    private AudioStream audioStream_hit;
    private InputStream inputStream_jump;
    private AudioStream audioStream_jump;
    private InputStream inputStream_explode;
    private AudioStream audioStream_explode;

    private URL urlhurt;
    private AudioInputStream audiohurt;
    private Clip cliphurt;
    private URL urlhit;
    private AudioInputStream audiohit;
    private Clip cliphit;
    private URL urljump;
    private AudioInputStream audiojump;
    private Clip clipjump;
    private URL urlexplode;
    private AudioInputStream audioexplode;
    private Clip clipexplode;

    
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

        else if(direction==Player.LEFT && block){
            p.setVelocityY((-8.0));//*(((double)p.getDamage()/100.0)+1.0)));
            p.setVelocityX((-8.0));//*(((double)p.getDamage()/100.0)+1.0)));
        }

        else if(direction==Player.RIGHT && block){
            p.setVelocityY((-8.0));//*(((double)p.getDamage()/100.0)+1.0)));
            p.setVelocityX((8.0));//*(((double)p.getDamage()/100.0)+1.0)));
        }
    }
    public void hitDetection()
    {
        if(player1.isHit(player2.getMoveRect())&& player2.hitOtherDude(player1.getRectangle()) && player1.getDirection()==Player.LEFT && (player2.getMoveStatus()!=Player.BLOCK_LEFT && player2.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player1.getMoveStatus()!=Player.BLOCK_LEFT || (player1.getMoveStatus()==Player.BLOCK_LEFT && player2.getDirection()==Player.LEFT)) {
           //     System.out.println("BLUE dong");
                player1.endLeft();
                player1.setHitStatus(Player.IS_HIT_LEFT);
                if(player2.getMoveStatus()==Player.PUNCH_LEFT || player2.getMoveStatus()==Player.PUNCH_RIGHT){
                   // cliphurt.start();
                    //AudioPlayer.player.start(audioStream_hurt);
                    player1.getHit(5);
                }
                else{ //player kicked you
                    //AudioPlayer.player.start(audioStream_hurt);
                    //cliphurt.start();
                    player1.getHit(3);
                }
                player1.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player1, player2.getDirection(), false);
            }
            else if(player1.getMoveStatus()==Player.BLOCK_LEFT && player2.getDirection()!=Player.LEFT){
                player2.endRight();
                player2.setHitStatus(Player.IS_HIT_RIGHT);
               // AudioPlayer.player.start(audioStream_hit);
               // cliphit.start();
                player2.getHit(0);
                knockBack(player2, player1.getDirection(), true);
            }
        }
        else if(player1.isHit(player2.getMoveRect())&& player2.hitOtherDude(player1.getRectangle())&& player1.getDirection()==Player.RIGHT && (player2.getMoveStatus()!=Player.BLOCK_LEFT && player2.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player1.getMoveStatus()!=Player.BLOCK_RIGHT || (player1.getMoveStatus()==Player.BLOCK_RIGHT && player2.getDirection()==Player.RIGHT)) {
            //    System.out.println("BLUE dong");
                player1.endRight();
                player1.setHitStatus(Player.IS_HIT_RIGHT);
                if(player2.getMoveStatus()==Player.PUNCH_LEFT || player2.getMoveStatus()==Player.PUNCH_RIGHT){
                    //cliphurt.start();
                    //AudioPlayer.player.start(audioStream_hurt);
                    player1.getHit(5);
                }
                else{ //player kicked you
                    //AudioPlayer.player.start(audioStream_hurt);
                  //  cliphurt.start();
                    player1.getHit(3);
                }
                player1.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player1, player2.getDirection(), false);
            }
            else if(player1.getMoveStatus()==Player.BLOCK_RIGHT && player2.getDirection()!=Player.RIGHT){
                player2.endLeft();
                player2.setHitStatus(Player.IS_HIT_LEFT);
               // AudioPlayer.player.start(audioStream_hit);
               // cliphit.start();
               // cliphit.start();
                player2.getHit(0);
                knockBack(player2, player1.getDirection(), true);
            }
        }
       /* else if(player1.checkOnGround()!=null){
            player1.setHitStatus(Player.NOT_HIT);
        }*/
        if(player2.isHit(player1.getMoveRect())&& player1.hitOtherDude(player2.getRectangle()) && player2.getDirection()==Player.LEFT && (player1.getMoveStatus()!=Player.BLOCK_LEFT && player1.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player2.getMoveStatus()!=Player.BLOCK_LEFT || (player2.getMoveStatus()==Player.BLOCK_LEFT && player1.getDirection()==Player.LEFT)){
             //   System.out.println("RED dong");
                player2.endLeft();
                player2.setHitStatus(Player.IS_HIT_LEFT);
                if(player1.getMoveStatus()==Player.PUNCH_LEFT || player1.getMoveStatus()==Player.PUNCH_RIGHT){
                   // AudioPlayer.player.start(audioStream_hurt);
                    //cliphurt.start();
                    player2.getHit(5);
                }
                else{ //player kicked you
                    //AudioPlayer.player.start(audioStream_hurt);
                   // cliphurt.start();
                    player2.getHit(3);
                }
                player2.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player2, player1.getDirection(), false);
            }
            else if(player2.getMoveStatus()==Player.BLOCK_LEFT  && player1.getDirection()!=Player.LEFT){
                player1.endRight();
                player1.setHitStatus(Player.IS_HIT_RIGHT);
               // AudioPlayer.player.start(audioStream_hit);
                //cliphit.start();
                player1.getHit(0);
                knockBack(player1, player2.getDirection(), true);
            }
        }
        else if(player2.isHit(player1.getMoveRect())&& player1.hitOtherDude(player2.getRectangle()) && player2.getDirection()==Player.RIGHT && (player1.getMoveStatus()!=Player.BLOCK_LEFT && player1.getMoveStatus()!=Player.BLOCK_RIGHT))
        {
            if(player2.getMoveStatus()!=Player.BLOCK_RIGHT || (player2.getMoveStatus()==Player.BLOCK_RIGHT && player1.getDirection()==Player.RIGHT)) {
            //    System.out.println("RED dong");
                player2.endRight();
                player2.setHitStatus(Player.IS_HIT_RIGHT);
                if(player1.getMoveStatus()==Player.PUNCH_LEFT || player1.getMoveStatus()==Player.PUNCH_RIGHT){
                    //AudioPlayer.player.start(audioStream_hurt);
                    //cliphurt.start();
                    player2.getHit(5);
                }
                else{ //player kicked you
                    //AudioPlayer.player.start(audioStream_hurt);
                    //cliphurt.start();
                    player2.getHit(3);
                }
                player2.setMoveRect(new Rectangle(0,0,0,0));
                knockBack(player2, player1.getDirection(), false);
            }
            else if(player2.getMoveStatus()==Player.BLOCK_RIGHT && player1.getDirection()!=Player.RIGHT){
                player1.endLeft();
                player1.setHitStatus(Player.IS_HIT_LEFT);
                //AudioPlayer.player.start(audioStream_hit);
                //cliphit.start();
                player1.getHit(0);
                knockBack(player1, player2.getDirection(), true);
            }
        }

    }


    public void update()
    {
        if(status != PLAYING) {
            return;
        }

        /*if(player1==RED&&player1.getHP()==0)
        {
            status==BLACK_WINS;
        }
        if(player1==BLACK&&player1.getHP()==0)
        {
            status==RED_WINS;
        }*/
        if(waitCount<100)
        {
            waitCount++;
        }
        else
        {
            worldY-=speed; //this controls the speed!!!!!!!!!!!!
        }

        hitDetection();
        updateMovingPlatforms();
        fallIntoPlatforms();
        fallIntoPlatforms2();
        player1.update();
        player2.update();


      /*  try {
            urlhurt = this.getClass().getClassLoader().getResource("Sounds_files\\hurt.wav");
            // audiohurt = AudioSystem.getAudioInputStream(urlhurt);
            cliphurt = AudioSystem.getClip();
            //cliphurt.open(audiohurt);

            urlhit = this.getClass().getClassLoader().getResource("Sounds_files\\hit.wav");
            // audiohit = AudioSystem.getAudioInputStream(urlhit);
            cliphit = AudioSystem.getClip();
            //cliphit.open(audiohit);

            urlexplode = this.getClass().getClassLoader().getResource("Sounds_files\\explosion.wav");
            //  audioexplode = AudioSystem.getAudioInputStream(urlexplode);
            clipexplode = AudioSystem.getClip();
            //clipexplode.open(audioexplode);
        }
        catch (Exception e)
        {
            System.out.println("Error with sounds");
            e.printStackTrace();
        }*/

     //   player1.correctGround();





    }

    public Collidable get(int c, int r)
    {
        if(c<0 || c>=grid[0].length || r<0 || r>=grid.length)
            return null;
        else
            return grid[r][c];
    }

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
            /*inputStream_hurt = getClass().getResourceAsStream("Sounds_files\\hurt.wav");
            audioStream_hurt = new AudioStream(inputStream_hurt);
            inputStream_hit = getClass().getResourceAsStream("Sounds_files\\hit.wav");
            audioStream_hit = new AudioStream(inputStream_hit);
            inputStream_jump = getClass().getResourceAsStream("Sounds_files\\jump.wav");
            audioStream_jump = new AudioStream(inputStream_jump);
            inputStream_explode = getClass().getResourceAsStream("Sounds_files\\explosion.wav");
            audioStream_explode = new AudioStream(inputStream_explode);*/





            for(int r=0; r<grid.length; r++)
            {
                fromText = new Scanner(fromFile.nextLine());

                for(int c=0; c<grid[0].length; c++)
                {
                    int num = fromText.nextInt();
                    //System.out.print(num);
                    if(num == 1) {
                        grid[r][c] = new Platform(c, r);
                    }
                    else if(num == 3) {
                        grid[r][c] = new MovingPlatform(c, r, MovingPlatform.DOWN);
                     //   System.out.println(r+", "+c);
                    }
                    else if(num == 4) {
                        grid[r][c] = new MovingPlatform(c, r, MovingPlatform.UP);
                       // System.out.println(r+", "+c);
                    }
                }
              //  System.out.println();
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

    public void updateMovingPlatforms(){
        for(Collidable[] row: grid)
        {
            for(Collidable item: row)
            {
                if(item instanceof MovingPlatform) {
                    ((MovingPlatform) (item)).update();
                    if(item.collidesWith(player1.getFeetCheck()))
                    {
                        player1.setRectangle(new Rectangle((int) player1.getRectangle().getX(), (item.getWorldY() - player1.HEIGHT), (int) player1.getRectangle().getWidth(), (int) player1.getRectangle().getHeight()));
                    }
                    if(item.collidesWith(player2.getFeetCheck()))
                    {
                        player2.setRectangle(new Rectangle((int) player2.getRectangle().getX(), (item.getWorldY() - player2.HEIGHT), (int) player2.getRectangle().getWidth(), (int) player2.getRectangle().getHeight()));
                    }

                }
            }
        }
    }

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
                        player1.setRectangle(new Rectangle((int) player1.getRectangle().getX(), (item.getWorldY() - player1.HEIGHT), (int) player1.getRectangle().getWidth(), (int) player1.getRectangle().getHeight()));
                        fellIntoAPlatform = true;
                }
            }
        }

        if(!fellIntoAPlatform)
            player1.setRectangle(old);
    }

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
                    player2.setRectangle(new Rectangle((int) player2.getRectangle().getX(), (item.getWorldY() - player2.HEIGHT), (int) player2.getRectangle().getWidth(), (int) player2.getRectangle().getHeight()));
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
            //  System.out.println();
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
    //    System.out.println(speed);
    }
}
