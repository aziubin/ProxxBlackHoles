/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import java.security.SecureRandom;

/**
 * Implementation using array of bytes underneath, which is extremely compact and fast way
 * to keep up to date information about game situation after each move. The content of each
 * cell is represented by the following numbers:
 * -1              was not opened yet and does not have neighbor holes; 
 *  0              does not have neighbor holes and is already opened and visible for user;
 * -2 -3 -4 ... -9 was not opened yet and do have neighbor holes; value is negative decremented number of holes;
 *  1  2  3 ...  8 has already bean opened and do have neighbor holes; value is the number of holes as is;
 * 127             is occupied by a hole and never visible for user.
 */
public class ByteArrayBoardImpl implements Board {
	public static byte HOLE_CELL = 127;
	public static int HOLE_REPOSITION_ATTAEMPTS_FACTOR = 33;

	private final int width;
	private final int heigth;

	/**
	 * Array of bytes is a compact and fast data structure allowing
	 * fast test cases and huge boards, like 1000 x 700 (with -Xss100m)
	 * processing data inplace in a single non-redundant data structure.
	 */
	private final byte[][] board;

	private final BoardUi ui;
	private final int holesCnt;
	private int remainingCellsToOpen;

	/**
	 * Implementation with the best possible complexity 1
	 * and suitable for real games. 
	 */
	@Override
	public int getRemainingCellsToOpen() {
		return remainingCellsToOpen;
	}
	
	/**
	 * Verifies if the cell is occupied by a hole.
	 */
	public static boolean isHole(byte cell) {
		// using defensive programming style to prevent assignment.
		return HOLE_CELL == cell;
	}

	/**
	 * Verifies if the cell is closed and user does not see its content.
	 */
	public static boolean isClosed(byte cell) {
		return 0 > cell;
	}

	public ByteArrayBoardImpl(int width, int height, int holesCnt, BoardUi uiStrategy) {
		this.width = width;
		this.heigth = height;
		this.ui = uiStrategy;
		board = new byte[heigth][width];
		this.holesCnt = holesCnt;
		
		int totalCells = width * height;
		remainingCellsToOpen = totalCells - holesCnt;
		
		if (holesCnt > totalCells) {
			throw new IllegalArgumentException(MsgFmtBundle.INST.format("NUMBER_OF_WHOLES")); 
		}
	}
	
	/**
	 * Helper method to check if passed horizontal
	 * and vertical coordinates are outside of the
	 * board and therefore does not have corresponding cell.  
	 */
	boolean outOfBounds(int x, int y) {
		return x < 0 || y < 0 || x > width - 1 || y > heigth - 1; 
	}

	/**
	 * Simple implementation of recursive search to inspect all cells
	 * adjacent	to the cell, which was chosen by user. Here the simplicity
	 * and the clarity of algorithm favors 
	 */
	private void openAdjacentCells(int x, int y) {
		if (outOfBounds(x, y) || HOLE_CELL == board[y][x] || 0 <= board[y][x]) {
			// condition of stop recursion including already opened cells.
			return;
		}

		remainingCellsToOpen--;
		byte cell = (byte) -board[y][x];
		cell -= 1;
		board[y][x] = cell;
		
		if (0 == cell) {
			// when found a cell, which does not have holes neighbors,
			// inspect each neighbor to identify other cells, which has to be
			// opened, propagating the open space for user.
			for (int i = x - 1; i <= x + 1; ++i) {
				for (int j = y - 1; j <= y + 1; ++j) {
					openAdjacentCells(i, j);
				}
			}
		}
	}

	/**
	 * Implementation, which uses recurrent cell inspection to open "chained" space on the board.
	 */
	@Override
	public int next(int x, int y) throws GameIsOverException {
		if (outOfBounds(x, y)) {
			throw new IllegalArgumentException(MsgFmtBundle.INST.format("BEYOND_EXPECTED_RANGE"));
		}
		byte cell = board[y][x];
		if (HOLE_CELL == cell) {
			throw new GameIsOverException(MsgFmtBundle.INST.format("THIS_CELL_IS_OCCUPIED"));
		} else if (cell < 0) {
			// Open this cell, so the number of holes is visible.
			openAdjacentCells(x, y);
		}
		return remainingCellsToOpen;
	}
	
	/**
	 * Generate holes randomly. It is possible that subsequent generated holes will overlap, so
	 * as a result, the total number of holes will be less than required if not addressed.
	 */
	@Override
	public void generate() {
		SecureRandom r = new SecureRandom();
		int i = 0;
		int repositionNum = 0;
		// Keep re-generating hole coordinates until new
		// hole does not overlap or iteration limit exceeded.
		while (i < holesCnt) {
			if (++repositionNum > holesCnt * HOLE_REPOSITION_ATTAEMPTS_FACTOR) {
				throw new IllegalArgumentException(MsgFmtBundle.INST.format("NOT_POSSIBLE_TO_FIND"));
			}
			int y = r.nextInt(heigth);
			int x = r.nextInt(width);
			if (HOLE_CELL != board[y][x]) {
				board[y][x] = HOLE_CELL; 
				++i;
			}
		}
	}
	
	private boolean isHole(int x, int y) {
		if (outOfBounds(x, y)) {
			return false;
		} else {
			return isHole(board[y][x]);
		}
	}

	/**
	 * Negative number indicates closed non-hole cell.
	 */
	public void inspectBoard() {
		for (int y = 0; y < heigth; ++y ) {
			for (int x = 0; x < width; ++x) {
				if (HOLE_CELL == board[y][x]) {
					continue;
				}
				
				// Iterate trough each cell in 3 x 3 square
				// to calculate the number of adjacent holes.
				byte holeNeighborsCnt = -1;
				for (int i = x - 1; i <= x + 1; ++i) {
					for (int j = y - 1; j <= y + 1; ++j) {
						if (isHole(i, j)) {
							--holeNeighborsCnt;
						}
					}
				}
				board[y][x] = holeNeighborsCnt;
			}
		}
	}

	/**
	 * This implementation inspects all cells one by one to verify
	 * the total number of opened cells with complexity o(n*n),
	 * but this is OK as it is only used for unit testing.
	 */
	@Override
	public int inspectSpaces() {
		int result = 0;
		for (byte[] line : board) {
			for (byte cell : line) {
				if (!isClosed(cell)) {
					++result;
				}
			}
		}
		return result;
	}

	/**
	 * This implementation inspects all cells one by one to verify
	 * the total number of holes with complexity o(n*n),
	 * but this is OK as it is only used for unit testing.
	 */
	@Override
	public int inspectHoles() {
		int result = 0;
		for (byte[] line : board) {
			for (byte cell : line) {
				if (isHole(cell)) {
					++result;
				}
			}
		}
		return result;
	}

	/**
	 * Textual console implementation of board representation.
	 */
	@Override
	public void ui() {
		ui.uiChar(' ');
		ui.uiChar(' ');
		for (int x = 0; x < board[0].length; ++x) {
			ui.uiChar((char) ('0' + (x % 10)));
		}
		ui.uiLine();

		int y = 0;
		for (byte[] line : board) {
			ui.uiChar((char) ('0' + (y++ % 10)));
			ui.uiChar(' ');
			for (byte cell : line) {
				ui.uiCell(cell);
			}
			ui.uiLine();
		}
	}

}
