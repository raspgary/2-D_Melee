import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

// a class to represent one of the two players
// sets status of players based on keys or outside force
public class Player extends Collidable{

    private int direction;
    private int moveStatus;
    private int hitStatus;
    private int color;
    private MeleeGame game;
    private BufferedImage image;
    private int horizontalSpeed = 5;
    private int x;
    private int y;
    private Rectangle moveRect;
    private Rectangle tempRect;
    private Rectangle tempRect2;
    private int damage;
    private int speed = 1;
    private double velocityX = 0.0;
    private double velocityY = MIN_FALL;
    private double gravity = MIN_FALL;
    private long currentTime;
    private long waitTime;
    private long waitTime2;
    private long immunityTime;
    private boolean cooling;
    private boolean justRekt;
    private boolean justJumped=false;
    private boolean blocking;
    private boolean isDead;
    private int lives;
    private Random randomGenerator = new Random();

    // player states
    public static final int BLUE            = 0;
    public static final int RED             = 1;
    public static final int JUMP_LEFT       = 4;
    public static final int MOVE_LEFT       = 5;
    public static final int MOVE_RIGHT      = 6;
    public static final int PUNCH_RIGHT     = 8;
    public static final int PUNCH_LEFT      = 9;
    public static final int KICK_RIGHT      = 10;
    public static final int KICK_LEFT       = 11;
    public static final int BLOCK_RIGHT     = 12;
    public static final int BLOCK_LEFT      = 13;
    public static final int IS_HIT_LEFT          = 14;
    public static final int NOT_HIT =20;
    public static final int JUMP_RIGHT      = 16;
    public static final int LEFT            = 17;
    public static final int RIGHT           = 18;
    public static final int IS_HIT_RIGHT          = 19;
    public static final int WIDTH = 22;
    public static final int HEIGHT = 54;
    
    // physics movements
    public static final double MIN_FALL=1.25;
    public static final double TERMINAL_VEL = 20.0;
    
    public static final int DAMAGE_CAP = 150;

    // initilaize player
    public Player (MeleeGame game, int x, int y, int color)
    {

        super(new Rectangle(x,y,WIDTH,HEIGHT));
        setMoveRect(new Rectangle(0,0,0,0));
        setMoveStatus(MOVE_RIGHT);
        setHitStatus(NOT_HIT);
        direction=RIGHT;
        tempRect=getRectangle();
        tempRect2=getRectangle();
        cooling=false;
        justRekt=false;
        damage=0;
        isDead=false;
        lives=3;

        this.game 	= game;
        this.color = color;

    }

    // reset players
    public void reset(){
        if(color==BLUE) {
            setRectangle((new Rectangle(100, 9520, 22, 54)));
            setMoveRect(new Rectangle(0, 0, 22, 54));
            setHitStatus(NOT_HIT);
            setMoveStatus(MOVE_RIGHT);
            velocityX=0;
            velocityY=MIN_FALL;
            direction=RIGHT;
            cooling=false;
            justRekt=false;
            isDead=false;
            damage=0;
            lives=3;
            tempRect = getRectangle();
            try {
                Thread.sleep(15);
            } catch (Exception e) {

            }
        }
        else{
            setRectangle((new Rectangle(1100, 9520, 22, 54)));
            setMoveRect(new Rectangle(00, 00, 22, 54));
            setHitStatus(NOT_HIT);
            setMoveStatus(MOVE_RIGHT);
            velocityX=0;
            velocityY=MIN_FALL;
            direction=RIGHT;
            cooling=false;
            justRekt=false;
            isDead=false;
            damage=0;
            lives=3;
            tempRect = getRectangle();
            try {
                Thread.sleep(15);
            } catch (Exception e) {

            }
        }
    }

    // set player status to punch right and start cool down
    public void punchRight(boolean first)
    {

        moveRect=getRectangle();
        setMoveRect(new Rectangle((int)getMoveRect().getX()+19,(int)getMoveRect().getY()+20,15,15));

        if(first) {
            currentTime = System.currentTimeMillis();
            waitTime = currentTime + 300;
            waitTime2= waitTime+150;

        }
        setMoveStatus(PUNCH_RIGHT);
        cooling=true;

    }

