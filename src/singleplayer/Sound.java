package singleplayer;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 * this class will handle sound
 * 
 * @author Rodney
 * 
 */
public class Sound {
	AudioClip clip; // the sound file

	/**
	 * this method loads the sound file
	 * 
	 * @param filename
	 */
	@SuppressWarnings("deprecation")
	public Sound(String filename) {
		File file = new File(filename);
		try {
			clip = Applet.newAudioClip(file.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method loops the sound
	 */
	public void loop() {
		clip.loop();

	}

	/**
	 * this method plays the sound
	 */
	public void play() {
		stop();
		clip.play();

	}

	/**
	 * this method stops the sound
	 */
	public void stop() {
		clip.stop();
	}
}
