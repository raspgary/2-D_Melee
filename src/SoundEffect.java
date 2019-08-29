/**
 * Created by othscs018 on 10/24/2016.
 */
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
public enum SoundEffect {
    EXPLODE("Sounds_files\\explosion_convertedLOUD.wav"),
    EXPLODE2("Sounds_files\\explosion_convertedLOUD.wav"),
    HURT("Sounds_files\\hurt_convertedLOUD.wav"),
    HURT2("Sounds_files\\argh_convertedLOUD.wav"),
    HIT("Sounds_files\\hit_converted.wav"),
  //  BG("Sounds_files\\Panjabi_MC.wav"),
    JUMP("Sounds_files\\jump_convertedQUIET.wav"),
    JUMP2("Sounds_files\\jump_convertedQUIET.wav");

    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.HIGH;


    private Clip clip;
    SoundEffect(String soundFileName){
        try{
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play() {
    //    if (volume != Volume.MUTE) {
            //System.out.println("play sound");
           if (clip.isRunning())
               clip.stop();   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
    }

    public void stop(){
        clip.stop();
    }

    public void stopAll(){

    }
    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
    }