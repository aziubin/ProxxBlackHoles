/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BlackHolesBoardTestCase {

	private static final int MIN_HOLES = 0;
	private static final int MIN_WIDTH = 4;
	private static final int MIN_HEIGTH = 3;
	private static final int MAX_HOLES = 11;
	private static final int MAX_WIDTH = 91;
	private static final int MAX_HEIGTH = 91;

	/**
	 * Generate holes for all possible combinations of parameters and
	 * verify the number of holes is correct. 
	 */
	@Test
	void testHolesGeneration() {
		for (int y = MIN_HEIGTH; y < MAX_HEIGTH; ++y) {
			for (int x = MIN_WIDTH; x < MAX_WIDTH; ++x) {
				for (int h = MIN_HOLES; h < MAX_HOLES; ++h) {
					Board board = RndBoardFactory.INST.getBoard(x, y, h);
					assertEquals(board.inspectHoles(), h);
				}
			}
		}
	}

	/**
	 * Open a cell on boards with different combinations of parameters,
	 * and verify that the number of opened cells is correctly maintained. 
	 */
	@Test
	void testCellOpening() {
		for (int y = MIN_HEIGTH; y < MAX_HEIGTH; ++y) {
			for (int x = MIN_WIDTH; x < MAX_WIDTH; ++x) {
				for (int h = MIN_HOLES; h < MAX_HOLES; ++h) {
					Board board = RndBoardFactory.INST.getBoard(x, y, h);
					try {
						board.next(0, 0);
						assertEquals(board.getRemainingCellsToOpen(), x * y - board.inspectSpaces());
					} catch (GameIsOverException e) {
						  // Ignore the end of game exception.	
					}
				}
			}
		}
	}

	/**
	 * Opening any cell on an empty board should open all remaining cells. 
	 */
	@Test
	void testEmptyBoard() {
		for (int y = MIN_HEIGTH; y < MAX_HEIGTH; ++y) {
			for (int x = MIN_WIDTH; x < MAX_WIDTH; ++x) {
				Board board = RndBoardFactory.INST.getBoard(x, y, 0);
				try {
					board.next(0, 0);
				} catch (GameIsOverException e) {
					  // Ignore the end of game exception when the chosen cell had a hole.	
				}
				assertEquals(0, board.getRemainingCellsToOpen());
			}
		}
	}

}
