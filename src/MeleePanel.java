
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import javax.sound.midi.*;
import java.applet.*;
import java.net.URL;

public class MeleePanel extends JPanel implements KeyListener, Runnable
{
    private MeleeGame game = null;
    Font f= new Font("Times New Roman",Font.BOLD,35);
    private BufferedImage buffer 	= null;
    private BufferedImage blue_lblock;
    private BufferedImage blue_rblock;
    private BufferedImage blue_lduck;
    private BufferedImage blue_rduck;
    private BufferedImage blue_ljump;
    private BufferedImage blue_rjump;
    private BufferedImage blue_lkick;
    private BufferedImage blue_rkick;
    private BufferedImage blue_lnorm;
    private BufferedImage blue_rnorm;
    private BufferedImage blue_lpunch;
    private BufferedImage blue_rpunch;
    private BufferedImage red_lblock;
    private BufferedImage red_rblock;
    private BufferedImage red_lduck;
    private BufferedImage red_rduck;
    private BufferedImage red_ljump;
    private BufferedImage red_rjump;
    private BufferedImage red_lkick;
    private BufferedImage red_rkick;
    private BufferedImage red_lnorm;
    private BufferedImage red_rnorm;
    private BufferedImage red_lpunch;
    private BufferedImage red_rpunch;
    private BufferedImage rock;
    private BufferedImage background;
    private BufferedImage blue_rhit;
    private BufferedImage blue_lhit;
    private BufferedImage red_rhit;
    private BufferedImage red_lhit;
   private BufferedImage explode_u;
    private BufferedImage explode_r;
    private BufferedImage explode_l;
    private BufferedImage explode_d;
    private int displayWidth=1200;
    private int displayHeight=960;
    private int cellWidth = 20;
    private int cellHeight = 20;
    private int worldWidth=4000;
    private int worldHeight=4000;
    private int count=0;
    private boolean dead=false;
    private boolean dead2=false;
    private int tempx;
    private int tempy;
    private int tempx2;
    private int tempy2;
    private long currentTime;
    private long deadTime_BLUE;
    private long deadTime_RED;
    public static Sequencer songPlayer;


    public static boolean[] keys = new boolean[255];

    public static final Set<Character> pressed = new HashSet<Character>();

    public MeleePanel() throws MalformedURLException {
        game = new MeleeGame();
        setSize(1200,960);
        buffer = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        setFocusable(true);
        SoundEffect.init();
        //bmusic.loop();

       try
       {
		blue_lblock=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_left_b.png"));
		blue_rblock=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_right_b.png"));
		blue_ljump=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_left_j.png"));
		blue_rjump=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_right_j.png"));
		blue_lkick=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_left_k2.png"));
		blue_rkick=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_right_k2.png"));
		blue_lnorm=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_left_n.png"));
		blue_rnorm=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_right_n.png"));
		blue_lpunch=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_left_p.png"));
		blue_rpunch=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_right_p.png"));
        blue_rhit=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_right_h.png"));
        blue_lhit=ImageIO.read(getClass().getResourceAsStream("Images\\blue_dude_left_h.png"));

		red_lblock=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_left_b.png"));
		red_rblock=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_right_b.png"));
		red_ljump=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_left_j.png"));
		red_rjump=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_right_j.png"));
		red_lkick=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_left_k2.png"));
		red_rkick=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_right_k2.png"));
		red_lnorm=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_left_n.png"));
		red_rnorm=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_right_n.png"));
		red_lpunch=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_left_p.png"));
		red_rpunch=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_right_p.png"));
        red_lhit=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_left_h.png"));
        red_rhit=ImageIO.read(getClass().getResourceAsStream("Images\\red_dude_right_h.png"));
		rock=ImageIO.read(getClass().getResourceAsStream("Images\\rock.png"));
        explode_r=ImageIO.read(getClass().getResourceAsStream("Images\\explode_r.png"));
        explode_u=ImageIO.read(getClass().getResourceAsStream("Images\\explode_u.png"));
        explode_d=ImageIO.read(getClass().getResourceAsStream("Images\\explode_d.png"));
        explode_l=ImageIO.read(getClass().getResourceAsStream("Images\\explode_l.png"));

        Sequence song = MidiSystem.getSequence(this.getClass().getClassLoader().getResource("Sounds_files\\Pokemon2.mid"));
        songPlayer = MidiSystem.getSequencer();
        songPlayer.setSequence(song);
        songPlayer.open();
        songPlayer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        songPlayer.start();


	}
	catch(Exception e)
	{
		System.out.print("Error Loading Images:");
		e.printStackTrace();
	}
	addKeyListener(this);
        Thread t = new Thread(this);
        t.start();
    }


