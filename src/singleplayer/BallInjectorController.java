package singleplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class is called everytime that the ball injector timer goes off
 * @author Rodney
 *
 */
public class BallInjectorController implements ActionListener {

	private SinglePlayerModel model; // the model of the game

	/**
	 * CONSTRUCTOR
	 * @param model
	 */
	public BallInjectorController(SinglePlayerModel model) {
		this.model = model;
	}

	/**
	 * handles the ball deployment and makes the game get harder as elapsed time goes up
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		double t = SinglePlayerModel.getElapsedTime(); // get elapsed time
		int nextBall = 1000;
		float speed = 2.5f;

		if (t >= 250) {
			nextBall = 20;
		} else if (t >= 225) {
			nextBall = 100;
		} else if (t >= 200) {
			nextBall = 250;
		} else if (t >= 175) {
			nextBall = 500;
			speed = 6.0f;
		} else if (t >= 150) {
			nextBall = 1000;
			speed = 5.5f;
		} else if (t >= 125) {
			nextBall = 2000;
			speed = 5.25f;
		} else if (t >= 100) {
			nextBall = 2500;
			speed = 5.0f;
		} else if (t >= 60) {
			nextBall = 3000;
			speed = 4.0f;
		} else if (t >= 30) {
			nextBall = 4000;
			speed = 3.0f;
		} else if (t >= 15) {
			nextBall = 5000;
			speed = 2.75f;
		} else {
			nextBall = 7000;
			speed = 2.5f;
		}

		model.deployBall(nextBall, speed);
	}

}
