package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String gameTitle = "Battle Pong Version 0.2.1";
	private JPanel contentPane;
	private static Main mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("In main method\n\tClient now executing...");
					mainFrame = new Main();
					mainFrame.setVisible(true);
					mainFrame.setTitle(gameTitle);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws URISyntaxException
	 */
	public Main() throws URISyntaxException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBattlePongClient = new JLabel("Battle Pong Client");
		lblBattlePongClient.setFont(new Font("Dialog", Font.BOLD, 24));
		lblBattlePongClient.setBounds(94, 26, 342, 54);
		contentPane.add(lblBattlePongClient);

		JButton btnSinglePlayer = new JButton("Play Game");
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * 
				 * EXEC SINGLE PLAYER
				 * 
				 * 
				 */
				System.out.println("Player picked Single player from main client.");
				contentPane.setVisible(false);
				mainFrame.setVisible(false); // hide main frame
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							System.out.println("\tExecuting Singleplayer menu (Client.java)");
							Client frame = new Client(true);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		final URI howto = new URI("http://www.stevenpetroff.net/cloud/pong/index.html");

		btnSinglePlayer.setBounds(115, 105, 207, 25);
		contentPane.add(btnSinglePlayer);
		JButton btnMultiplayer = new JButton("How To Play");
		JLabel warning = new JLabel("Warning: This button will open a browser");
		btnMultiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Player picked How to play from main client");
				try {
					java.awt.Desktop.getDesktop().browse(howto);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnMultiplayer.setBounds(115, 142, 207, 25);
		warning.setBounds(80, 160, 300, 50);
		contentPane.add(warning);
		contentPane.add(btnMultiplayer);
		/*
		 * JButton btnMultiplayer = new JButton("Multiplayer"); btnMultiplayer.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
		 * System.out.println("Player picked Multiplayer from main client"); contentPane.setVisible(false); EventQueue.invokeLater(new Runnable() { public void run() { try {
		 * System.out.println("\tExecuting Multiplayer menu (Client.java)"); lient frame = new Client(false); frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } }); } });
		 * btnMultiplayer.setBounds(115, 142, 207, 25); contentPane.add(btnMultiplayer);
		 */
	}

}
