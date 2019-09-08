import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

// enum for sound effects and background music
public enum SoundEffect {
    EXPLODE("Sounds_files\\explosion_convertedLOUD.wav"),
    EXPLODE2("Sounds_files\\explosion_convertedLOUD.wav"),
    HURT("Sounds_files\\hurt_convertedLOUD.wav"),
    HURT2("Sounds_files\\argh_convertedLOUD.wav"),
    HIT("Sounds_files\\hit_converted.wav"),
    JUMP("Sounds_files\\jump_convertedQUIET.wav"),
    JUMP2("Sounds_files\\jump_convertedQUIET.wav");

    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.HIGH;


    private Clip clip;
    SoundEffect(String soundFileName){
        try{
            // get URL with soundFileName (path)
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            // set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // get a clip resource.
            clip = AudioSystem.getClip();
            // open audio clip and load samples from the audio input stream.
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
    	// Stop the player if it is still running
        if (clip.isRunning())
           clip.stop();
       	// rewind to beginning
        clip.setFramePosition(0);
        // start playing
        clip.start();
    }

    public void stop(){
        clip.stop();
    }

    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
}