/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class, which represents console game application.
 * Use command line to start it like below:
 * java -Xss10m -cp ProxxBlackHoles.jar com.aziubin.proxx.blackholes.game 300 133 3455
 * where 300 is horizontal dimensions of the board, 133 is vertical dimensions and 3455 is
 * the number of holes.
 * java -Xss10m -jar ProxxBlackHoles.jar 155 25 187
 */
public class Game {

	/**
	 * Main loop of the game where board is constructed,
	 * user input is collected and the result of the move is presented to user.
	 */
	public static void play(Integer width, Integer heigth, Integer holesNumber) {
		try {
			Board board = RndBoardFactory.INST.getBoard(width, heigth, holesNumber);
			try(Scanner scanner = new Scanner(System.in)) {
	            do {
	                try {
	                    board.ui();
	                    ConsoleUtils.printMessage("MOVE_STATUS", holesNumber, board.getRemainingCellsToOpen());

	                    int x = ConsoleUtils.getIntFromUi(scanner);
	                    int y = ConsoleUtils.getIntFromUi(scanner);
	                    
	                    int remainingCellsToOpen = board.next(x, y);
	                    if (0 == remainingCellsToOpen) {
	                    	ConsoleUtils.printMessage("GREETING");
	                        board.ui();
	                        break;
	                    }

	                } catch (IllegalArgumentException e) {
	                    System.out.println(e.getMessage());
	                } catch (GameIsOverException e) {
	                	System.out.println(e.getMessage());
	                	break;
	                } catch (IllegalStateException e) {
	                	System.out.println(e.getMessage());
	                	break;
	                } catch (NoSuchElementException e) {
	                	break;
	                } catch (Exception e) {
	                	System.out.println(e.getClass().getCanonicalName());
	                	ConsoleUtils.printMessage("ERROR", e.getMessage());
	                	break;
	                }
	            } while (true); 
	        }
		} catch (Exception e) {
        	System.out.println(e.getMessage());
        	return;
		} finally {
			ConsoleUtils.printMessage("SEEYOU");
		}
    }

    public static void main(String[] args) {
    	ConsoleUtils.printMessage("WELCOME");
        int width, height, holes;
        try {
            width = Integer.valueOf(args[0]);
            height = Integer.valueOf(args[1]);
            holes = Integer.valueOf(args[2]);
        } catch (Exception e) {
            width = 33;
            height = 10;
            holes = 10;
            ConsoleUtils.printMessage("INCORRECT_CMD", width, height, holes);
        }

        play(width, height, holes);
    }

}
