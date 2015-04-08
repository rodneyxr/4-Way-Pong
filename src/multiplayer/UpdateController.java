package multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class functions as the game's Tick
 * 
 * @author Rodney
 * 
 */
public class UpdateController implements ActionListener {

	private MultPlayerModel model;
	private PlayerView view;

	public UpdateController(MultPlayerModel model, PlayerView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.updateArena();
		model.updateBalls();
		model.updateInjector();
		model.updatePlayer();
	}

}