    // set player status to punch left and start cool down
    public void punchLeft(boolean first)
    {
        moveRect=getRectangle();
        setMoveRect(new Rectangle((int)getMoveRect().getX()-10-3,(int)getMoveRect().getY()+20,15,15));
        if(first) {
            currentTime = System.currentTimeMillis();
            waitTime = currentTime + 300;
            waitTime2= waitTime+150;
        }
        setMoveStatus(PUNCH_LEFT);
        cooling=true;
    }

    // set player status to kick right and start cool down
    public void kickRight(boolean first)
    {
        moveRect=getRectangle();
        setMoveRect(new Rectangle((int)getMoveRect().getX()+12,(int)getMoveRect().getY()+42,28,10));
        if(first) {
            currentTime = System.currentTimeMillis();
            waitTime = currentTime + 400;
            waitTime2 = waitTime + 250;
        }
        setMoveStatus(KICK_RIGHT);
        cooling=true;
    }


    // set player status to kick left and start cool down
    public void kickLeft(boolean first)
    {

        moveRect=getRectangle();
        setMoveRect(new Rectangle((int)getMoveRect().getX()-20,(int)getMoveRect().getY()+42,28,10));

        if(first) {
            currentTime = System.currentTimeMillis();
            waitTime = currentTime + 400;
            waitTime2 = waitTime + 250;
        }
        setMoveStatus(KICK_LEFT);
        cooling=true;
    }
    
    // set player status to block left and start cool down
    public void blockLeft()
    {

        moveRect=getRectangle();

        setMoveRect(new Rectangle((int)getMoveRect().getX()-10+5,(int)getMoveRect().getY()+15,10,20));

        setMoveStatus(BLOCK_LEFT);
    }
    
    // set player status to block right and start cool down
    public void blockRight()
    {

            moveRect=getRectangle();
            setMoveRect(new Rectangle((int)getMoveRect().getX()+15,(int)getMoveRect().getY()+15,10,20));


        setMoveStatus(BLOCK_RIGHT);
    }


    public boolean isHit(Rectangle rekt)
    {
        if(rekt.intersects(getRectangle()))
        {
            return true;
        }
        return false;
    }

    public boolean hitOtherDude(Rectangle rekt)
    {
        if(moveRect.intersects(rekt))
        {
            return true;
        }
        return false;
    }

    // falling off platform from left
    public void endLeft()
    {
        if(checkOnGround()!=null){
            setMoveStatus(MOVE_LEFT);
        }
        else{
            setMoveStatus(JUMP_LEFT);
        }
        setMoveRect(new Rectangle(0,0,0,0));

    }
    
