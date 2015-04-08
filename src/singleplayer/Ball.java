package singleplayer;

import java.awt.Color;
import java.awt.Graphics;

import tools.Tools;
import tools.Vector2D;

/**
 * This class will hold the Ball's properties
 * 
 * @author Rodney
 * 
 */

public class Ball {

	private static final int SCORE_MARGIN = 30; // how close the balls center needs to be to the edge in order to be scored

	// Properties
	private Color color; // the color of the ball
	private int radius; // the radius of the ball
	private Vector2D velocity; // the velocity of the ball
	private Vector2D position; // the position of the ball
	private float speed; // the initial speed of the ball
	private float mass; // the mass of the balls
	private boolean isSuperBall; // is the ball a superball
	private boolean isPowerUp; // is the ball a powerup
	private boolean needsDeletion; // if this is the true the ball will be removed next update

	// CollisionGrid
	private int[][] cells; // the cells the ball occupies

	// This variable will be -1 if it is in the arena.
	// If it happens to go outside the arena into another players goal, it will change corresponding to the side it went out on.
	// 0 - if it went out on top
	// 1 - if it went out on right
	// 2 - bottom (player)
	// 3 - left
	// Once the number changes, it this object will be deleted by the model and the player will be docked point(s)
	private int wasScored; // ^
	private boolean hasBeenDeployed; // delay collision checking until ball is in play

	/**
	 * CONSTRUCTOR
	 * 
	 * @param position
	 * @param velocity
	 * @param radius
	 * @param color
	 */
	public Ball(Vector2D position, Vector2D velocity, float speed, float mass, int radius, Color color, boolean isSuperBall, boolean isPowerUp) {
		this.position = position;
		this.velocity = velocity;
		this.radius = radius;
		this.color = color;
		this.wasScored = -1;
		this.hasBeenDeployed = false;
		this.speed = speed;
		this.mass = mass;
		this.isSuperBall = isSuperBall;
		this.isPowerUp = isPowerUp;
		this.needsDeletion = false;
	}

	/**
	 * returns if the ball was scored
	 * 
	 * @return
	 */
	public int wasScored() {
		return wasScored;
	}

	/**
	 * 
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * 
	 * @return the speed of the ball
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * 
	 * @return the velocity of the ball
	 */
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
	 * 
	 * @return return the velocity.x
	 */
	public float getVelocityX() {
		return velocity.x;
	}

	/**
	 * 
	 * @return return the velocity.y
	 */
	public float getVelocityY() {
		return velocity.y;
	}

	/**
	 * sets the velocity.x
	 * @param x
	 */
	public void setVelocityX(float x) {
		velocity.x = x;
	}

	/**
	 * return the velocity.y
	 * @param y
	 */
	public void setVelocityY(float y) {
		velocity.y = y;
	}

	/**
	 * sets the position of the ball
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	/**
	 * set the position.x of the ball
	 * @param x
	 */
	public void setPositionX(float x) {
		position.x = x;
	}

	/**
	 * set the position.y of the ball
	 * @param y
	 */
	public void setPositionY(float y) {
		position.y = y;
	}

	/**
	 * 
	 * @return the x value of the position
	 */
	public int getPx() {
		return (int) position.getX();
	}

	/**
	 * 
	 * @return the y value of the position
	 */
	public int getPy() {
		return (int) position.getY();
	}

	/**
	 * 
	 * @return the mass of the ball
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * 
	 * @return if the ball has been deployed into the came and is able to start colliding
	 */
	public boolean hasBeenDeployed() {
		return hasBeenDeployed;
	}

	/**
	 * 
	 * @return the position of the ball
	 */
	public Vector2D getPos() {
		return position;
	}

	/**
	 * 
	 * @return the cells that the the ball occupies
	 */
	public int[][] getCells() {
		return cells;
	}

	/**
	 * able to set if the ball needs to be deleted in the next frame
	 * @param nD
	 */
	public void setNeedsDeletion(Boolean nD) {
		needsDeletion = nD;
	}

	/**
	 * 
	 * @return if the ball needs to be deleted in the next frame
	 */
	public boolean getNeedsDeletion() {
		return needsDeletion;
	}

	/**
	 * 
	 * @return if the ball is a super ball
	 */
	public boolean getIsSuperBall() {
		return isSuperBall;

	}

	/**
	 * 
	 * @return if the ball is a powerup
	 */
	public boolean getIsPowerUp() {
		return isPowerUp;
	}

