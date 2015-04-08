package tools;

/**
 * This class is a tool to assist mainly with the positions of the balls and players
 * 
 * @author Rodney
 * 
 */
public class Vector2D {

	public float x;
	public float y;

	public Vector2D() {
		x = 0;
		y = 0;
	}

	public Vector2D(Vector2D v) {
		x = v.x;
		y = v.y;
	}

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public static float distanceX(Vector2D v1, Vector2D v2) {
		return Math.abs(v1.x - v2.x);
	}

	public static float distanceY(Vector2D v1, Vector2D v2) {
		return Math.abs(v1.y - v2.y);
	}

	/**
	 * @param v1
	 * @param v2
	 * @return the distance between v1 and v2
	 */
	public static float distance(Vector2D v1, Vector2D v2) {
		float dx = distanceX(v1, v2);
		float dy = distanceY(v1, v2);
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public float distance(Vector2D v) {
		float dx = Math.abs(x - v.x);
		float dy = Math.abs(y - v.y);
		float d = (float) Math.sqrt(dx * dx + dy * dy);
		return d;
	}

	public void add(Vector2D v) {
		x += v.x;
		y += v.y;
	}

	public Vector2D scale(float n) {
		float newX = x * n;
		float newY = y * n;
		return (new Vector2D(newX, newY));
	}

	public Vector2D scale(Vector2D v) {
		float newX = x * v.x;
		float newY = y * v.y;
		return (new Vector2D(newX, newY));
	}

	public String toString() {
		return String.format("Vector2D: (%f, %f)", x, y);
	}

}
