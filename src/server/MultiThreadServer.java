package server;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.Random;

public class MultiThreadServer {
	
	/*game stuff*/
	static final int maxClientsCount =1;
	private static final clientThread[] thread = new clientThread[maxClientsCount];
	static int startGame =0;
	/*server stuff*/
	private static Socket clientSocket;
	static ServerSocket serverSocket;
	int PORT;//867 on cslab
	

	public static void create(int p){
		int PORT = p;
		int CPlayers=0;
		  try {
			  System.out.println("Attempting to create server at port: " +PORT);
		      serverSocket = new ServerSocket(PORT);
		      System.out.println("Server Created!");
		    } catch (IOException e) {
		      System.out.println(e);
		    }
		  /*
		     * Create a client socket for each connection and pass it to a new client
		     * thread.
		     */
		  int i=0;
		    while (CPlayers != maxClientsCount) {
		    	try {
		    		CPlayers++;
		    		System.out.println("Attempting to accept socket from client "+CPlayers);
		    		clientSocket = serverSocket.accept();
		    		System.out.println("Connection established for player "+CPlayers);
		    		(thread[i++] = new clientThread(clientSocket, thread)).start();
		    		if(CPlayers == maxClientsCount){
		    			startGame = 1;
		    		}
		    	} catch (IOException e) {
		        System.out.println(e);
		      }
		    }
		  }
		  
		  
	}
	
class clientThread extends Thread{
	
	ObjectOutputStream Oout = null;
	ObjectInputStream Oin = null;
	
	private DataInputStream in = null;
	private PrintStream os = null;
	private DataOutputStream ds = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount =2;
	static String USERNAME;
	static String paddle_color;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket=clientSocket;
		this.threads=threads;
		maxClientsCount= threads.length;
	}
	
	
	@SuppressWarnings("deprecation")
	public void run(){
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;
		int i;
		try {
			/*
			 * Create input and output streams for this client.
			 */
			/**
			 * Object streams
			 */
			
			Oout = new ObjectOutputStream(clientSocket.getOutputStream());
			Oin = new ObjectInputStream(clientSocket.getInputStream());
			
			//in = new DataInputStream(clientSocket.getInputStream());
			ds = new DataOutputStream(clientSocket.getOutputStream());
			os = new PrintStream(clientSocket.getOutputStream());
			BufferedReader br= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	      
			USERNAME = br.readLine();
			//os.println("You have connected to the Server with the name "+USERNAME);
			paddle_color = br.readLine();
			//os.println("You have chosen the paddle color "+paddle_color);
		
			if(threads.length ==maxClientsCount){
				System.out.print("starting game");
				os.println("STARTGAME");
			}
			/*
			for (i = 0; i < maxClientsCount; i++) {
		    	  	if (threads[i] != null && threads[i] != this) {
		    	  		
		    	  	}	
			}
			*/
		} catch (IOException e) {
		}
	}
}
