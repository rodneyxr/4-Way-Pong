package multiplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import tools.Vector2D;

public class BallInjector {

	private final Random RAND = new Random();
	private final int ARC_ANGLE = 90;

	private int corner; // topleft(TL)=0,TR=1,BR=2,BL=3
	private Vector2D position;
	private Vector2D velocity;
	private int radius;
	private int startAngle;
	private Color color;

	// CollisionGrid
	private int[][] cells;

	final int WIDTH = 800; // temp values
	final int HEIGHT = 800; // temp values

	public BallInjector(int corner, CollisionGrid cg) {
		this.corner = corner;
		this.color = Color.GRAY;
		this.radius = 99;
		if (corner == 0) {
			position = new Vector2D(0, 0);
			velocity = new Vector2D(1, 1);
			this.startAngle = 270;
		} else if (corner == 1) {
			position = new Vector2D(WIDTH - 1, 0);
			velocity = new Vector2D(-1, 1);
			this.startAngle = 180;
		} else if (corner == 2) {
			position = new Vector2D(WIDTH - 1, HEIGHT - 1);
			velocity = new Vector2D(-1, -1);
			this.startAngle = 90;
		} else if (corner == 3) {
			position = new Vector2D(0, HEIGHT - 1);
			velocity = new Vector2D(1, -1);
			this.startAngle = 0;
		}
		cells = cg.update(this, position, radius * 2, radius * 2);
		cells = cg.update(this, position, radius * 2, radius * 2); // I don't know why but this needs to be called twice. glitch in the cg class
	}

	public int[][] getCells() {
		return cells;
	}

	/**
	 * This method will handle collisions between the ball injector and balls
	 * 
	 * @param b
	 */
	public void checkCollision(Ball b) { // TL=0,TR=1,BR=2,BL=3
		/* take into consideration that the balls are colliding as the are injected */
		// TODO: fix collision checking, right now it just deflects the ball if it enters the grid
		float distance = Vector2D.distance(position, b.getPos());
		if (distance <= radius + b.getRadius()) {
			float dx = Vector2D.distanceX(position, b.getPos());
			float dy = Vector2D.distanceY(position, b.getPos());
			double angle = Math.toDegrees(Math.atan2(dy, dx));
			//System.out.println(angle);

			// Vector2D v = new Vector2D(b.getVelocity());
			// v.x *= (float) Math.cos(angle);
			// v.y *= (float) Math.sin(angle);
			// v = v.scale(b.getSpeed());
			if (corner == 3) {//if (b.getVelocityX() < 0 && b.getVelocityY() > 0) {
				b.setVelocityX((float) Math.cos(angle) * b.getSpeed());
				b.setVelocityY((float) Math.sin(angle) * b.getSpeed());
				System.out.println(b.getVelocity());
			}
		}
	}

	public Ball deployBall(float speed, int radius, Color color) {
		double angle = RAND.nextDouble() * 70 + 10;
		if (corner != 0)
			angle += corner * 90; // rotate the angle accordingly

		Vector2D v = new Vector2D(velocity);
		v.x = (float) Math.cos(Math.toRadians(angle));
		v.y = (float) Math.sin(Math.toRadians(angle));
		v = v.scale(speed);
		return new Ball(new Vector2D(position), v, speed, radius, color);
	}

	public void update(CollisionGrid cg) {
		// Nothing may need to be called in here
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillArc((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2, startAngle, ARC_ANGLE);
		g.setColor(Color.RED);
		g.drawArc((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2, startAngle, ARC_ANGLE);
		g.drawArc((int) position.x - radius, (int) position.y - radius, radius * 2 + 1, radius * 2 + 1, startAngle, ARC_ANGLE);
	}

}
