package TetrisConfiguration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private String filePath;
    private Clip clip;
    private Thread musicThread;

    public void loadSound(String filePath) {
        this.filePath = filePath;
    }

    public synchronized void playSound(float volume) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Multi-threaded background music
    public void loopSoundInThread(float volume) {
        // Stop any existing thread before starting a new one
        stopMusicThread();

        musicThread = new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(volume);
                clip.loop(Clip.LOOP_CONTINUOUSLY);  // Continuous loop

                // Block the thread until the clip is closed
                synchronized (clip) {
                    clip.wait();
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        musicThread.start();
    }

    public synchronized void stopMusicThread() {
        if (musicThread != null && musicThread.isAlive()) {
            // Stop the clip and interrupt the thread
            stopSound();
            musicThread.interrupt();
            musicThread = null;
        }
    }
}
