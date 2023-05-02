package com.aziubin.proxx.blackholes;

import java.util.Scanner;

/**
 * @author aziub
 * Start this program like
 * java.exe -cp . com.aziubin.proxx.blackholes.game
 *
 */
public class game {
	private static final String UNEXPECTED_INPUT = "unexpected input";
	private static final String BLACK_HOLE_CONSOLE_GAME = "Black hole console game.";
	
	private static void welcome() {
		System.out.println("Welcome to " + BLACK_HOLE_CONSOLE_GAME);
	}

	private static void error(String mesage) {
		System.out.println("Error: " + mesage);
	}
	
	private static void seeyou() {
		System.out.println("Thank you for using " + BLACK_HOLE_CONSOLE_GAME); //TODO resource
	}
	
	private static Integer uiInt(Scanner scanner) {
		System.out.print(">");
		if (!scanner.hasNextInt()) {
			error(UNEXPECTED_INPUT);
			scanner.next();
			return null;
		}
		return scanner.nextInt();
	}

	public static void play(Integer width, Integer heigth) {
		Board board = RndBoardFactory.INSTANCE.getBoard(width, heigth);
		try(Scanner scanner = new Scanner(System.in)) {
			do {
				try {
					board.ui();
					System.out.println("Type zero - based x and y and press enter, for example 5 7");

					Integer x = uiInt(scanner);
					if (x < 0 || x > width + 1) {
						throw new Exception("x is not in expected range");
					}
					Integer y = uiInt(scanner);
					if (y < 0 || y > heigth + 1) {
						throw new Exception("y is not in expected range");
					}
					
					board.next(x, y);
				} catch (Exception e) {
					error(e.getMessage());
				}
			} while (true);  // it is OK 
		}
		//seeyou();
	}

	public static void main(String[] args) {
		welcome();
		int w;
		int h;
		
		try {
			w = Integer.valueOf(args[0]);
			h = Integer.valueOf(args[1]);
		} catch (Exception e) {
			w = 10;
			h = 10;
			error("incorrect command line parameters, using default board width and height.");
		}

		play(w, h);
	}

}
