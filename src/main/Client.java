package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/* Java libs */
import singleplayer.SinglePlayer;

/**
 * * This is the client class. This will create the client that pops up when the user starts the program. MULTI_PLAYER go to next menu:
 * 
 * @NICKNAME: user will enter their nickname
 * @COLOR: user will enter the color they want (what if 2 people pick the same color?)
 * 
 *         BUTTON{CREATESERVER}: If the client chooses to create the server, it will call the BHserverclass and magic will happen. BUTTON{JOINGAME}: If the client chooses to join a game, it will
 *         connect to the server somehow and then call the Multiplayer class
 * 
 *         It will be a GUI interface using JFrame and other things to make this client show up.
 * @author Steven
 * 
 */
public class Client extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/* CLIENT INFORMATION */
	private static final long serialVersionUID = 1L;
	private static String GAME = "Battle Pong"; // name of the game
	private static String GAME_VERSION = "0.0.1";
	private static final String IP_ADDRESS = "localhost"; // might need this
	private static String CONNECT_IP_ADDRESS;
	private static final int PORT = 46792; // might need this on campus 867

	/* PLAYER INFORMATION */
	private boolean isSinglePlayer = false;
	private String playerName = "Player"; // user name of player
	private Color playerColor = Color.BLUE; // color player chooses
	private boolean isHost = false; // used to see if the person is server hostor not

	private int player; // which player are we? ( this may need to be in another func )

	/* CONNECTION INFO */
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;

	public Client(final boolean isSingleplayer) {
		super("Battle Pong");
		this.isSinglePlayer = isSingleplayer; // Single player
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO: maybe go back to preview window instead of exiting?
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBattlePongMultiplayer;
		if (isSingleplayer)
			lblBattlePongMultiplayer = new JLabel("Battle Pong Singleplayer Menu");
		else
			lblBattlePongMultiplayer = new JLabel("Battle Pong Multiplayer Menu");
		lblBattlePongMultiplayer.setFont(new Font("Dialog", Font.BOLD, 20));
		lblBattlePongMultiplayer.setBounds(39, 27, 381, 43);
		contentPane.add(lblBattlePongMultiplayer);

		textField = new JTextField();
		textField.setBounds(208, 88, 145, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblEnterYourUsername = new JLabel("Enter your Username:");
		lblEnterYourUsername.setBounds(35, 90, 164, 15);
		contentPane.add(lblEnterYourUsername);

		JLabel lblPickYourColor = new JLabel("Pick your color:");
		lblPickYourColor.setBounds(63, 126, 136, 15);
		contentPane.add(lblPickYourColor);

		String[] colorbox = { "Blue", "Red", "Green", "Black", "Orange", "Cyan", "Pink" };
		// final JComboBox<String> comboBox = new JComboBox<String>(colorbox);

		final JComboBox comboBox = new JComboBox(colorbox); // this is here becuase my mac doesnt recognize the <String> on combobox
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** USER SELECTED THE COLOR X **/
				if (comboBox.getSelectedItem().toString() == "Blue") {
					playerColor = Color.BLUE;
					System.out.println("Player picked color Blue");
				}
				if (comboBox.getSelectedItem().toString() == "Red") {
					playerColor = Color.RED;
					System.out.println("Player picked color Red");
				}
				if (comboBox.getSelectedItem().toString() == "Green") {
					playerColor = Color.GREEN;
					System.out.println("Player picked color Green");
				}
				if (comboBox.getSelectedItem().toString() == "Black") {
					playerColor = Color.BLACK;
					System.out.println("Player picked color Black");
				}
				if (comboBox.getSelectedItem().toString() == "Orange") {
					playerColor = Color.ORANGE;
					System.out.println("Player picked color Orange");
				}
				if (comboBox.getSelectedItem().toString() == "Cyan") {
					playerColor = Color.CYAN;
					System.out.println("Player picked color Cyan");
				}
				if (comboBox.getSelectedItem().toString() == "Pink") {
					playerColor = Color.PINK;
					System.out.println("Player picked color Pink");
				}

			}
		});
		comboBox.setBounds(208, 121, 145, 24);
		contentPane.add(comboBox);

		JButton btnStartGame = new JButton("Start Game!");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerName = textField.getText(); // set username here
				System.out.println("User has selected Start Game");
				if (isSingleplayer) {
					setVisible(false); // hide customization window
					System.out.println("\tSingle Player now executing (SinglePlayer.java)");
					SinglePlayer sp = new SinglePlayer();
					sp.startGame(playerName, playerColor);
				} else {
					// join host
					// MClient.connect(playerColor.toString(), playerName);
				}
			}
		});
		btnStartGame.setBounds(275, 204, 145, 25);
		contentPane.add(btnStartGame);
		/**
		 * 
		 * CREATE THE SERVER
		 * 
		 */
		if (!isSingleplayer) {
			JButton btnCreateServer = new JButton("Create Server");
			btnCreateServer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					System.out.println("User has selected Create Server");
					// MultiThreadServer.create(PORT);
				}
			});
			btnCreateServer.setBounds(39, 204, 145, 25);
			contentPane.add(btnCreateServer);
			/**
			 * 
			 * END CREATE SERVER
			 * 
			 */
		}
	}

}// end Client main

