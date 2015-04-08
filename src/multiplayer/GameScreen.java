package multiplayer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 * This class will be the the view of the actual singleplayer game. Handles drawing of the balls, players, arena...
 * 
 * @author Rodney
 * 
 */
public class GameScreen extends Canvas {
	private static final long serialVersionUID = 1L;

	/* Model and View */
	private MultPlayerModel model;
	private PlayerView view;

	/* Double Buffer */
	Image dbImage;
	Graphics dbg;

	public int width;
	public int height;

	/* Constructor */
	public GameScreen(MultPlayerModel model, PlayerView view) {
		this.model = model;
		this.view = view;
		width = 800;
		height = 800;
		this.setBackground(Color.BLACK); // Default Black Color
		this.setSize(width, height); // Default Size
		this.setVisible(true);
		this.setFocusable(true); // To read Keyboard Input
		width = this.getWidth();
		height = this.getHeight();
	}

	public void update(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paint(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void paint(Graphics g) {
		model.drawArena(g);

		// draw CollisionGrid
		model.drawCollisionGrid(g);

		model.drawBalls(g);
		model.drawInjectors(g);
		model.drawPlayer(g);

		// draw a red border around arena
		g.setColor(Color.RED);
		g.drawRect(0, 0, width - 1, height - 1);
	}

}