    public void run()
    {
        try
        {
            while(true)
            {
                Thread.sleep(15);
                game.update();
                if(this.getGraphics() != null){
                    paint(this.getGraphics());

                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error in Run");
            e.printStackTrace();
        }
    }


    public void paint(Graphics s) {
        Graphics g = buffer.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());

        int worldX = game.getWorldX();
        int worldY = game.getWorldY();

        Player player = game.getPlayer1();
        Player player2 = game.getPlayer2();

        if(worldY<=0){
            if(game.getPlayer1().getLives()>game.getPlayer2().getLives()){
                game.setStatus(game.BLUE_WINS);
            }
            else if(game.getPlayer2().getLives()>game.getPlayer2().getLives()){
                game.setStatus(game.RED_WINS);
            }
            else if(game.getPlayer1().getLives()==game.getPlayer2().getLives()){
                if(game.getPlayer1().getDamage()>game.getPlayer2().getDamage()){
                    game.setStatus(game.RED_WINS);
                }
                else if(game.getPlayer1().getDamage()<game.getPlayer2().getDamage()){
                    game.setStatus(game.BLUE_WINS);
                }
            }
            else {
                game.setStatus(game.TIE);
            }
        }

        if(dead && System.currentTimeMillis()>=deadTime_BLUE){
            dead=false;
            tempx=0;
            tempy=0;
            if(player.getLives()>0) {
                player.respawn();
            }
        }
        if(dead2 && System.currentTimeMillis()>=deadTime_RED){
            dead2=false;
            tempx2=0;
            tempy2=0;
            if(player2.getLives()>0) {
                player2.respawn();
            }
        }

        int startRow = worldY/cellHeight-1;
        int startColumn = worldX/cellWidth-1;

        int endRow = (worldY+displayHeight)/cellHeight;
        int endColumn = game.getNumColumns();

        g.setColor(new Color(63, 191, 255));
        g.fillRect(0, 0,getWidth(), getHeight());

        for(int r=startRow; r<=endRow;r++){
            for(int c=startColumn; c<=endColumn; c++)
            {
                Collidable p = game.get(c,r);

                if(p != null && p instanceof Platform)
                {
                    Platform w = (Platform)p;
                    g.drawImage(w.getImage(),w.getWorldX(),w.getWorldY()-worldY,null);
                }
            }
        }

        for(MovingPlatform w: game.getMovingPlat())
        {

            g.drawImage(w.getImage(), w.getWorldX(), w.getWorldY() - worldY, null);

        }


        if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.MOVE_RIGHT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){
           g.drawImage(blue_rnorm, player.getWorldX(), player.getWorldY()-worldY, null);
        }

        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.MOVE_LEFT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){
            g.drawImage(blue_lnorm, player.getWorldX(), player.getWorldY()-worldY, null);
        }

        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.JUMP_RIGHT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){

            g.drawImage(blue_rjump, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.PUNCH_RIGHT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){


            g.drawImage(blue_rpunch, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.PUNCH_LEFT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){

            g.drawImage(blue_lpunch, player.getWorldX()-10, player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.KICK_RIGHT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){

            g.drawImage(blue_rkick, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.KICK_LEFT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){

            g.drawImage(blue_lkick, player.getWorldX()-20, player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.BLOCK_RIGHT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){
            g.drawImage(blue_rblock, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.BLOCK_LEFT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){
            g.drawImage(blue_lblock, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getMoveStatus()==Player.JUMP_LEFT && player.getHitStatus()==Player.NOT_HIT && !player.getIsDead()){
            g.drawImage(blue_ljump, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        if(player.getColor() == Player.BLUE  && player.getHitStatus()==Player.IS_HIT_LEFT && !player.getIsDead()){
            g.drawImage(blue_lhit, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        else if(player.getColor() == Player.BLUE && player.getHitStatus()==Player.IS_HIT_RIGHT  && !player.getIsDead()){
            g.drawImage(blue_rhit, player.getWorldX(), player.getWorldY()-worldY, null);
        }
        if(player.getDamage()>=Player.DAMAGE_CAP){
            if(!dead) {
                tempx = player.getWorldX() - 85;
                tempy = player.getWorldY() - 95;
                player.die();
                deadTime_BLUE=System.currentTimeMillis()+2500;
                dead = true;
            }
            if(dead) {
                g.drawImage(explode_u, tempx, tempy-worldY, null);

            }
        }
        else if(player.getWorldY() -worldY+54+80<0){
            if(!dead) {
                 tempx=player.getWorldX()-65;
                 tempy=player.getWorldY()-worldY+45+72;
                 player.die();
                deadTime_BLUE=System.currentTimeMillis()+2500;
                 dead = true;
            }
            if(dead) {
                g.drawImage(explode_d, tempx, tempy, null);

            }
        }
        else if (player.getWorldY() -worldY>960){
            if(!dead) {
                tempx=player.getWorldX()-85;
                tempy=player.getWorldY()-worldY-150;
                player.die();
                deadTime_BLUE=System.currentTimeMillis()+2500;
                dead = true;
            }
            if(dead) {
                g.drawImage(explode_u, tempx, tempy, null);

            }
        }
        else if (player.getWorldX()+22<0){
            if(!dead) {
                tempx = player.getWorldX();
                tempy = player.getWorldY() - worldY - 65;
                player.die();
                deadTime_BLUE=System.currentTimeMillis()+2500;
                dead = true;
            }
            if(dead) {
                g.drawImage(explode_r, tempx, tempy, null);
            }
        }
        else if (player.getWorldX()>1200){
            if(!dead) {
                 tempx = player.getWorldX() - 150;
                tempy = player.getWorldY() - worldY-65;
                player.die();
                deadTime_BLUE=System.currentTimeMillis()+2500;
                dead = true;
            }
            if(dead){
                g.drawImage(explode_l, tempx, tempy, null);

            }
        }

        if(player2.getColor() == Player.RED && player2.getMoveStatus()==Player.MOVE_RIGHT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_rnorm, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }

        else if(player2.getColor() == Player.RED && player2.getMoveStatus()==Player.MOVE_LEFT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_lnorm, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }

        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.JUMP_RIGHT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_rjump, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }
        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.PUNCH_RIGHT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_rpunch, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }
        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.PUNCH_LEFT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_lpunch, player2.getWorldX()-10, player2.getWorldY()-worldY, null);
        }
        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.KICK_RIGHT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_rkick, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }
        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.KICK_LEFT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){

            g.drawImage(red_lkick, player2.getWorldX()-20, player2.getWorldY()-worldY, null);
        }
        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.BLOCK_RIGHT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_rblock, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }
        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.BLOCK_LEFT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_lblock, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }
        else if(player2.getColor() == Player.RED  && player2.getMoveStatus()==Player.JUMP_LEFT && player2.getHitStatus()==Player.NOT_HIT && !player2.getIsDead()){
            g.drawImage(red_ljump, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }
        if(player2.getColor() == Player.RED  && player2.getHitStatus()==Player.IS_HIT_LEFT  && !player2.getIsDead()){
            g.drawImage(red_lhit, player2.getWorldX(), player2.getWorldY()-worldY, null);;
        }
        else if(player2.getColor() == Player.RED  && player2.getHitStatus()==Player.IS_HIT_RIGHT  && !player2.getIsDead()){
            g.drawImage(red_rhit, player2.getWorldX(), player2.getWorldY()-worldY, null);
        }

        if(player2.getDamage()>=Player.DAMAGE_CAP){
            if(!dead2) {
                tempx2 = player2.getWorldX() - 85;
                tempy2 = player2.getWorldY() - 95;
                player2.die();
                deadTime_RED=System.currentTimeMillis()+2500;
                dead2 = true;
            }
            if(dead2) {
                g.drawImage(explode_u, tempx2, tempy2-worldY, null);
            }
        }
        else if(player2.getWorldY() -worldY+54+80<0){
            if(!dead2) {
                tempx2 = player2.getWorldX() - 65;
                tempy2 = player2.getWorldY() - worldY + 45 +72;
                player2.die();
                deadTime_RED=System.currentTimeMillis()+2500;
                dead2 = true;
            }
            if(dead2) {
                g.drawImage(explode_d, tempx2, tempy2, null);
            }
        }
        else if (player2.getWorldY() -worldY>960){
            if(!dead2) {
               tempx2 = player2.getWorldX() - 85;
                tempy2 = player2.getWorldY() - worldY - 150;
                player2.die();
                deadTime_RED=System.currentTimeMillis()+2500;
                dead2 = true;
            }
            if(dead2) {
                g.drawImage(explode_u, tempx2, tempy2, null);
            }
        }
        else if (player2.getWorldX()+22<0){
            if(!dead2) {

                tempx2 = player2.getWorldX();
                tempy2 = player2.getWorldY() - worldY - 65;
                player2.die();
                deadTime_RED=System.currentTimeMillis()+2500;
                dead2 = true;
            }
            if(dead2) {
                g.drawImage(explode_r, tempx2, tempy2, null);
            }
        }
        else if (player2.getWorldX()>1200){
            if(!dead2) {
                tempx2 = player2.getWorldX() - 150;
                tempy2 = player2.getWorldY() - worldY-65;
                player2.die();
                deadTime_RED=System.currentTimeMillis()+2500;
                dead2 = true;
            }
            if(dead2) {
                g.drawImage(explode_l, tempx2, tempy2, null);
              //  player2.setLives(player2.getLives()-1);
                //System.out.println("P2 DEAD");
                //game.setStatus(MeleeGame.BLUE_WINS);

            }
        }

        g.setColor(Color.BLACK);
       // g.drawRect(player.getWorldX(), player.getWorldY()-worldY, (int)player.getRectangle().getWidth(), (int)player.getRectangle().getHeight());
        if(player.getMoveRect()!=null) {
            g.setColor(Color.GREEN);
            //g.drawRect((int) player.getMoveRect().getX(), (int) (player.getMoveRect().getY() - worldY), (int) player.getMoveRect().getWidth(), (int) player.getMoveRect().getHeight());
        }

        g.setColor(Color.BLACK);
       // g.drawRect(player2.getWorldX(), player2.getWorldY()-worldY, (int)player2.getRectangle().getWidth(), (int)player2.getRectangle().getHeight());
        if(player2.getMoveRect()!=null) {
            g.setColor(Color.GREEN);
            //g.drawRect((int) player2.getMoveRect().getX(), (int) (player2.getMoveRect().getY() - worldY), (int) player2.getMoveRect().getWidth(), (int) player2.getMoveRect().getHeight());
        }
        /*if(player.getMoveStatus()==Player.DEAD)
        {
            player.setRectangle(new Rectangle(0,0,0,0));
        }*/

        Font f2 = new Font("Times New Roman", Font.BOLD, 20);
        g.setFont(f2);

        g.setColor(Color.RED);
        for(int x=0; x<player2.getLives(); x++){
            g.fillOval(902+x*16, 949, 8, 8);
        }
        g.drawString("Player 2", 895, 944);

        g.setColor(Color.BLUE);
        for(int x=0; x<player.getLives(); x++){
            g.fillOval(312+x*16, 949, 8, 8);
        }
        g.drawString("Player 1", 305, 944);

        Font f3 = new Font("Times New Roman", Font.BOLD, 28);
        g.setFont(f3);

        g.setColor(Color.BLACK);
        if(player2.getDamage()>=100){
            g.drawString(String.valueOf(player2.getDamage()) + "%", 898, 923);
            g.drawString(String.valueOf(player2.getDamage()) + "%", 898, 925);
            g.drawString(String.valueOf(player2.getDamage()) + "%", 900, 923);
            g.drawString(String.valueOf(player2.getDamage()) + "%", 900, 925);
        }
        else {
            g.drawString(String.valueOf(player2.getDamage()) + "%", 904, 923);
            g.drawString(String.valueOf(player2.getDamage()) + "%", 904, 925);
            g.drawString(String.valueOf(player2.getDamage()) + "%", 906, 923);
            g.drawString(String.valueOf(player2.getDamage()) + "%", 906, 925);
        }

        if(player.getDamage()>=100) {
            g.drawString(String.valueOf(player.getDamage()) + "%", 308, 923);
            g.drawString(String.valueOf(player.getDamage()) + "%", 308, 925);
            g.drawString(String.valueOf(player.getDamage()) + "%", 310, 923);
            g.drawString(String.valueOf(player.getDamage()) + "%", 310, 925);
        }
        else{
            g.drawString(String.valueOf(player.getDamage()) + "%", 314, 923);
            g.drawString(String.valueOf(player.getDamage()) + "%", 314, 925);
            g.drawString(String.valueOf(player.getDamage()) + "%", 316, 923);
            g.drawString(String.valueOf(player.getDamage()) + "%", 316, 925);
        }

        int colorMOD2=(int)(player2.getDamage()*1.5);
        if(colorMOD2>=255){
            colorMOD2=255;
        }
        g.setColor(new Color(255, 255-colorMOD2, 255-colorMOD2));


        if(player2.getDamage()>=100){
            g.drawString(String.valueOf(player2.getDamage())+"%", 899, 924);
        }
        else {
            g.drawString(String.valueOf(player2.getDamage()) + "%", 905, 924);
        }



        int colorMOD=(int)(player.getDamage()*1.5);
        if(colorMOD>=255){
            colorMOD=255;
        }
        g.setColor(new Color(255, 255-colorMOD, 255-colorMOD ));

        if(player.getDamage()>=100){
            g.drawString(String.valueOf(player.getDamage())+"%", 309, 924);
        }
        else {
            g.drawString(String.valueOf(player.getDamage()) + "%", 315, 924);
        }

        if(game.getStatus()==game.TIE){
            Font f6 = new Font("Times New Roman", Font.BOLD, 50);
            g.setFont(f6);
            g.setColor(Color.BLACK);
            g.drawString("TIE! Press r to restart.",200,500);
        }

        else if(game.getStatus()==game.RED_WINS)
        {
            Font f5 = new Font("Times New Roman", Font.BOLD, 50);
            g.setFont(f5);
            g.setColor(Color.BLACK);
            g.drawString("Player 2 wins! Press r to restart.",200,500);
        }
        else if(game.getStatus()==game.BLUE_WINS)
        {
            Font f6 = new Font("Times New Roman", Font.BOLD, 50);
            g.setFont(f6);
            g.setColor(Color.BLACK);
            g.drawString("Player 1 wins! Press r to restart.",200,500);
        }


        s.drawImage(buffer,0,0,null);
    }

    public void keyPressed(KeyEvent e)
    {
        //System.out.println("key"+e.getKeyChar());
        if(e.getKeyCode()>255) {
            return;
        }
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()>255) {
            return;
        }
        keys[e.getKeyCode()] = false;

    }

    public void keyTyped(KeyEvent e)
    {
        if(e.getKeyChar() == 'r' || e.getKeyChar()== 'R'){
            game.setStatus(game.PLAYING);
            SoundEffect.EXPLODE.stop();
            songPlayer.setTempoFactor(1);
            game.setWaitCount(0);
            game.setWorldY(game.getNumRows()*20-920);
            game.getPlayer1().reset();
            game.getPlayer2().reset();
            game.setSpeed(0.75);
            dead=false;
            dead2=false;
        }
        else if(e.getKeyChar()=='y'){
            game.getPlayer1().respawn();
        }

    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public void addNotify()
    {
        super.addNotify();
        addKeyListener(this);
        requestFocus();
    }


}
