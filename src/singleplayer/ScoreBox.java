package singleplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class will handle the scores
 * 
 * @author Rodney
 * 
 */
public class ScoreBox {
	final static int STARTINGSCORE = 50;

	private int killed;

	/* player score labels */
	private static JLabel oneScore;
	private static JLabel lblLeftScore;
	private static JLabel lblTopScore;
	private static JLabel lblRightScore;
	private static JLabel timer;

	/* player difficulty toggle buttons */
	private static JButton btnLeftDifficulty, btnRightDifficulty, btnTopDifficulty;

	static int bottomScore, leftScore, topScore, rightScore;

	/* custom ball vars */
	static float ballSpeed = 0;
	static float ballMass = 0;
	static int ballSize = 0;
	static Color ballColor = Color.BLUE;
	static boolean ispowerBall = false;
	static String textInput = null;

	static int num;

	private SinglePlayerModel model;

	static boolean toggle = true;

	private static JTextField speedField;
	private static JTextField massField;
	private static JTextField sizeField;
	private static JTextField nField;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param model
	 */
	public ScoreBox(SinglePlayerModel model) {
		this.model = model;
		killed = 0;
	}

	/**
	 * 
	 * @param model
	 * @return scorepanel for the view
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JPanel scorePanel() {
		JPanel scores = new JPanel();
		scores.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridwidth = 4;
		c.gridheight = 14;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;

		/**
		 * SCORES
		 */
		JLabel lblNescoreswLabel = new JLabel("Scores");
		lblNescoreswLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		c.gridwidth = 4;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.CENTER;
		scores.add(lblNescoreswLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;

		/**
		 * PLAYER 1 (BOTTOM)
		 */
		JLabel lblPlayerOne = new JLabel(model.getPlayer().getName());
		lblPlayerOne.setForeground(model.getPlayer().getColor());
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		scores.add(lblPlayerOne, c);
		bottomScore = STARTINGSCORE;
		oneScore = new JLabel(String.valueOf(bottomScore));
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		scores.add(oneScore, c);

		/**
		 * AI 2 (Left)
		 */
		JLabel lblLeftPlayer = new JLabel("Left AI");
		lblLeftPlayer.setForeground(model.getAI(2).getColor());
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 2;
		scores.add(lblLeftPlayer, c);

		leftScore = STARTINGSCORE;
		lblLeftScore = new JLabel(String.valueOf(leftScore));
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 2;
		scores.add(lblLeftScore, c);

		btnLeftDifficulty = new JButton(model.getAI(2).getDifficulty());
		btnLeftDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getAI(2).toggleDifficulty();
				btnLeftDifficulty.setText(model.getAI(2).getDifficulty());
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 2;
		scores.add(btnLeftDifficulty, c);

		/**
		 * AI 1 / Player 2 (TOP)
		 */
		JLabel lblTopPlayer = new JLabel(model.getAI(1).getName());
		lblTopPlayer.setForeground(model.getAI(1).getColor());
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 3;
		scores.add(lblTopPlayer, c);

		topScore = STARTINGSCORE;
		lblTopScore = new JLabel(String.valueOf(topScore));
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 3;
		scores.add(lblTopScore, c);

		btnTopDifficulty = new JButton(model.getAI(1).getDifficulty());
		btnTopDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getAI(1).toggleDifficulty();
				btnTopDifficulty.setText(model.getAI(1).getDifficulty());
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 3;
		scores.add(btnTopDifficulty, c);

		/**
		 * AI 0 (RIGHT)
		 */
		JLabel lblPlayerFour = new JLabel("Right AI");
		lblPlayerFour.setForeground(model.getAI(0).getColor());
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 4;
		scores.add(lblPlayerFour, c);

		rightScore = STARTINGSCORE;
		lblRightScore = new JLabel(String.valueOf(rightScore));
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 4;
		scores.add(lblRightScore, c);

		btnRightDifficulty = new JButton(model.getAI(0).getDifficulty());
		btnRightDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getAI(0).toggleDifficulty();
				btnRightDifficulty.setText(model.getAI(0).getDifficulty());
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 4;
		scores.add(btnRightDifficulty, c);

		/**
		 * TIME
		 */
		timer = new JLabel("Elapsed Time: x seconds");
		c.gridwidth = 4;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 5;
		c.fill = GridBagConstraints.CENTER;
		scores.add(timer, c);

		/**
		 * DEVELOPER TOOLS
		 */
		JLabel lblDeveloperTools = new JLabel("Developer Tools");
		lblDeveloperTools.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		c.gridwidth = 4;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 6;
		scores.add(lblDeveloperTools, c);
		c.fill = GridBagConstraints.HORIZONTAL;

		// COLLISION GRID
		JButton btnEnableDeveloperMode = new JButton("Collision Grid");
		btnEnableDeveloperMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.toggleDevMode();
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 7;
		scores.add(btnEnableDeveloperMode, c);

		// PAUSE
		JButton btnNewButton_1 = new JButton("Pause");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (toggle == true) {
					model.setRunning(false);
					toggle = false;
				} else if (toggle == false) {
					model.setRunning(true);
					toggle = true;
				}

			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 7;
		scores.add(btnNewButton_1, c);

		// WHITE BALL
		JButton whiteBall = new JButton("Eject White Ball");
		whiteBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.ejectBall(3.0f, 10.0f, 20, Color.WHITE, false, false);
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 8;
		scores.add(whiteBall, c);

		// YELLOW BALL
		JButton yellowBall = new JButton("Eject Yellow Ball");
		yellowBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.ejectBall(3.0f, 5.0f, 10, Color.YELLOW, false, false);
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 8;
		scores.add(yellowBall, c);

		// PHOTON RAY
		JButton crazyBall = new JButton("Eject Photon Ray ");
		crazyBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int x = 0; x < 20; x++)
					model.ejectBall(4.0f, 200.0f, 2, Color.CYAN, false, false);

			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 9;
		scores.add(crazyBall, c);

