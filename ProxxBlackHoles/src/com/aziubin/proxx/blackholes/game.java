/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import java.util.Scanner;

/**
 * Start this application like below:
 * java.exe -Xss10m -cp ProxxBlackHoles.jar com.aziubin.proxx.blackholes.game 300 133 3455
 */
public class game {
	private static final String UNEXPECTED_INPUT = "unexpected input";
	private static final String BLACK_HOLE_CONSOLE_GAME = "the Black Hole console game.";
	
	private static void welcome() {
		System.out.println("Welcome to " + BLACK_HOLE_CONSOLE_GAME);
	}

	private static void error(String mesage) {
		System.out.println("Error: " + mesage);
	}
	
	private static void seeyou() {
		System.out.println("Thank you for using " + BLACK_HOLE_CONSOLE_GAME); //TODO resource
	}

	private static void greetings() {
		System.out.println("You have won. All cells are opened."); //TODO resource
	}
	
	private static Integer uiInt(Scanner scanner) {
		if (!scanner.hasNextInt()) {
			error(UNEXPECTED_INPUT);
			scanner.next();
			return null;
		}
		return scanner.nextInt();
	}

	public static void play(Integer width, Integer heigth, Integer holesNumber) {
		Board board = RndBoardFactory.INSTANCE.getBoard(width, heigth, holesNumber);
		try(Scanner scanner = new Scanner(System.in)) {
			do {
				try {
					board.ui();
					System.out.println("Number of holes: " + holesNumber + ". Remaining cells to find: " + board.getRemainingCellsToOpen());
					System.out.print("Type zero-based x and y and press enter, for example: 5 7\n>");

					Integer x = uiInt(scanner);
					if (x < 0 || x > width + 1) {
						throw new Exception("x is not in expected range");
					}
					Integer y = uiInt(scanner);
					if (y < 0 || y > heigth + 1) {
						throw new Exception("y is not in expected range");
					}
					
					int remainingCellsToOpen = board.next(x, y);
					if (0 == remainingCellsToOpen) {
						greetings();
						board.ui();
						break;
					}
//tpdp : ctrl-c
				} catch (GameIsOver e) {
					System.out.println(e.getMessage());
					break;
				} catch (Exception e) {
					error(e.getMessage());
				}
			} while (true);  // it is OK 
		}
		seeyou();
	}

	public static void main(String[] args) {
		welcome();
		int w, h, k;
		try {
			w = Integer.valueOf(args[0]);
			h = Integer.valueOf(args[1]);
			k = Integer.valueOf(args[2]);
		} catch (Exception e) {
			w = 33;
			h = 10;
			k = 10;
			error("incorrect command line parameters, using default board width and height.");
		}

		play(w, h, k);
	}

}