	/**
	 * Will update wasScored depending on what side the ball exits the arena on
	 */
	private void checkIfBallScored() {
		if (hasBeenDeployed) { // must be in arena before it starts checking this
			if (position.y < SCORE_MARGIN) // if scored on top - 0
				wasScored = 0;
			else if (position.x > Tools.getWidth() - SCORE_MARGIN) // if scored on right - 1
				wasScored = 1;
			else if (position.y > Tools.getHeight() - SCORE_MARGIN) // if scored on bottom - 2
				wasScored = 2;
			else if (position.x < SCORE_MARGIN)// if scored on left - 3
				wasScored = 3;
		} else if (!(position.y < (99 + radius) || position.x > Tools.getWidth() - (99 + radius) || position.y > Tools.getHeight() - (99 + radius) || position.x < (99 + radius)))
			hasBeenDeployed = true;
	}

	/**
	 * This method will remove itself from the CollisionGrid before its removed from the game
	 */
	public void removeFromGrid(CollisionGrid cg) {
		cg.removeAllOf(this);
	}

	/**
	 * Checks for ball to ball collision
	 * 
	 * @param b
	 */
	public void checkCollision(Ball b) {
		if (position.distance(b.getPos()) < radius + b.getRadius() && hasBeenDeployed && b.hasBeenDeployed && isSuperBall == b.isSuperBall) {
			Vector2D v1 = new Vector2D(velocity);
			Vector2D v2 = new Vector2D(b.getVelocity());

			// calculate new velocities
			v1.x = (velocity.x * (mass - b.getMass()) + (2 * b.getMass() * b.getVelocity().x)) / (mass + b.getMass());
			v1.y = (velocity.y * (mass - b.getMass()) + (2 * b.getMass() * b.getVelocity().y)) / (mass + b.getMass());
			v2.x = (b.getVelocity().x * (b.getMass() - mass) + (2 * mass * velocity.x)) / (mass + b.getMass());
			v2.y = (b.getVelocity().y * (b.getMass() - mass) + (2 * mass * velocity.y)) / (mass + b.getMass());

			// set ball1 velocity
			velocity = v1;

			// set ball2 velocity
			b.setVelocityX(v2.x);
			b.setVelocityY(v2.y);

			// seperate the balls

			move();
			b.move();
		} else if (position.distance(b.getPos()) < radius + b.getRadius() && hasBeenDeployed && b.hasBeenDeployed && isSuperBall != b.isSuperBall) {
			// if(this.isSuperBall == b.isSuperBall){
			Vector2D v1 = new Vector2D(velocity);
			Vector2D v2 = new Vector2D(b.getVelocity());

			// calculate new velocities
			v1.x = (velocity.x * (mass - b.getMass()) + (2 * b.getMass() * b.getVelocity().x)) / (mass + b.getMass());
			v1.y = (velocity.y * (mass - b.getMass()) + (2 * b.getMass() * b.getVelocity().y)) / (mass + b.getMass());
			v2.x = (b.getVelocity().x * (b.getMass() - mass) + (2 * mass * velocity.x)) / (mass + b.getMass());
			v2.y = (b.getVelocity().y * (b.getMass() - mass) + (2 * mass * velocity.y)) / (mass + b.getMass());

			// set ball1 velocity
			velocity = v1;

			// set ball2 velocity
			b.setVelocityX(v2.x);
			b.setVelocityY(v2.y);

			// seperate the balls
			move();
			b.move();

			if (isSuperBall) {
				radius += b.getRadius() / 2;
				mass += b.getMass();
				b.setNeedsDeletion(true);
				if (radius >= 50)
					needsDeletion = true;
			}

			else if (b.isSuperBall) {
				b.radius += getRadius() / 2;
				b.mass += getMass();
				needsDeletion = true;
				if (b.radius >= 50)
					b.setNeedsDeletion(true);

			}
		}

	}

	/**
	 * updates the position of the ball
	 */
	public void move() {
		position.x += velocity.x;
		position.y += velocity.y;
	}

	/**
	 * Will check for collision and check if ball has gone out of arena
	 * 
	 * @param ball
	 */
	public void update(Player player, AI[] ai, CollisionGrid cg) {
		cells = cg.update(this, position, radius * 2, radius * 2);
		cg.checkCell(this, cells);
		checkIfBallScored();
		// move the ball
		move();
	}

	/**
	 * Draw the ball object onto the GameScreen
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		if (!isPowerUp) {
			g.drawOval((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
			g.setColor(color);
			g.fillOval((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
		} else {
			g.drawRect((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
			g.setColor(color);
			g.fillRect((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
		}
	}

}