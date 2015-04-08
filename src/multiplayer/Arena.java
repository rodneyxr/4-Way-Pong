package multiplayer;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Holds Arena properties, ball injectors, and how to draw the arena (Should be drawn before anything else in the game loop)
 * 
 * @author Rodney
 * 
 */
public class Arena {

	private int nextBall;
	private Color backgroundColor;
	private int countDown;

	private int width;
	private int height;

	public Arena() {
		width = 800; // temp
		height = 800; // temp
		nextBall = 5000;
		backgroundColor = Color.BLACK;
		countDown = 5;
	}

	public void update() {
		
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	/**
	 * Draws the background and count down
	 * @param g
	 */
	public void draw(Graphics g) {
		// fill background
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		// draw count down
		if (countDown > 0) {
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(countDown), width / 2, height / 2);
		}
	}

}