		// GREEN BALL
		JButton greenBall = new JButton("Eject Green Ball");
		greenBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.ejectBall(7.0f, 10.0f, 13, Color.GREEN, false, false);
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 9;
		scores.add(greenBall, c);

		// POWER BALL
		JButton powerBall = new JButton("Eject Power Ball");
		powerBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.ejectBall(3.0f, 5.0f, 10, Color.RED, true, false);
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 10;
		scores.add(powerBall, c);

		// FRAME CAP
		JButton paddleT = new JButton("Frame Cap");
		paddleT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.toggleLockFPS();
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 10;
		scores.add(paddleT, c);

		/**
		 * CUSTOM BALLS
		 */
		JButton btnNewButton = new JButton("Eject N Custom Balls");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// get speed
				textInput = speedField.getText();
				try {
					ballSpeed = Float.parseFloat(textInput);
				} catch (NumberFormatException exception) {
					System.err.print("no string for speed field\n");
					ballSpeed = 3.0f;
				}

				// get mass
				textInput = massField.getText();
				try {
					ballMass = Float.parseFloat(textInput);
				} catch (NumberFormatException exception) {
					System.err.print("no string for mass field\n");
					ballMass = 5.0f;
				}

				// get size
				textInput = sizeField.getText();
				try {
					ballSize = Integer.parseInt(textInput);
				} catch (NumberFormatException exception) {
					System.err.print("no string for size field\n");
					ballSize = 10;
				}

				textInput = nField.getText();
				try {
					num = Integer.parseInt(textInput);
				} catch (NumberFormatException exception) {
					System.err.print("no string for N field\n");
					num = 1;
				}

				System.out.println("ejecting custom ball");
				System.out.println("Speed set to " + ballSpeed);
				System.out.println("Mass set to " + ballMass);
				System.out.println("Size set to " + ballSize);
				System.out.println("isPowerball set to " + ispowerBall);

				// get color

