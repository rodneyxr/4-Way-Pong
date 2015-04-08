package multiplayer;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.Timer;

public class MultiPlayer implements Runnable {

	private MultPlayerModel model;
	private PlayerView view;
	private MultiPlayerController controller;
	private BallInjectorController injectorController;
	private UpdateController updateController;

	private Thread thread;

	public MultiPlayer() {

	}

	public void startGame(String name, Color color) {
		model = new MultPlayerModel(name, color);
		view = new PlayerView(model);
		controller = new MultiPlayerController(model, view);
		injectorController = new BallInjectorController(model, view);
		updateController = new UpdateController(model, view);

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

	@Override
	public void run() {
		/* Count Down */
		int countDown = -1; // Disabled countdown for faster testing (was = 4)
		model.setCountDown(0); // delete this to make countdown work
		while (countDown >= 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			view.drawGameScreen();
			model.setCountDown(countDown--);
		}

		/* Start Game Loop */
		System.out.println("Entering multiplayer player game loop..."); // DEBUG

		model.setRunning(true);
		while (model.running()) {
			/* call draw methods */
			view.drawGameScreen();

			try {
				Thread.sleep(16); // about 60fps
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (model.gameOver())
				break;
		}
		System.out.println("*** MULTIPLAYER GAME OVER ***"); // DEBUG
	}
}
