package singleplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
public class SinglePlayerModel {

	/* constants */
	private static final Random RAND = new Random(); // this random is used to determine which injector will shoot the next ball
	private static final int STARTING_LIVES = 50;
	private static final float DEFAULT_SPEED = 10.0f;
	private static final int DEFAULT_PADDLE_LENGTH = 99;
	private static final int DEFAULT_DIFFICULTY = 1;

	/* booleans */
	private boolean running; // tells if the game is running (switch to false for pause)
	private boolean gameOver; // ends the game when this is true
	private boolean devMode; // enables developer mode when this is true
	private boolean lockFPS; // locks fps at 60 when true, unlimited when false

	/* objects */
	private Arena arena; // the Arena of the game
	private AI[] ai; // 3 AI's to play against the player
	private Player player; // the player
	private BallInjector[] injector; // the 4 injectors to deploy the balls
	private LinkedList<Ball> ball; // linked list of balls for easy removal
	private ListIterator<Ball> it; // iterator for the balls
	private CollisionGrid cg; // the collision grid stores all objects to check for collisions
	private ScoreBox sb; // the scoreBox for the game

	/* elapsed time and timers */
	private static long tStart; // starting time
	private static double elapsedTime; // elapsed time
	private Timer updateTimer; // timer to control the tick of the game
	private Timer injectorTimer; // timer to control when the next ball will be deployed

	/* sound */
	private BackGroundMusic bgm;
	private Sound countDownSound;

	/**
	 * Constructor for the model
	 * 
	 * @param name
	 * @param color
	 */
	public SinglePlayerModel(String name, Color color) {
		running = false;
		gameOver = false;
		devMode = false;
		lockFPS = false;
		tStart = 0;
		elapsedTime = 0;

		bgm = new BackGroundMusic("Rodney's Song");
		countDownSound = new Sound("countdown.wav");

		cg = new CollisionGrid(this, Tools.getWidth(), Tools.getHeight(), 50);

		ball = new LinkedList<Ball>();
		it = ball.listIterator();

		// Initiate Arena
		arena = new Arena();

		// Initiate Injectors
		injector = new BallInjector[4];
		for (int i = 0; i < 4; i++)
			injector[i] = new BallInjector(i, cg);

		// Initiate AI
		ai = new AI[3];
		ai[0] = new AI(0, "R", new Color(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat()).darker().darker(), DEFAULT_PADDLE_LENGTH, DEFAULT_SPEED, STARTING_LIVES, DEFAULT_DIFFICULTY);
		ai[1] = new AI(1, "Robinson", Color.BLACK, DEFAULT_PADDLE_LENGTH, DEFAULT_SPEED, STARTING_LIVES, DEFAULT_DIFFICULTY);
		ai[2] = new AI(2, "L", new Color(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat()).darker().darker(), DEFAULT_PADDLE_LENGTH, DEFAULT_SPEED, STARTING_LIVES, DEFAULT_DIFFICULTY);

		// Initiate Player
		if (name.length() < 1)
			name = "Player";
		player = new Player(name, color, DEFAULT_PADDLE_LENGTH, STARTING_LIVES);
		System.out.println("Name set to: " + name); // DEBUG
		System.out.println("Color set to: " + color.toString()); // DEBUG

		sb = new ScoreBox(this);
	}

	public ScoreBox getScoreBox() {
		return sb;
	}

	/**
	 * Calculate the Elapsed Time
	 * 
	 * @return
	 */
	public static double getElapsedTime() {
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		elapsedTime = tDelta / 1000.0;
		return elapsedTime;
	}

	/**
	 * toggles between 60fps and unlimited
	 */
	public void toggleLockFPS() {
		lockFPS = !lockFPS;
	}

	/**
	 * 
	 * @return if the fps should be locked to 60
	 */
	public boolean lockFPS() {
		return lockFPS;
	}

	/**
	 * toggles developer mode
	 */
	public void toggleDevMode() {
		if (devMode)
			devMode = false;
		else
			devMode = true;
	}
	
	/**
	 * kills the player
	 */
	public void killPlayer() {
		player.killPlayer();
	}

	/**
	 * kills the ai
	 * @param i
	 */
	public void killAI(int i) {
		getAI(i).killAI();
	}

	/**
	 * 
	 * @return developer mode
	 */
	public boolean isDevMode() {
		return devMode;
	}

	/**
	 * sets the starting time to be used for calculating elapsed time
	 * 
	 * @param tStart
	 */
	public static void setStartTime(long time) {
		tStart = time;
	}

	/**
	 * 
	 * @return the start time
	 */
	public long getStartTime() {
		return tStart;
	}

	/**
	 * sets the injector Timer
	 * 
	 * @param injectorTimer
	 */
	public void setInjectorTimer(Timer injectorTimer) {
		injectorTimer.setDelay(7000);
		this.injectorTimer = injectorTimer;
	}

	/**
	 * sets the countdown timer
	 * 
	 * @param countDown
	 */
	public void setCountDown(int countDown) {
		arena.setCountDown(countDown);
	}

