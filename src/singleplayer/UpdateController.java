package singleplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class functions as the game's Tick
 * 
 * @author Rodney
 * 
 */
public class UpdateController implements ActionListener {

	private SinglePlayerModel model;

	/**
	 * 
	 * @param model
	 * @param view
	 */
	public UpdateController(SinglePlayerModel model) {
		this.model = model;
	}

	/**
	 * updates everything in the game very tick
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		model.updateBalls();
		model.updateAI();
		model.updatePlayer();
	}

}
