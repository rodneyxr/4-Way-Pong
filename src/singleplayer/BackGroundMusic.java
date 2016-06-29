package singleplayer;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * this class will handle the background music for the game
 * 
 * @author Rodney
 *
 */
public class BackGroundMusic implements Runnable {
	private Thread t;
	private String threadName;

	/**
	 * constructor
	 * 
	 * @param name
	 */
	public BackGroundMusic(String name) {
		this.threadName = name;
	}

	/**
	 * starts the thread for the background music
	 */
	public void run() {
		File audioFile = new File("Afterglow.wav");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("The specified audio file is not supported.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error playing the audio file.");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			System.out.println("Audio line for playing back is unavailable.");
			e.printStackTrace();
		}
	}

	/**
	 * start the background music
	 */
	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

}
