package singleplayer;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * this is the main class that starts the game
 * @author Rodney
 *
 */
public class SinglePlayer implements Runnable {

	private SinglePlayerModel model; // the model of the game
	private SinglePlayerView view; // the view of the game
	private SinglePlayerController controller; // controller for player input
	private BallInjectorController injectorController; // controls the injectors
	private UpdateController updateController; // controlls the tick
	private Thread thread; // thread the game will be run on

	/**
	 * this method starts the whole game up
	 * 
	 * @param name
	 * @param color
	 */
	public void startGame(String name, Color color) {
		model = new SinglePlayerModel(name, color);
		view = new SinglePlayerView(model);
		controller = new SinglePlayerController(model, view);
		injectorController = new BallInjectorController(model);
		updateController = new UpdateController(model);

		// register listeners
		view.registerListeners(controller);
		model.setInjectorTimer(new Timer(0, injectorController)); // update ballInjector every x seconds
		model.setUpdateTimer(new Timer(10, updateController)); // about 100 ticks per second

		// start it up
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(1200, 835);
		view.setResizable(false);
		view.setVisible(true);

		thread = new Thread(this);
		thread.start();
	}

	/**
	 * this is the actual game loop
	 */
	@Override
	public void run() {
		model.startBackGroundMusic();
		/* Count Down */
		int countDown = 5; // Disabled countdown (-1) for faster testing (was = 4)
		model.setCountDown(0); // delete this to make countdown work
		while (countDown >= 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			view.drawGameScreen();
			model.playCountDownSound();
			model.setCountDown(countDown--);
		}
		SinglePlayerModel.setStartTime(System.currentTimeMillis());
		
		/* Start Game Loop */
		System.out.println("Entering single player game loop..."); // DEBUG

		model.setRunning(true);
		while (!model.gameOver()) {
			while (model.running()) {
				/* call draw methods */
				view.drawGameScreen();

				if (model.lockFPS())
					try {
						Thread.sleep(16); // about 60fps
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				if (model.gameOver())
					break;
			}
			// System.out.println("*** SINGLEPLAYER GAME OVER ***"); // DEBUG
		}
	}
}
