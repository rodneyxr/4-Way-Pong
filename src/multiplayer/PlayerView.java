package multiplayer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PlayerView extends JFrame {

	private static final long serialVersionUID = 1L;
	private MultPlayerModel model;
	private GameScreen gameScreen;

	public PlayerView(MultPlayerModel model) {
		super("Singleplayer");
		this.model = model;
		gameScreen = new GameScreen(model, this);
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
		JButton button1 = new JButton("GameInfo"); // replace with gameInfo panel
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 0;
		add(button1, c);

		JButton button2 = new JButton("ChatBox"); // no chatbox for singleplayer...?
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 1;
		add(button2, c);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void drawGameScreen() {
		gameScreen.repaint();
	}

	public void registerListeners(MultiPlayerController controller) {
		gameScreen.addKeyListener(controller);
	}

}
