package multiplayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MultiPlayerController implements KeyListener {

	private MultPlayerModel model;
	private PlayerView view;

	private boolean left;
	private boolean right;

	public MultiPlayerController(MultPlayerModel model, PlayerView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		int code = k.getKeyCode();
		if (code == KeyEvent.VK_A && !left) {
			model.getPlayer().move(1); // move left
			left = true;
		} else if (code == KeyEvent.VK_D && !right) {
			model.getPlayer().move(2); // move right
			right = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		int code = k.getKeyCode();
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
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
	}

}
