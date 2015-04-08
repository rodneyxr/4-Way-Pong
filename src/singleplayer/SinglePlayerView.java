package singleplayer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * this class handles the gui for the game
 * 
 * @author Rodney
 * 
 */
public class SinglePlayerView extends JFrame {

	private static final long serialVersionUID = 1L;
	private SinglePlayerModel model;
	private GameScreen gameScreen;
	private JPanel scorebox;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param model
	 */
	public SinglePlayerView(SinglePlayerModel model) {
		super("Singleplayer");
		this.model = model;
		gameScreen = new GameScreen(model);
		scorebox = model.getScoreBox().scorePanel();

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy = 0;

		add(gameScreen, c);

		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 0;

		add(scorebox, c);
	}

	/**
	 * the gamescreen that displays the gameplay
	 * @return
	 */
	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * draws the gamescreen
	 */
	public void drawGameScreen() {
		gameScreen.repaint();
	}

	/**
	 * register the keylistener for player input
	 * @param controller
	 */
	public void registerListeners(SinglePlayerController controller) {
		gameScreen.addKeyListener(controller);
	}

}
