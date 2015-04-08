package tools;

import java.util.Scanner;

/**
 * Consists of small helper functions. useful for retrieving input from scanner
 * 
 * @author Rodney
 * 
 */
public class Tools {
	private static final int GAME_SCREEN_WIDTH = 800;
	private static final int GAME_SCREEN_HEIGHT = 800;
	private static final Scanner INPUT = new Scanner(System.in);

	public static String getString(String prompt) {
		System.out.print(prompt);
		return INPUT.nextLine();
	}
	
	public static int getInt(String prompt) {
		System.out.print(prompt);
		return INPUT.nextInt();
	}
	
	public static void m(String message) { // "m" for message
		System.out.println(message);
	}
	
	public static int getWidth() {
		return GAME_SCREEN_WIDTH;
	}
	
	public static int getHeight() {
		return GAME_SCREEN_HEIGHT;
	}

}
