package multiplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.Timer;

import tools.Tools;

/**
 * This class will hold all of the game's data
 * 
 * @author Rodney
 * 
 */
public class MultPlayerModel {

	private static final Random RAND = new Random();

	private boolean running;
	private boolean gameOver;

	private Arena arena;
	private Player player;
	private BallInjector[] injector;
	private LinkedList<Ball> ball;
	private ListIterator<Ball> it;
	private CollisionGrid cg;

	private Timer updateTimer;
	private Timer injectorTimer;

	/* Constructor */
	public MultPlayerModel(String name, Color color) {
		running = false;
		gameOver = false;

		cg = new CollisionGrid(this, Tools.getWidth(), Tools.getHeight(), 50);

		ball = new LinkedList<Ball>();
		it = ball.listIterator();

		// Initiate Arena
		arena = new Arena();

		// Initiate Injectors
		injector = new BallInjector[4];
		for (int i = 0; i < 4; i++)
			injector[i] = new BallInjector(i, cg);

		// Initiate other players

		// Initiate Player
		if (name.length() < 1)
			name = "Player";
		player = new Player(name, color);
		System.out.println("Name set to: " + name); // DEBUG
		System.out.println("Color set to: " + color.toString()); // DEBUG
	}

	public void setInjectorTimer(Timer injectorTimer) {
		injectorTimer.setDelay(1000);
		this.injectorTimer = injectorTimer;
	}

	public void setCountDown(int countDown) {
		arena.setCountDown(countDown);
	}

	public void setUpdateTimer(Timer updateTimer) {
		this.updateTimer = updateTimer;
	}

	/* return isRunning to check if the game is running or not */
	public boolean running() {
		return running;
	}

	/* Pause or resume the game */
	public void setRunning(boolean running) {
		if (running) { // start
			updateTimer.start();
			injectorTimer.start();
			System.out.println("start loop");
		} else { // pause
			updateTimer.stop();
			injectorTimer.stop();
			System.out.println("stop loop");
		}
		this.running = running;
	}

	public boolean gameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		setRunning(false);
		this.gameOver = gameOver;
	}

	public void updatePlayer() {
		player.update(cg);
	}

	public Player getPlayer() {
		return player;
	}

	public void updateArena() {
		arena.update();
	}
	
	public void updateInjector() {
		for (BallInjector i : injector)
			i.update(cg);
	}

	public void updateBalls() {
		it = ball.listIterator();
		while (it.hasNext()) {
			Ball b = it.next();
			b.update(player, cg);
			if (b.wasScored() != -1) { // if ball passed a players goal
				// System.out.println("removing ball on side: " + b.wasScored()); // DEBUG
				b.removeFromGrid(cg);
				it.remove(); // delete ball from play forever! muahaha >:)
				// System.out.println("[REMOVE]: # balls: " + ball.size()); // DEBUG
			}
		}
	}

	public void drawBalls(Graphics g) {
		for (Ball b : ball)
			b.draw(g);
	}

	public void drawPlayer(Graphics g) {
		player.draw(g);
	}

	public void drawArena(Graphics g) {
		arena.draw(g);
	}

	public void drawInjectors(Graphics g) {
		for (BallInjector x : injector)
			x.draw(g);
	}

	public void clearGrid() {
		cg.clear();
	}

	public void deployBall(int newDelay) {
		// get a random injector to deploy the ball
		int i = 1;//RAND.nextInt(4);
		float speed = 3.0f;
		injectorTimer.setDelay(newDelay); // set new delay for next ball (5s) for now...
		ball.add(injector[i].deployBall(speed, 20, Color.YELLOW));
		// System.out.println("[DEPLOY]: # balls: " + ball.size()); // DEBUG
		// System.out.println("ball deployed from Injector(" + i + ")"); // DEBUG
		// injectorTimer.stop(); // DEBUG
	}

	public void drawCollisionGrid(Graphics g) {
		cg.draw(g);
	}

}