    // falling off platform from right
    public void endRight()
    {
        if(checkOnGround()!=null){
            setMoveStatus(MOVE_RIGHT);
        }
        else{
            setMoveStatus(JUMP_RIGHT);
        }
        setMoveRect(new Rectangle(0,0,0,0));

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    
    private void moveLeft()
    {
        ArrayList<Collidable> items = game.getCollidables();

        if(getMoveStatus()==BLOCK_LEFT){

            for(int x=0;x<horizontalSpeed-3;x++)
            {
                Rectangle oldRect = getRectangle();
                setRectangle(new Rectangle((int)getRectangle().getX()-speed,(int)getRectangle().getY(),
                		(int)getRectangle().getWidth(),(int)getRectangle().getHeight()));

                if(hitOther()==true)
                {
                    setRectangle(oldRect);
                    return;
                }

            }
        }
        else {
            for (int x = 0; x < horizontalSpeed; x++) {
                Rectangle oldRect = getRectangle();
                setRectangle(new Rectangle((int) getRectangle().getX() - speed, (int) getRectangle().getY(), 
                		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));

                if (hitOther() == true) {
                    setRectangle(oldRect);
                    return;
                }

            }
        }
        direction=LEFT;
        if(moveStatus==MOVE_RIGHT){

            setMoveStatus(MOVE_LEFT);
        }
        else if(moveStatus==PUNCH_RIGHT){
            setMoveStatus(PUNCH_LEFT);
        }
        else if(moveStatus==KICK_RIGHT){
            setMoveStatus(KICK_LEFT);
        }
        else if(moveStatus==JUMP_RIGHT){
            setMoveStatus(JUMP_LEFT);
        }
        else if(moveStatus==BLOCK_RIGHT){
            setMoveStatus(BLOCK_LEFT);
        }
    }

    private void moveRight()
    {
        ArrayList<Collidable> items = game.getCollidables();
        if(getMoveStatus()==BLOCK_RIGHT){
            for(int x=0;x<horizontalSpeed-3;x++)
            {
                Rectangle oldRect = getRectangle();
                setRectangle(new Rectangle((int)getRectangle().getX()+speed,(int)getRectangle().getY(),
                		(int)getRectangle().getWidth(),(int)getRectangle().getHeight()));

                if(hitOther()==true)
                {
                    setRectangle(oldRect);
                    return;
                }

            }
        }
        else {
            for (int x = 0; x < horizontalSpeed; x++) {
                Rectangle oldRect = getRectangle();
                setRectangle(new Rectangle((int) getRectangle().getX() + speed, (int) getRectangle().getY(), 
                		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));

                if (hitOther() == true) {
                    setRectangle(oldRect);
                    return;
                }

            }
        }
        direction=RIGHT;
        if(moveStatus==MOVE_LEFT){

            setMoveStatus(MOVE_RIGHT);
        }
        else if(moveStatus==PUNCH_LEFT){
            setMoveStatus(PUNCH_RIGHT);
        }
        else if(moveStatus==KICK_LEFT){
            setMoveStatus(KICK_RIGHT);
        }
        else if(moveStatus==JUMP_LEFT){
            setMoveStatus(JUMP_RIGHT);
        }
        else if(moveStatus==BLOCK_LEFT){
            setMoveStatus(BLOCK_RIGHT);
        }


    }

    // start jump so it goes higher longer the key is pressed
    public void startJump() {
        if (checkOnGround() != null) {
            Collidable g = checkOnGround();
            if (g instanceof MovingPlatform) {
                MovingPlatform p = (MovingPlatform) g;
                if (getMoveStatus() != BLOCK_LEFT && getMoveStatus() != BLOCK_RIGHT) {
                    if (p.getDirection() == MovingPlatform.UP) {
                        if(color==BLUE) {
                            SoundEffect.JUMP.play();
                        }
                        else{
                            SoundEffect.JUMP2.play();
                        }
                        velocityY = -20.0 - (p.getSpeed() * -1);
                    } else {
                        if(color==BLUE) {
                            SoundEffect.JUMP.play();
                        }
                        else{
                            SoundEffect.JUMP2.play();
                        }
                        velocityY = -20.0;
                    }
                } 
            } else {
                if (getMoveStatus() != BLOCK_LEFT && getMoveStatus() != BLOCK_RIGHT) {
                    if(color==BLUE) {
                        SoundEffect.JUMP.play();
                    }
                    else{
                        SoundEffect.JUMP2.play();
                    }
                    velocityY = -20.0;
                } 
            }
           
            if (getMoveStatus() == MOVE_RIGHT) {
                setMoveStatus(JUMP_RIGHT);
            } else if (getMoveStatus() == MOVE_LEFT) {
                setMoveStatus(JUMP_LEFT);
            }

            justJumped = true;

        }
    }

    // begin to fall down
    public void endJump()
    {
        if(justJumped) {
            if (velocityY < -12.5)
                velocityY = -12.5;
        }

        justJumped=false;
    }

    // player gets hurt and immunity timer is set
    public void getHit(int damageTaken){
        if(justRekt==false){
            damage+=damageTaken;
            if(color==BLUE){
                SoundEffect.HURT.play();
            }
            else{
                SoundEffect.HURT2.play();
            }
            if (damage >=DAMAGE_CAP) {
                damage=DAMAGE_CAP;
               isDead=true;
            }
            else {
                immunityTime = System.currentTimeMillis() + 250;
                justRekt = true;
            }
        }
    }

    // check if player is on platform
    // fall down if move off platform
    public void correctGround(){
        Collidable g = checkOnGround();
        if(g!=null) {

            if (g instanceof MovingPlatform) {
                MovingPlatform p = (MovingPlatform) g;

                if (p.getDirection() == MovingPlatform.UP) {
                    velocityY = MIN_FALL;
                    setRectangle(new Rectangle((int) getRectangle().getX(), (p.getWorldY() - HEIGHT), 
                    		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
                } else {
                    velocityY = MIN_FALL;
                    setRectangle(new Rectangle((int) getRectangle().getX(), (p.getWorldY() - HEIGHT), 
                    		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
                }
             } else {
                velocityY = MIN_FALL;
                setRectangle(new Rectangle((int) getRectangle().getX(), (g.getWorldY() - HEIGHT), 
                		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
            }
            if (moveStatus == JUMP_RIGHT) {
                setMoveStatus(MOVE_RIGHT);
            } else if (moveStatus == JUMP_LEFT) {
                setMoveStatus(MOVE_LEFT);
            }
            return;
        }
    }

    
    public void fall()
    {
        if(velocityY>0)
            setRectangle(new Rectangle((int) getRectangle().getX(), (int) (getRectangle().getY() + 5), 
            		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));

    }
    
    // set status by key presses
    public void checkBlueKeys(){
        if(MeleePanel.keys[KeyEvent.VK_D])
        {
            moveRight();
        }
        if (MeleePanel.keys[KeyEvent.VK_A])
        {
            moveLeft();
        }

        if (MeleePanel.keys[KeyEvent.VK_W]){
            startJump();
        }
        else if(!MeleePanel.keys[KeyEvent.VK_W]){
            endJump();
        }
        if(MeleePanel.keys[KeyEvent.VK_C]&&getDirection()==LEFT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            punchLeft(true);
            return;
        }
        if(MeleePanel.keys[KeyEvent.VK_C]&&getDirection()==RIGHT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            punchRight(true);
            return;
        }
        if(MeleePanel.keys[KeyEvent.VK_V]&&getDirection()==LEFT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            kickLeft(true);
            return;
        }
        if(MeleePanel.keys[KeyEvent.VK_V]&&getDirection()==RIGHT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            kickRight(true);
            return;
        }
        if(MeleePanel.keys[KeyEvent.VK_B]&&getDirection()==LEFT )
        {
            blockLeft();
            blocking=true;
            return;
        }
        else if(!MeleePanel.keys[KeyEvent.VK_B]&&getDirection()==LEFT && blocking)
        {
            endLeft();
            blocking=false;
        }
        if(MeleePanel.keys[KeyEvent.VK_B]&&getDirection()==RIGHT)
        {
            blockRight();
            blocking=true;
            return;
        }
        else if(!MeleePanel.keys[KeyEvent.VK_B]&&getDirection()==RIGHT  && blocking)
        {
            endRight();
            blocking=false;
        }
    }

    public void checkRedKeys(){
        if(MeleePanel.keys[KeyEvent.VK_SEMICOLON])
        {
            moveRight();
        }
        if (MeleePanel.keys[KeyEvent.VK_K])
        {
            moveLeft();
        }

        if (MeleePanel.keys[KeyEvent.VK_O]){
           // AudioPlayer.player.start(audioStream_jump);
            startJump();
        }
        else if(!MeleePanel.keys[KeyEvent.VK_O]){
            endJump();
        }
        if(MeleePanel.keys[KeyEvent.VK_OPEN_BRACKET]&&getDirection()==LEFT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            punchLeft(true);
            return;
        }

        if(MeleePanel.keys[KeyEvent.VK_OPEN_BRACKET]&&getDirection()==RIGHT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            punchRight(true);
            return;
        }

        if(MeleePanel.keys[KeyEvent.VK_CLOSE_BRACKET]&&getDirection()==LEFT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            kickLeft(true);
            return;
        }

        if(MeleePanel.keys[KeyEvent.VK_CLOSE_BRACKET]&&getDirection()==RIGHT && !cooling && !blocking && getHitStatus()==NOT_HIT)
        {
            kickRight(true);
            return;
        }

        if(MeleePanel.keys[KeyEvent.VK_BACK_SLASH]&&getDirection()==LEFT )
        {
            blockLeft();
            blocking=true;
            return;
        }
        else if(!MeleePanel.keys[KeyEvent.VK_BACK_SLASH]&&getDirection()==LEFT && blocking)
        {
            endLeft();
            blocking=false;
        }
        if(MeleePanel.keys[KeyEvent.VK_BACK_SLASH]&&getDirection()==RIGHT)
        {
            blockRight();
            blocking=true;
            return;
        }
        else if(!MeleePanel.keys[KeyEvent.VK_BACK_SLASH]&&getDirection()==RIGHT && blocking)
        {
            endRight();
            blocking=false;
        }
    }

    public void checkKeys()
    {
        if(color==BLUE) {
            checkBlueKeys();
        }
        else if(color==RED) {
        checkRedKeys();
         }

    }
    
    // updates player continuously
    public void update()
    {
    	
    	
        if(isDead){
            return;
        }


        checkKeys();

        // handle immunity
        if(justRekt){
            if(System.currentTimeMillis()>=immunityTime) {
                setHitStatus(Player.NOT_HIT);
                justRekt = false;
            }
        }
        
        // set cooldown

        if(cooling)
        {
            if(System.currentTimeMillis()>=waitTime2){
                cooling=false;
            }
            else if(System.currentTimeMillis()>waitTime){
                if(moveStatus==PUNCH_LEFT){
                    endLeft();
                }
                else if(moveStatus==PUNCH_RIGHT){
                    endRight();
                }
                else if(moveStatus==KICK_LEFT){
                    endLeft();
                }
                else if(moveStatus==KICK_RIGHT){
                    endRight();
                }
            }
        }

        if(moveStatus==KICK_LEFT){
            kickLeft(false);
        }
        else if(moveStatus==KICK_RIGHT){
            kickRight(false);
        }
        else if(moveStatus==PUNCH_LEFT){
            punchLeft(false);
        }
        else if(moveStatus==PUNCH_RIGHT){
            punchRight(false);
        }

        velocityY += gravity;


        if(velocityY>= TERMINAL_VEL){
            velocityY=TERMINAL_VEL;
        }
        else if(velocityY<=0 && velocityY<= TERMINAL_VEL*-1){
            velocityY=TERMINAL_VEL*-1;
        }

        setRectangle(new Rectangle((int) getRectangle().getX(), (int) (getRectangle().getY() + velocityY), 
        		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        knockBackHorizontal();

        if(checkOnHead()!=null){
        	// makes sure player head doesn't get stuck on moving platform
            if(checkOnHead() instanceof MovingPlatform){
                MovingPlatform p = (MovingPlatform) checkOnHead();
                if(checkOnGround()!=null && p.getDirection()==MovingPlatform.DOWN){
                    int midpoint = p.getWorldX()+p.getWidth()/2;
                    //player is left to moving platform
                    if(getWorldX()<midpoint){ 
                        while(p.getRectangle().intersects(getRectangle())) {
                            setRectangle(new Rectangle((int) getRectangle().getX() - 1, 
                            		(int) getRectangle().getY(), (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
                        }
                    }
                    else{
                        while(p.getRectangle().intersects(getRectangle())) {
                            setRectangle(new Rectangle((int) getRectangle().getX()+1, 
                            		(int) getRectangle().getY(), (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
                        }
                    }
                }
                else {
                    if (velocityY < 0) {
                        velocityY = MIN_FALL;
                    }
                    setRectangle(new Rectangle((int) getRectangle().getX(), 
                    		(p.getWorldY() + p.getHeight() + 1), (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
                }
            }
            else {
                velocityY = MIN_FALL;
                setRectangle(new Rectangle((int) getRectangle().getX(), 
                		(checkOnHead().getWorldY() + (int) checkOnHead().getRectangle().getHeight() + 1), 
                		(int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
            }

        }
        correctGround();
    }

    // sets velocity of player from knockback
    public void knockBackHorizontal(){
        if(velocityX!=0){
            double acceleration;
            if(velocityX>=TERMINAL_VEL)
            {
                velocityX=TERMINAL_VEL;
            }
            else if(velocityX<=0 && velocityX<=TERMINAL_VEL*-1){
                velocityX=TERMINAL_VEL*-1;
            }
            if(velocityX>0){
                acceleration = -0.75;
            }
            else{
                acceleration = 0.75;
            }

            Rectangle oldRect = getRectangle();
            setRectangle(new Rectangle((int)(getRectangle().getX()+velocityX),(int)getRectangle().getY(),
            		(int)getRectangle().getWidth(),(int)getRectangle().getHeight()));
            if(hitOther()==true)
            {
                setRectangle(oldRect);
                velocityX=0;
                return;
            }
            velocityX+=acceleration;
            if(acceleration>0 && velocityX>=0){
                velocityX=0;
            }
            else if(acceleration<0 && velocityX<=0){
                velocityX=0;
            }
        }
    }

    // check if player is on the ground
    // uses small hit box	
    public Collidable checkOnGround()
    {
        tempRect=(new Rectangle((int)getRectangle().getX(),(int)(getRectangle().getY()+getRectangle().getHeight()),22,1));
        return hitOther(tempRect);
    }
    // return where the feet are
    // uses small hit box
    public Collidable getFeetCheck(){
        return new Collidable(new Rectangle((int)getRectangle().getX(),(int)(getRectangle().getY()+getRectangle().getHeight()),22,1));
    }

    // return where the head is
    // uses small hit box
    public Collidable checkOnHead()
    {
        tempRect2=(new Rectangle((int)getRectangle().getX(),(int)(getRectangle().getY()-1),(int)getRectangle().getWidth(),2));
        return hitOther(tempRect2);
    }


   private boolean hitOther()
    {
        ArrayList<Collidable> items = game.getCollidables();

        for(Collidable a:items)
            if(collidesWith(a)) {
                return true;
            }
        return false;
    }

    private Collidable hitOther(Rectangle rect){
        ArrayList<Collidable> items = game.getCollidables();

        for(Collidable a:items) {
            if (rect.intersects(a.getRectangle())) {
                return a;
            }
        }
        return null;
    }

    // called when a player dies
    public void die(){
        lives--;
        isDead=true;
        int otherLives;
        if(color==BLUE) {
             otherLives = game.getPlayer2().getLives();
        }
        else{
            otherLives = game.getPlayer1().getLives();
        }
        // speed up if one life left
        if(lives==1 || lives==1 && otherLives==1){
            game.setSpeed(game.getSpeed()+0.75);
            MeleePanel.songPlayer.setTempoFactor(1.25F);
        }
        // play sound effects
        if(color==BLUE){
            SoundEffect.EXPLODE.play();
        }
        else {
            SoundEffect.EXPLODE2.play();
        }
        // checks if game is over
        if(lives<=0 && color==BLUE) {
            if(game.getStatus()==game.PLAYING) {
                game.setStatus(MeleeGame.RED_WINS);
            }
        }
        else if(lives<=0 && color==RED){
            if(game.getStatus()==game.PLAYING) {
                game.setStatus(MeleeGame.BLUE_WINS);
            }
        }
        if((lives<=0 && color==BLUE && game.getPlayer2().getLives()<=0) || (lives<=0 && color==RED && game.getPlayer1().getLives()<=0)){
            game.setStatus(MeleeGame.TIE);
        }
    }

    // respawns dead player at random spot on map
    public void respawn(){
        int gameY=game.getWorldY();
        int lowerLimit=gameY+700; //appears larger than upperlimit in the grid coord
        int upperLimit=gameY+300;
        int lowerCoord=(lowerLimit-lowerLimit%20)/20; //rounds to the nearest coordinate
        int upperCoord=(upperLimit-upperLimit%20)/20;
        ArrayList<Collidable> potentialSpawns = game.getValidCollidables(upperCoord, lowerCoord);
        int random = randomGenerator.nextInt(potentialSpawns.size());
        int spawnX = potentialSpawns.get(random).getWorldX();
        int spawnY = potentialSpawns.get(random).getWorldY()-HEIGHT;
        setRectangle((new Rectangle(spawnX, spawnY, 22, 54)));
        setMoveRect(new Rectangle(00, 00, 22, 54));
        isDead=false;
        setHitStatus(NOT_HIT);
        setMoveStatus(MOVE_RIGHT);
        velocityX=0;
        velocityY=MIN_FALL;
        direction=RIGHT;
        cooling=false;
        justRekt=false;
        damage=0;
        tempRect = getRectangle();

    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public MeleeGame getGame() {
        return game;
    }

    public void setGame(MeleeGame game) {
        this.game = game;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMoveStatus(int status)
    {
        moveStatus=status;
    }

    public int getMoveStatus()
    {
        return moveStatus;
    }

    public void setHitStatus(int status)
    {
        hitStatus=status;
    }

    public int getHitStatus() {
        return hitStatus;
    }

    public void setMoveRect(Rectangle rect)
    {
        moveRect=rect;
    }

    public Rectangle getMoveRect()
    {
        return moveRect;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives=lives;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean dead) {
        isDead = dead;
    }
}



