package multiplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import tools.Tools;
import tools.Vector2D;

/**
 * This class will hold the Ball's properties
 * 
 * @author Rodney
 * 
 */
public class Ball {

	private static final int SCORE_MARGIN = 0;

	private Color color; // the color of the ball
	private int radius; // the radius of the ball
	private Vector2D velocity; // the velocity of the ball
	private Vector2D position; // the position of the ball
	private float speed;

	// CollisionGrid
	private int[][] cells;

	// This variable will be -1 if it is in the arena.
	// If it happens to go outside the arena into another players goal, it will change corresponding to the side it went out on.
	// 0 - if it went out on top
	// 1 - if it went out on right
	// 2 - bottom (player)
	// 3 - left
	// Once the number changes, it this object will be deleted by the model and the player will be docked point(s)
	private int wasScored;

	private boolean hasBeenDeployed;

	/**
	 * 
	 * @param position
	 * @param velocity
	 * @param radius
	 * @param color
	 */
	public Ball(Vector2D position, Vector2D velocity,float speed, int radius, Color color) {
		this.position = position;
		this.velocity = velocity;
		this.radius = radius;
		this.color = color;
		this.wasScored = -1;
		this.hasBeenDeployed = false;
		this.speed = speed;
	}

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
	
	public float getSpeed() {
		return speed;
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}

	public float getVelocityX() {
		return velocity.x;
	}

	public float getVelocityY() {
		return velocity.y;
	}

	public void setVelocityX(float x) {
		velocity.x = x;
	}

	public void setVelocityY(float y) {
		velocity.y = y;
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

	public boolean hasBeenDeployed() {
		return hasBeenDeployed;
	}

	public Vector2D getPos() {
		return position;
	}

	public int[][] getCells() {
		return cells;
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
			else if (position.x < SCORE_MARGIN) // if scored on left - 3
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
		// TODO: This is where ball to ball collision should be implemented
		// TODO: Everything in here is probably completely wrong...
		// if (canCollide && b.canCollide)
		// if (position.distance(b.getPos()) < radius + b.getRadius()) {
		// Vector2D tempVec = new Vector2D(velocity);
		// velocity.x = (float) (Math.cos(velocity.x) - Math.sin(b.velocity.y));
		// velocity.y = (float) (Math.sin(velocity.y) - Math.cos(b.velocity.y));
		// b.velocity.x = (float) (Math.cos(b.velocity.x) - Math.sin(tempVec.y));
		// b.velocity.y = (float) (Math.sin(b.velocity.y) - Math.cos(tempVec.x));
		// canCollide = false;
		// b.canCollide = false;
		// }

	}

	public void checkCollision(BallInjector bi) {

	}

	public void move() {
		position.x += velocity.x;
		position.y += velocity.y;
	}

	/**
	 * Will check for collision and check if ball has gone out of arena
	 * 
	 * @param ball
	 */
	public void update(Player player,CollisionGrid cg) {
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
		g.drawOval((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
		g.setColor(color);
		g.fillOval((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
	}

}
