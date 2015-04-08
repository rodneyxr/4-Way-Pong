package singleplayer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will be the the view of the actual singleplayer game. Handles drawing of the balls, players, arena...
 * 
 * @author Rodney
 * 
 */
public class GameScreen extends Canvas {
	private static final long serialVersionUID = 1L;

	/* Model and View */
	private SinglePlayerModel model;
	/* Double Buffer */
	Image dbImage;
	Graphics dbg;

	/* FPS */
	int totalFrameCount;
	int fps;

	public int width;
	public int height;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param model
	 */
	public GameScreen(final SinglePlayerModel model) {
		this.model = model;
		width = 800;
		height = 800;
		this.setBackground(Color.BLACK); // Default Black Color
		this.setSize(width, height); // Default Size
		this.setVisible(true);
		this.setFocusable(true); // To read Keyboard Input
		width = this.getWidth();
		height = this.getHeight();
		TimerTask updateFPS = new TimerTask() {
			public void run() {
				fps = totalFrameCount;
				totalFrameCount = 0;
				model.getScoreBox().updateTime(SinglePlayerModel.getElapsedTime());

			}
		};

		Timer t = new Timer();
		t.scheduleAtFixedRate(updateFPS, 1000, 1000);
	}

	/**
	 * draws the fps counter at top right
	 * 
	 * @param g
	 */
	private void updateFPS(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(String.format("FPS: %d", fps), 10, 20);
		// g.drawString(String.format("Elapsed Time: %f", model.getElapsedTime()), 10, 40);
	}

	/**
	 * double buffered painting
	 */
	public void update(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paint(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	/**
	 * draw the gamescreen
	 */
	public void paint(Graphics g) {
		model.drawArena(g);

		model.drawCollisionGrid(g); // draw CollisionGrid
		model.drawBalls(g);
		model.drawInjectors(g);
		model.drawPlayer(g);
		model.drawAI(g);

		// draw a red border around arena
		g.setColor(Color.RED);
		g.drawRect(0, 0, width - 1, height - 1);

		totalFrameCount++;
		// draw FPS counter
		if (model.running())
			updateFPS(g);
	}

}
