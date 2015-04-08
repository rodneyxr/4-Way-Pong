package singleplayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * this class will handle all player input
 * @author Rodney
 *
 */
public class SinglePlayerController implements KeyListener {

	private SinglePlayerModel model;
	private boolean left; // bottom player
	private boolean right;
	private boolean left2; // top player
	private boolean right2;
	private boolean up; // left player
	private boolean down;
	private boolean up2; // right player
	private boolean down2;

	/**
	 * CONSTRUCTOR
	 * @param model
	 * @param view
	 */
	public SinglePlayerController(SinglePlayerModel model, SinglePlayerView view) {
		this.model = model;
	}

	/**
	 * handles the keys that are pressed
	 */
	@Override
	public void keyPressed(KeyEvent k) {
		int code = k.getKeyCode();

		// bottom player
		if (code == KeyEvent.VK_A && !left) {
			model.getPlayer().move(1); // move left
			left = true;
			return;
		} else if (code == KeyEvent.VK_D && !right) {
			model.getPlayer().move(2); // move right
			right = true;
			return;
		}

		// right player
		if (code == KeyEvent.VK_DOWN && !down) {
			model.getAI(0).movement(1); // move down
			down = true;
			return;
		} else if (code == KeyEvent.VK_UP && !up) {
			model.getAI(0).movement(2); // move up
			up = true;
			return;
		}

		// top player
		if (code == KeyEvent.VK_LEFT && !left2) {
			model.getAI(1).movement(1); // move left
			left2 = true;
			return;
		} else if (code == KeyEvent.VK_RIGHT && !right2) {
			model.getAI(1).movement(2); // move right
			right2 = true;
			return;
		}

		// left player
		if (code == KeyEvent.VK_S && !down2) {
			model.getAI(2).movement(1); // move left
			down2 = true;
			return;
		} else if (code == KeyEvent.VK_W && !up2) {
			model.getAI(2).movement(2); // move right
			up2 = true;
			return;
		}
	}

	/**
	 * handles released keys
	 */
	@Override
	public void keyReleased(KeyEvent k) {
		int code = k.getKeyCode();

		// top player
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
			if (code == KeyEvent.VK_A) {
				left = false;
			} else if (code == KeyEvent.VK_D) {
				right = false;
			}
			if (left && !right) {
				model.getPlayer().move(1);
			} else if (right && !left) {
				model.getPlayer().move(2);
			} else {
				model.getPlayer().move(0);
			}
			return;
		}

		// right player
		if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP) {
			if (code == KeyEvent.VK_DOWN) {
				down = false;
			} else if (code == KeyEvent.VK_UP) {
				up = false;
			}
			if (down && !up) {
				model.getAI(0).movement(1);
			} else if (up && !down) {
				model.getAI(0).movement(2);
			} else {
				model.getAI(0).movement(0);
			}
			return;
		}

		// bottom player
		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
			if (code == KeyEvent.VK_LEFT) {
				left2 = false;
			} else if (code == KeyEvent.VK_RIGHT) {
				right2 = false;
			}
			if (left2 && !right2) {
				model.getAI(1).movement(1);
			} else if (right2 && !left2) {
				model.getAI(1).movement(2);
			} else {
				model.getAI(1).movement(0);
			}
			return;
		}

		// left player
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_W) {
			if (code == KeyEvent.VK_S) {
				down2 = false;
			} else if (code == KeyEvent.VK_W) {
				up2 = false;
			}
			if (down2 && !up2) {
				model.getAI(2).movement(1);
			} else if (up2 && !down2) {
				model.getAI(2).movement(2);
			} else {
				model.getAI(2).movement(0);
			}
			return;
		}
	}

	/**
	 * not needed
	 */
	@Override
	public void keyTyped(KeyEvent k) {
		// Don't need this method
	}

}
