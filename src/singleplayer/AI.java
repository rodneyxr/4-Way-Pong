package singleplayer;

import java.awt.Color;
import java.awt.Graphics;

import tools.Tools;
import tools.Vector2D;

/**
 * This player class will contain the AI's, color, vector position, vector velocity
 * 
 * @author Rodney
 * 
 */
public class AI {

	// AI Properties
	String name; // the AI's name
	private float initialSpeed;
	private float speed; // maybe to incorporate a hardness setting?
	private int anchor; // 0=right,1=top,2=left,3=bottom
	private int paddleWidth; // how long the paddle is
	private int paddleHeight; // how tall the paddle is
	private int difficulty; // 0: easy, 1: medium, 2: hard, 3: godMode
	private int lives; // number of lives the AI has

	// Sound
	Sound paddleSound;

	// Power Up
	private final double POWER_UP_DURATION = 7;
	private double powerUpStartTime; // start time of the powerup

	// CollisionGrid
	private int[][] cells; // cells the AI occupies
	private int[][] fov; // field of view of the AI (the cells the AI can see)

	// Graphics
	private Color color; // color of the AI
	private int guiWidth; // will be used to draw the width of the paddle
	private int guiHeight; // used to draw the height of the paddle

	// AI Position
	private Vector2D position; // position of the AI
	private Vector2D velocity; // velocity of the AI

