package multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BallInjectorController implements ActionListener {

	private MultPlayerModel model;
	private PlayerView view;

	public BallInjectorController(MultPlayerModel model, PlayerView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.deployBall(100);
	}

}
