package multiplayer;

import java.awt.Color;
import java.awt.Graphics;

import tools.Tools;
import tools.Vector2D;

/**
 * This player class will contain the player's name, color, vector position, vector velocity
 * 
 * @author Rodney
 * 
 */
public class Player {

	// Player Properties
	private String name;
	private int playerWidth;
	private int playerHeight;
	private float speed;

	// CollisionGrid
	private int[][] cells;

	// Player Color
	private Color color;

	// Player Position
	private Vector2D position; // coordinates referring to the middle of the paddle
	private Vector2D velocity;

	/**
	 * 
	 * @param name
	 * @param color
	 */
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		playerWidth = 99;
		playerHeight = 15;
		position = new Vector2D(Tools.getWidth() / 2, Tools.getHeight() - 30);
		velocity = new Vector2D();
		speed = 10.0f;
		cells = null;
	}

	public int getPlayerWidth() {
		return playerWidth;
	}

	public void setPlayerWidth(int playerWidth) {
		this.playerWidth = playerWidth;
	}

	public int getPlayerHeight() {
		return playerHeight;
	}

	public void setPlayerHeight(int playerHeight) {
		this.playerHeight = playerHeight;
	}

	public Vector2D getPosition() {
		return new Vector2D(position);
	}

	public Vector2D getVelocity() {
		return new Vector2D(velocity);
	}

	public int[][] getCells() {
		return cells;
	}

	/**
	 * This method will handle collision between player and the ball
	 * @param b
	 */
	public void checkCollision(Ball b) {
		Vector2D bPos = b.getPos();
		if (b.getVelocityY() >= 0 && bPos.y + b.getRadius() < position.y && Vector2D.distanceX(bPos, position) <= playerWidth / 2 + b.getRadius()
				&& Vector2D.distanceY(bPos, position) < b.getRadius() + playerHeight / 2) {
			b.setVelocityY(b.getVelocityY() * -1);
		}
	}

	/**
	 * Move the paddle according to the direction entered by user
	 * 
	 * @param dir
	 *            dir: 0 stop, 1 left, 2 right
	 */
	public void move(int dir) {
		if (dir == 0) { // stop movement
			velocity.x = 0;
		} else if (dir == 1) { // move left
			velocity.x = -speed;
		} else if (dir == 2) { // move right
			velocity.x = speed;
		}
	}

	/**
	 * 
	 * @param dim
	 */
	public void update(CollisionGrid cg) {
		cells = cg.update(this, position, playerWidth, playerHeight);
		cg.checkCell(this, cells);
		// compute bounds
		int leftBound = playerWidth / 2 + 100;
		int rightBound = Tools.getWidth() - playerWidth / 2 - 100;

		// update players position
		if (position.x >= leftBound && position.x <= rightBound)
			position.x += velocity.x;
		position.y += velocity.y; // y coordinate will never change

		// keep player within pillars
		if (position.x <= leftBound)
			position.x = leftBound;
		else if (position.x > rightBound)
			position.x = rightBound;
	}

	/**
	 * This method will draw the player's paddle every frame
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		// code to draw the paddle using the position in this class
		g.setColor(color);
		g.fillRoundRect((int) position.x - playerWidth / 2, (int) position.y - playerHeight / 2, playerWidth, playerHeight, 10, 10);
		g.setColor(Color.WHITE);
		g.drawRoundRect((int) position.x - playerWidth / 2, (int) position.y - playerHeight / 2, playerWidth, playerHeight, 10, 10);
		g.drawString(name, ((int) position.x - playerWidth / 2) + g.getFontMetrics().stringWidth(name), (int) ((position.y - playerHeight / 2) + playerHeight) - 2);
	}

}
