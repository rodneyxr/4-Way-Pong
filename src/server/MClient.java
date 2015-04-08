package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import singleplayer.SinglePlayer;

public class MClient {

	// client socket
	private static Socket clientSocket = null;
	private static PrintStream os = null;
	private static DataInputStream is = null;
	private static BufferedReader inputLine = null;

	private static ObjectOutputStream Oout = null;
	private static ObjectInputStream Oin = null;

	private static boolean closed = false;
	private static String paddle_color;
	private static String username;

	static int n = 0;

	public static void connect(String pc, String name) {
		int PORT = 46792;// 46792
		paddle_color = pc;
		username = name;
		String HOST = "localhost"; // IP to connect to

		/**
		 * Open a socket on a given host and port. Open input and output streams
		 */

		try {
			clientSocket = new Socket(HOST, PORT);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());

			Oout = new ObjectOutputStream(clientSocket.getOutputStream());
			Oin = new ObjectInputStream(clientSocket.getInputStream());

		} catch (UnknownHostException e) {
			System.err.println("Cant connect to host" + e);
		} catch (IOException e) {
			System.err.println("Cant get I/O for the connection to the host " + HOST);
		}

		/**
		 * 
		 */
		boolean running = false;
		if (clientSocket != null && os != null && is != null) {
			try {

				/* Create a thread to read from the server. */
				// new Thread(new MultiThreadClient()).start();
				System.out.println("Start while !closed loop");
				while (!closed) {
					os.println(username);
					os.println(paddle_color);
					if (running == false) {
						System.out.println("running false");
						SinglePlayer sp = new SinglePlayer();
						// sp.startGame("steven",Color.BLACK); // not sure if this is where we should start the loop
						running = true;
					}
					// System.out.println("end while closed");
				}

				/*
				 * Close the output stream, close the input stream, close the socket.
				 */
				os.close();
				is.close();
				clientSocket.close();
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}

		}

	}
	/*
	 * @SuppressWarnings("deprecation") public void run() { String line; int thread; try{ //while(!(line=is.readLine()).equals("-999")){
	 * 
	 * line = is.readLine(); System.out.println("Line = " + line); if(line.equals("STARTGAME")){ System.out.println("Starting game now..."); /*MultiPlayer mt = new MultiPlayer();
	 * mt.startGame(username, Color.BLUE); // not sure if this is where we should start the loop
	 */
	/*
	 * SinglePlayer sp = new SinglePlayer(); sp.startGame(username, Color.black); // not sure if this is where we should start the loop
	 * 
	 * }
	 * 
	 * }catch (IOException e){ e.printStackTrace(); } }
	 */
}
