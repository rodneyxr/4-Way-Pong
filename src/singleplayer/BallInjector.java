package singleplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import tools.Vector2D;

/**
 * this class will handle the ball deployment into the game
 * 
 * @author Rodney
 * 
 */
public class BallInjector {

	private final Random RAND = new Random(); // to be used for random angles
	private final int ARC_ANGLE = 90; // angle of the injectors arcs
	private final int FORCE = 5; // the force that the ball should be reflected

	private int corner; // topleft(TL)=0,TR=1,BR=2,BL=3
	private Vector2D position; // the injectors position
	private Vector2D velocity; // the velocity each injector will apply to each ball
	private int radius; // the radius of the injector
	private int startAngle; // start angle of each injector's arc
	private Color color; // color of the injector

	// sound
	Sound injectorSound;

	private Vector2D repelForce; // the repel force each injector will use on the ball

	// CollisionGrid
	private int[][] cells; // the cells of the collision grid the injectors occupy

	final int WIDTH = 800; // width of the arena
	final int HEIGHT = 800; // height of the arena

	/**
	 * constructor
	 * 
	 * @param corner
	 * @param cg
	 */
	public BallInjector(int corner, CollisionGrid cg) {
		this.corner = corner;
		this.color = new Color(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat());
		this.color.darker(); // make the color dark
		this.radius = 99;
		injectorSound = new Sound("injectorhit.wav");
		if (corner == 0) {
			position = new Vector2D(0, 0);
			velocity = new Vector2D(1, 1);
			repelForce = new Vector2D(FORCE, FORCE);
			this.startAngle = 270;
		} else if (corner == 1) {
			position = new Vector2D(WIDTH - 1, 0);
			velocity = new Vector2D(-1, 1);
			repelForce = new Vector2D(-FORCE, FORCE);
			this.startAngle = 180;
		} else if (corner == 2) {
			position = new Vector2D(WIDTH - 1, HEIGHT - 1);
			velocity = new Vector2D(-1, -1);
			repelForce = new Vector2D(-FORCE, -FORCE);
			this.startAngle = 90;
		} else if (corner == 3) {
			position = new Vector2D(0, HEIGHT - 1);
			velocity = new Vector2D(1, -1);
			repelForce = new Vector2D(FORCE, -FORCE);
			this.startAngle = 0;
		}
		cells = cg.update(this, position, radius * 2, radius * 2);
		cells = cg.update(this, position, radius * 2, radius * 2); // I don't know why but this needs to be called twice. glitch in the cg class
	}

	/**
	 * gets the cells the injector currently occupies
	 * 
	 * @return
	 */
	public int[][] getCells() {
		return cells;
	}

	/**
	 * This method will handle collisions between the ball injector and balls
	 * 
	 * @param b
	 */
	public void checkCollision(Ball b) { // TL=0,TR=1,BR=2,BL=3
		float distance = Vector2D.distance(position, b.getPos());
		if (distance <= radius + b.getRadius()) {

			// get the distances between the x and y
			float dx = Vector2D.distanceX(position, b.getPos());
			float dy = Vector2D.distanceY(position, b.getPos());

			// calculate the rebound angle of the ball
			double angle = Math.atan(dy / dx);

			// get the old velocity of the ball
			Vector2D v2 = new Vector2D(b.getVelocity());

			// get the counter vector to repel ball
			Vector2D repel = new Vector2D();
			repel.x = (float) (repelForce.x * Math.cos(angle));
			repel.y = (float) (repelForce.y * Math.sin(angle));

			// calculate new velocity for the ball
			v2.x = (b.getVelocity().x * (b.getMass() - b.getMass()) + (2 * b.getMass() * repel.x)) / (b.getMass() + b.getMass());
			v2.y = (b.getVelocity().y * (b.getMass() - b.getMass()) + (2 * b.getMass() * repel.y)) / (b.getMass() + b.getMass());

			// set ball velocity
			b.setVelocityX(v2.x);
			b.setVelocityY(v2.y);

			// seperate the balls
			b.move();

			// change Injector color
			color = new Color(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat());
			color.darker(); // make the color dark
			injectorSound.play();
		}
	}

	/**
	 * deploys a ball into the game
	 * 
	 * @param speed
	 * @param mass
	 * @param radius
	 * @param color
	 * @param isPowerBall
	 * @return
	 */
	public Ball deployBall(float speed, float mass, int radius, Color color, boolean isSuperBall, boolean isPowerUp) {
		double angle = RAND.nextDouble() * 70 + 10;
		if (corner != 0)
			angle += corner * 90; // rotate the angle accordingly

		Vector2D v = new Vector2D(velocity);
		v.x = (float) Math.cos(Math.toRadians(angle));
		v.y = (float) Math.sin(Math.toRadians(angle));
		v = v.scale(speed);
		return new Ball(new Vector2D(position), v, speed, mass, radius, color, isSuperBall, isPowerUp);
	}

	/**
	 * draws the injector using arcs
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {

		g.setColor(color);
		g.fillArc((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2, startAngle, ARC_ANGLE);
		g.setColor(Color.WHITE);
		g.drawArc((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2, startAngle, ARC_ANGLE);
	}

}
