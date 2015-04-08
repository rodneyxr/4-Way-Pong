package singleplayer;
	
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.InputStream;

	import sun.audio.AudioPlayer;
	import sun.audio.AudioStream;

	/**
	 * this class will handle the background music for the game
	 * @author Rodney
	 *
	 */
	public class BackGroundMusic implements Runnable {
		private Thread t;
		private String threadName;
		
		/**
		 * constructor
		 * @param name
		 */
		public BackGroundMusic(String name) {
			this.threadName = name;
		}

		/**
		 * starts the thread for the background music
		 */
		public void run() {
			
			InputStream in = null;
			AudioStream as = null;
			try{
				in = new FileInputStream("Afterglow.wav");
				as = new AudioStream(in);
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			AudioPlayer.player.start(as);
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AudioPlayer.player.stop(as);
		}
		
		/**
		 * start the background music
		 */
		public void start()
		{
			if (t == null){
				t = new Thread (this, threadName);
				t.start();
			}
		}

}