				for (int i = 0; i < num; i++)
					model.ejectBall(ballSpeed, ballMass, ballSize, ballColor, ispowerBall, false);
			}
		});
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 11;
		scores.add(btnNewButton, c);

		JLabel lblHowMany = new JLabel("N: ");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 11;
		scores.add(lblHowMany, c);

		nField = new JTextField();
		nField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 3;
		c.gridy = 11;
		scores.add(nField, c);
		nField.setColumns(10);

		JLabel speed = new JLabel("Speed");
		c.weightx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 12;
		scores.add(speed, c);

		JLabel mass = new JLabel("Mass");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 12;
		scores.add(mass, c);

		JLabel size = new JLabel("Size");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 12;
		scores.add(size, c);

		JLabel color = new JLabel("Color");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 3;
		c.gridy = 12;
		scores.add(color, c);

		// SPEED FIELD
		speedField = new JTextField();
		speedField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		speedField.setColumns(10);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 13;
		scores.add(speedField, c);

		// MASS FIELD
		massField = new JTextField();
		massField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		massField.setColumns(10);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 13;
		scores.add(massField, c);

		// SIZE FIELD
		sizeField = new JTextField();
		sizeField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 13;
		scores.add(sizeField, c);
		sizeField.setColumns(10);

		String[] colorbox = { "Blue", "Red", "Green", "Black", "Orange", "Cyan", "Pink" };

		final JComboBox comboBox = new JComboBox(colorbox); // this is here becuase my mac doesnt recognize the <String> on combobox <- lol
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** USER SELECTED THE COLOR X **/
				if (comboBox.getSelectedItem().toString() == "Blue") {
					ballColor = Color.BLUE;
					System.out.println("Player picked color Blue");
				}
				if (comboBox.getSelectedItem().toString() == "Red") {
					ballColor = Color.RED;
					System.out.println("Player picked color Red");
				}
				if (comboBox.getSelectedItem().toString() == "Green") {
					ballColor = Color.GREEN;
					System.out.println("Player picked color Green");
				}
				if (comboBox.getSelectedItem().toString() == "Black") {
					ballColor = Color.BLACK;
					System.out.println("Player picked color Black");
				}
				if (comboBox.getSelectedItem().toString() == "Orange") {
					ballColor = Color.ORANGE;
					System.out.println("Player picked color Orange");
				}
				if (comboBox.getSelectedItem().toString() == "Cyan") {
					ballColor = Color.CYAN;
					System.out.println("Player picked color Cyan");
				}
				if (comboBox.getSelectedItem().toString() == "Pink") {
					ballColor = Color.PINK;
					System.out.println("Player picked color Pink");
				}

			}
		});
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 3;
		c.gridy = 13;
		scores.add(comboBox, c);

		// CHECKBOX
		final JCheckBox chckbxPowerBall = new JCheckBox("Power Ball?");
		chckbxPowerBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/**
				 * IS POWERBALL?
				 */
				ispowerBall = chckbxPowerBall.isSelected();
				System.out.println("isPowerball set to " + ispowerBall);
			}
		});
		c.gridwidth = 4;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 14;
		c.fill = GridBagConstraints.CENTER;
		scores.add(chckbxPowerBall, c);

		return scores;

	}

	void updateScore(int n, boolean superBall) {
		int subtract = 1;
		if (superBall)
			subtract = 3;
		if (n == 2) { // bottom
			if (bottomScore > 1) {
				oneScore.setText(String.valueOf(bottomScore -= subtract));
			} else {
				oneScore.setText(String.valueOf(0));
				model.killPlayer();
				killed++;
			}
		}
		if (n == 3) { // left
			if (leftScore > 1) {
				lblLeftScore.setText(String.valueOf(leftScore -= subtract));
			} else {
				lblLeftScore.setText(String.valueOf(0));
				model.killAI(2);
				killed++;
			}
		}
		if (n == 0) { // top
			if (topScore > 1) {
				lblTopScore.setText(String.valueOf(topScore -= subtract));
			} else {
				lblTopScore.setText(String.valueOf(0));
				model.killAI(1);
				killed++;
			}
		}
		if (n == 1) { // right
			if (rightScore > 1) {
				lblRightScore.setText(String.valueOf(rightScore -= subtract));
			} else {
				lblRightScore.setText(String.valueOf(0));
				model.killAI(0);
				killed++;
			}
		}

		if (killed >= 3)
			model.setGameOver(true);
	}

	/**
	 * updates the elapsed time in the dev panel
	 * 
	 * @param n
	 * @param model
	 */
	void updateTime(double n) {
		if (model.running() == true)
			timer.setText("Elapsed Time: " + String.valueOf((int) n) + " seconds");
	}

}