	/**
	 * plays the count down sound
	 */
	public void playCountDownSound() {
		countDownSound.play();
	}

	/**
	 * starts the background music
	 */
	public void startBackGroundMusic() {
		bgm.start();
	}

	/**
	 * sets the update timer
	 * 
	 * @param updateTimer
	 */
	public void setUpdateTimer(Timer updateTimer) {
		this.updateTimer = updateTimer;
	}

	/**
	 * to check if the game is running or stopped
	 * 
	 * @return isRunning
	 */
	public boolean running() {
		return running;
	}

	/**
	 * this can start and pause the game
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		if (running) { // start
			updateTimer.start();
			injectorTimer.start();
		} else { // pause
			updateTimer.stop();
			injectorTimer.stop();
		}
		this.running = running;
	}

	/**
	 * when this is called and is false, the game should be ended
	 * 
	 * @return gameOver
	 */
	public boolean gameOver() {
		return gameOver;
	}

	/**
	 * stop the game and end it
	 * 
	 * @param gameOver
	 */
	public void setGameOver(boolean gameOver) {
		setRunning(false);
		this.gameOver = gameOver;
	}

	/**
	 * update the player
	 */
	public void updatePlayer() {
		player.update(cg, elapsedTime);
	}

	/**
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * update all the AI
	 */
	public void updateAI() {
		for (AI bot : ai)
			bot.update(cg, elapsedTime);
	}

	/**
	 * update all the balls
	 */
	public void updateBalls() {
		int n;
		it = ball.listIterator();
		while (it.hasNext()) {
			Ball b = it.next();
			b.update(player, ai, cg);
			if (b.getNeedsDeletion()) {
				b.removeFromGrid(cg);
				it.remove();
				continue;
			}
			if ((n = b.wasScored()) != -1) { // if ball passed a players goal
				sb.updateScore(n, b.getIsSuperBall());
				b.removeFromGrid(cg);
				it.remove(); // delete ball from play forever! muahaha >:)
			}
		}
	}

	/**
	 * draw all the balls
	 * 
	 * @param g
	 */
	public void drawBalls(Graphics g) {
		for (Ball b : ball)
			b.draw(g);
	}

	/**
	 * draw the player
	 * 
	 * @param g
	 */
	public void drawPlayer(Graphics g) {
		player.draw(g);
	}

	/**
	 * draw all the AI
	 * 
	 * @param g
	 */
	public void drawAI(Graphics g) {
		for (AI a : ai)
			a.draw(g);
	}

	/**
	 * draw the Arena
	 * 
	 * @param g
	 */
	public void drawArena(Graphics g) {
		arena.draw(g);
	}

	/**
	 * draw the injectors
	 * 
	 * @param g
	 */
	public void drawInjectors(Graphics g) {
		for (BallInjector x : injector)
			x.draw(g);
	}

	/**
	 * clears the entire collision grid
	 */
	public void clearGrid() {
		cg.clear();
	}

	/**
	 * manually eject a ball at any time
	 * 
	 * @param speed
	 * @param mass
	 * @param size
	 * @param color
	 * @param isPowerBall
	 */
	public void ejectBall(float speed, float mass, int size, Color color, boolean isSuperBall, boolean isPowerUp) {
		int anchor = RAND.nextInt(4);
		ball.add(injector[anchor].deployBall(speed, mass, size, color, isSuperBall, isPowerUp));
	}

	/**
	 * calculates a random ball and injector to deploy a ball into the game
	 * 
	 * @param newDelay
	 */
	public void deployBall(int newDelay, float speed) {
		int i = RAND.nextInt(4); // which injector to come out of?
		int chance = RAND.nextInt(100) + 1; // probability implementation
		injectorTimer.setDelay(newDelay); // set new delay for next ball

		if (chance >= 98) // 3% chance
			ball.add(injector[i].deployBall(speed, 5.0f, 10, Color.PINK, false, true)); // inject power up
		else if (chance >= 96) // 2% chance
			ball.add(injector[i].deployBall(speed, 5.0f, 10, Color.RED, true, false)); // inject power powerBall
		else if (chance >= 90) // 6% chance
			ball.add(injector[i].deployBall(speed, 10.0f, 20, Color.WHITE, false, false)); // inject big ball
		else if (chance >= 86) // 4% chance
			ball.add(injector[i].deployBall(10.0f, 10.0f, 13, Color.GREEN, false, false)); // inject fast ball
		else
			ball.add(injector[i].deployBall(speed, 5.0f, 10, Color.YELLOW, false, false)); // inject normal ball
	}

	/**
	 * draw the collision grid
	 * 
	 * @param g
	 */
	public void drawCollisionGrid(Graphics g) {
		if (devMode)
			cg.draw(g);
	}

	/**
	 * get an AI
	 * 
	 * @return
	 */
	public AI getAI(int i) {
		// 0 : left
		// 1 : top
		// 2 : right
		return ai[i];
	}
}