	/**
	 * 
	 * @param name
	 * @param color
	 */
	public AI(int anchor, String name, Color color, int paddleWidth, float speed, int lives, int difficulty) {
		this.anchor = anchor;
		this.name = name;
		this.color = color;
		this.lives = lives;
		this.paddleWidth = paddleWidth;
		this.paddleHeight = 15;
		this.initialSpeed = speed;
		this.speed = speed;
		this.difficulty = difficulty;
		this.powerUpStartTime = -1;
		paddleSound = new Sound("paddlehit.wav");
		if (anchor == 0) { // right
			position = new Vector2D(Tools.getWidth() - 30, Tools.getHeight() / 2);
			guiWidth = paddleHeight;
			guiHeight = paddleWidth;
			fov = new int[6 * 15][2];
			// fill the sub-grid
			for (int i = 10, x = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					fov[x][0] = Math.max(0, Math.min((i + 0), 15)); // math.min makes sure that the cell stays within the main-grid
					fov[x][1] = Math.max(0, Math.min((j + 0), 15));
					x++;
				}
			}
		} else if (anchor == 1) { // top
			position = new Vector2D(Tools.getWidth() / 2, 30);
			guiHeight = paddleHeight;
			guiWidth = paddleWidth;

			fov = new int[6 * 15][2];
			// fill the sub-grid
			for (int j = 0, x = 0; j < 6; j++) {
				for (int i = 0; i < 15; i++) {
					fov[x][0] = Math.max(0, Math.min((i + 0), 15)); // math.min makes sure that the cell stays within the main-grid
					fov[x][1] = Math.max(0, Math.min((j + 0), 15));
					x++;
				}
			}

		} else if (anchor == 2) { // left
			position = new Vector2D(30, Tools.getHeight() / 2);
			guiWidth = paddleHeight;
			guiHeight = paddleWidth;
			fov = new int[6 * 15][2];
			// fill the sub-grid
			for (int i = 0, x = 0; i < 6; i++) {
				for (int j = 0; j < 15; j++) {
					fov[x][0] = Math.max(0, Math.min((i + 0), 15)); // math.min makes sure that the cell stays within the main-grid
					fov[x][1] = Math.max(0, Math.min((j + 0), 15));
					x++;
				}
			}
		}
		velocity = new Vector2D();
	}

	/**
	 * kills the AI
	 */
	public void killAI() {
		if (anchor == 0) {
			position = new Vector2D(Tools.getWidth() - 30, Tools.getHeight() / 2);
		} else if (anchor == 1) {
			position = new Vector2D(Tools.getWidth() / 2, 30);
		} else if (anchor == 2) {
			position = new Vector2D(30, Tools.getHeight() / 2);
		}
		setPaddleLength(Tools.getWidth() - 200);
	}

	/**
	 * 
	 * @return the name of the AI
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the color of the AI
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the AI
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the difficulty of the AI
	 * 
	 * @param difficulty
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * toggles the difficulty of the AI
	 */
	public void toggleDifficulty() {
		difficulty++;
		if (difficulty > 3)
			difficulty = -1;
		if (difficulty == 0)
			speed = initialSpeed / 4;
		else
			speed = initialSpeed;
	}

	/**
	 * 
	 * @return the string difficulty
	 */
	public String getDifficulty() {
		String s = null;
		switch (difficulty) {
			case -1:
				s = "Controlled";
				break;
			case 0:
				s = "Easy";
				break;
			case 1:
				s = "Medium";
				break;
			case 2:
				s = "Hard";
				break;
			case 3:
				s = "Extreme";
				break;
		}
		return s;
	}

	/**
	 * deducts lives from the AI
	 * 
	 * @param n
	 */
	public void deductLives(int n) {
		lives -= n;
		if (lives < 0)
			lives = 0;
	}

	/**
	 * sets the length of the paddle
	 * 
	 * @param length
	 */
	public void setPaddleLength(int length) {
		paddleWidth = length;
		if (anchor == 0) { // right
			guiWidth = paddleHeight;
			guiHeight = paddleWidth;
		} else if (anchor == 1) { // top
			guiHeight = paddleHeight;
			guiWidth = paddleWidth;
		} else if (anchor == 2) { // left
			guiWidth = paddleHeight;
			guiHeight = paddleWidth;
		}
	}

	/**
	 * 
	 * @return the guiWidth of the AI
	 */
	public int getGuiWidth() {
		return guiWidth;
	}

	/**
	 * 
	 * @return the guiHeight of the AI
	 */
	public int getGuiHeight() {
		return guiHeight;
	}

	/*
	 * returns the position of the AI
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * 
	 * @return the cells of the Collision grid this object occupies
	 */
	public int[][] getCells() {
		return cells;
	}

	/**
	 * This method will handle collision between all AI and the ball
	 * 
	 * @param b
	 */
	public void checkCollision(Ball b) {
		Vector2D bPos = b.getPos();
		if (anchor == 0) { // right side
			if (b.getVelocityX() >= 0 && bPos.x + b.getRadius() < position.x && Vector2D.distanceY(bPos, position) <= paddleWidth / 2 + b.getRadius()
					&& Vector2D.distanceX(bPos, position) < b.getRadius() + paddleHeight / 2) {
				b.setVelocityX(b.getVelocityX() * -1);
				if (b.getIsPowerUp()) {
					if (powerUpStartTime == -1)
						setPaddleLength(paddleWidth + 50);
					powerUpStartTime = SinglePlayerModel.getElapsedTime();
					b.setNeedsDeletion(true);
				} else
					paddleSound.play();
			}
		} else if (anchor == 1) { // top side
			if (b.getVelocityY() <= 0 && bPos.y + b.getRadius() > position.y && Vector2D.distanceX(bPos, position) <= paddleWidth / 2 + b.getRadius()
					&& Vector2D.distanceY(bPos, position) < b.getRadius() + paddleHeight / 2) {
				b.setVelocityY(b.getVelocityY() * -1);
				if (b.getIsPowerUp()) {
					if (powerUpStartTime == -1)
						setPaddleLength(paddleWidth + 50);
					powerUpStartTime = SinglePlayerModel.getElapsedTime();
					b.setNeedsDeletion(true);
				} else
					paddleSound.play();
			}
		} else if (anchor == 2) { // left side
			if (b.getVelocityX() <= 0 && bPos.x + b.getRadius() > position.x && Vector2D.distanceY(bPos, position) <= paddleWidth / 2 + b.getRadius()
					&& Vector2D.distanceX(bPos, position) < b.getRadius() + paddleHeight / 2) {
				b.setVelocityX(b.getVelocityX() * -1);
				if (b.getIsPowerUp()) {
					if (powerUpStartTime == -1)
						setPaddleLength(paddleWidth + 50);
					powerUpStartTime = SinglePlayerModel.getElapsedTime();
					b.setNeedsDeletion(true);
				} else
					paddleSound.play();
			}
		}
	}

	/**
	 * Move the paddle according to the direction entered by user
	 * 
	 * @param dir
	 *            dir: 0 stop, 1 left, 2 right
	 */
	public void movement(int dir) {
		if (difficulty == -1) {
			if (anchor == 1) {
				if (dir == 0) { // stop movement
					velocity.x = 0;
				} else if (dir == 1) { // move left
					velocity.x = -speed;
				} else if (dir == 2) { // move right
					velocity.x = speed;
				}
			} else {
				if (dir == 0) { // stop movement
					velocity.y = 0;
				} else if (dir == 1) { // move down
					velocity.y = speed;
				} else if (dir == 2) { // move up
					velocity.y = -speed;
				}
			}
		}
	}

	/**
	 * This method will update the players position ever tick according to its velocity
	 */
	private void move() {
		// compute bounds
		int leftBound = paddleWidth / 2 + 102;
		int rightBound = Tools.getWidth() - paddleWidth / 2 - 102;

		// update players position
		if (anchor == 1 || anchor == 3) { // if top or bottom
			if (position.x >= leftBound && position.x <= rightBound) {
				position.x += velocity.x;
				position.y += velocity.y;
			}
		} else {
			if (position.y >= leftBound && position.y <= rightBound) {
				position.x += velocity.x;
				position.y += velocity.y;
			}
		}

		// keep player within pillars
		if (anchor == 1 || anchor == 3) { // if top or bottom
			if (position.x <= leftBound)
				position.x = leftBound;
			else if (position.x > rightBound)
				position.x = rightBound;
		} else {
			if (position.y <= leftBound)
				position.y = leftBound;
			else if (position.y > rightBound)
				position.y = rightBound;
		}
	}

	/**
	 * this method is the brains of the AI
	 * 
	 * @param cg
	 */
	private void followBall(CollisionGrid cg) {
		if (difficulty == -1)
			return;
		/**
		 * Right AI
		 */
		if (anchor == 0) { // Right side
			Ball b = cg.look(fov, position, anchor, difficulty);
			if (b != null) { // if ai found a ball to follow
				if (b.getVelocityX() > 0 && !(Vector2D.distanceY(position, b.getPos()) < paddleWidth / 2)) {
					if (difficulty == 3) { // extreme
						position.y = b.getPy();
					} else if (b.getPy() < position.y) // if ball is above
						velocity.y = -speed;
					else
						velocity.y = speed;
				} else
					velocity.y = 0;
			}

			/**
			 * Top AI
			 */
		} else if (anchor == 1) { // top side
			Ball b = cg.look(fov, position, anchor, difficulty);
			if (b != null) { // if ai found a ball to follow
				if (b.getVelocityY() < 0 && !(Vector2D.distanceX(position, b.getPos()) < paddleWidth / 2)) {
					if (difficulty == 3) { // extreme
						position.x = b.getPx();
					} else if (b.getPx() < position.x) // if ball is above
						velocity.x = -speed;
					else
						velocity.x = speed;
				} else
					velocity.x = 0;
			}

			/**
			 * Left AI
			 */
		} else if (anchor == 2) { // left side
			Ball b = cg.look(fov, position, anchor, difficulty);
			if (b != null) { // if ai found a ball to follow
				if (b.getVelocityX() < 0 && !(Vector2D.distanceY(position, b.getPos()) < paddleWidth / 2)) {
					if (difficulty == 3) { // extreme
						position.y = b.getPy();
					} else if (b.getPy() < position.y) // if ball is above
						velocity.y = -speed;
					else
						velocity.y = speed;
				} else
					velocity.y = 0;
			}
		} // anchor will never go to bottom unless all players are ai
	}

	/**
	 * This method should be updated in the game loop every set amount of ticks. It will constantly follow the ball at a set speed.
	 * 
	 * @param cg
	 */
	void update(CollisionGrid cg, double elapsedTime) {
		// update power up
		if (powerUpStartTime != -1) {
			if (elapsedTime - powerUpStartTime >= POWER_UP_DURATION) {
				setPaddleLength(paddleWidth - 50);
				powerUpStartTime = -1;
			}
		}

		cells = cg.update(this, position, guiWidth, guiHeight);
		cg.checkCell(this, cells);
		followBall(cg);
		move();
	}

	/**
	 * This method will draw the AI's paddle every frame
	 * 
	 * @param g
	 */
	void draw(Graphics g) {
		g.setColor(color); // set color of the paddle
		g.fillRoundRect((int) position.x - guiWidth / 2, (int) position.y - guiHeight / 2, guiWidth, guiHeight, 10, 10);
		g.setColor(Color.WHITE);
		g.drawRoundRect((int) position.x - guiWidth / 2, (int) position.y - guiHeight / 2, guiWidth, guiHeight, 10, 10);
		g.drawString(name, ((int) position.x) - g.getFontMetrics().stringWidth(name) / 2, (int) ((position.y - paddleHeight / 2) + paddleHeight) - 2);
	}

}