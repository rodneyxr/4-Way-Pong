package singleplayer;

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
	private int lives;
	
	// Sound
	Sound paddleSound;

	// Power Up
	private final double POWER_UP_DURATION = 7;
	private double powerUpStartTime;

	// CollisionGrid
	private int[][] cells;

	// Player Color
	private Color color;

	// Player Position
	private Vector2D position; // coordinates referring to the middle of the paddle
	private Vector2D velocity;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param name
	 * @param color
	 */
	public Player(String name, Color color, int playerWidth, int lives) {
		this.name = name;
		this.color = color;
		this.lives = lives;
		this.playerWidth = playerWidth;
		powerUpStartTime = -1;
		playerHeight = 15;
		position = new Vector2D(Tools.getWidth() / 2, Tools.getHeight() - 30);
		velocity = new Vector2D();
		speed = 10.0f;
		cells = null;
		paddleSound = new Sound("paddlehit.wav");
	}
	
	/**
	 * kills the player
	 */
	public void killPlayer() {
		position = new Vector2D(Tools.getWidth() / 2, Tools.getHeight() - 30);
		setPlayerWidth(Tools.getWidth() - 200);
	}

	/**
	 * 
	 * @return the player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the player color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 
	 * @return the player width
	 */
	public int getPlayerWidth() {
		return playerWidth;
	}

	/**
	 * sets the player width
	 * 
	 * @param playerWidth
	 */
	public void setPlayerWidth(int playerWidth) {
		this.playerWidth = playerWidth;
	}

	/**
	 * 
	 * @return the player height
	 */
	public int getPlayerHeight() {
		return playerHeight;
	}

	/**
	 * sets the player height
	 * 
	 * @param playerHeight
	 */
	public void setPlayerHeight(int playerHeight) {
		this.playerHeight = playerHeight;
	}

	/**
	 * 
	 * @return the position of the player
	 */
	public Vector2D getPosition() {
		return new Vector2D(position);
	}

	/**
	 * 
	 * @return the velocity of the player
	 */
	public Vector2D getVelocity() {
		return new Vector2D(velocity);
	}

	/**
	 * deducts lives from the player
	 * 
	 * @param n
	 */
	public void deductLives(int n) {
		lives -= n;
		if (lives < 0)
			lives = 0;
	}

	/**
	 * 
	 * @return the cells that the player occupies
	 */
	public int[][] getCells() {
		return cells;
	}

	/**
	 * This method will handle collision between player and the ball
	 * 
	 * @param b
	 */
	public void checkCollision(Ball b) {
		Vector2D bPos = b.getPos();
		if (b.getVelocityY() >= 0 && bPos.y + b.getRadius() < position.y && Vector2D.distanceX(bPos, position) <= playerWidth / 2 + b.getRadius()
				&& Vector2D.distanceY(bPos, position) < b.getRadius() + playerHeight / 2) {
			if (b.getIsPowerUp()) {
				if (powerUpStartTime == -1)
					playerWidth += 50;
				powerUpStartTime = SinglePlayerModel.getElapsedTime();
				b.setNeedsDeletion(true);
			}
			b.setVelocityY(b.getVelocityY() * -1);
			paddleSound.play();
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
	 * updates the player in the collision grid
	 * @param cg
	 * @param elapsedTime
	 */
	public void update(CollisionGrid cg, double elapsedTime) {
		// update power up
		if (powerUpStartTime != -1) {
			if (elapsedTime - powerUpStartTime >= POWER_UP_DURATION) {
				playerWidth -= 50;
				powerUpStartTime = -1;
			}
		}

		cells = cg.update(this, position, playerWidth, playerHeight);
		cg.checkCell(this, cells);

		// compute bounds
		int leftBound = playerWidth / 2 + 102;
		int rightBound = Tools.getWidth() - playerWidth / 2 - 102;

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
		g.setColor(color);
		g.fillRoundRect((int) position.x - playerWidth / 2, (int) position.y - playerHeight / 2, playerWidth, playerHeight, 10, 10);
		g.setColor(Color.WHITE);
		g.drawRoundRect((int) position.x - playerWidth / 2, (int) position.y - playerHeight / 2, playerWidth, playerHeight, 10, 10);
		g.drawString(name, ((int) position.x) - g.getFontMetrics().stringWidth(name) / 2, (int) ((position.y - playerHeight / 2) + playerHeight) - 2);
	}
}
