/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import java.security.SecureRandom;

public class ByteArrayBoardImpl implements Board {
	private static final String THIS_CELL_IS_OCCUPIED_BY_A_HOLE = "This cell is occupied by a hole, The gamne is over.";
	private static final String THE_NUMBER_OF_WHOLES_IS_LARGER = "The number of wholes is larger than the number of cells on the board.";
	private static final String NOT_POSSIBLE_TO_FIND = "Not possible to find a place for a new hole in specified number of iterations.";
	public static byte HOLE_CELL = 127;
	public static int HOLE_REPOSITION_ATTAEMPTS_FACTOR = 33;

	private final int width;
	private final int heigth;

	/**
	 * Array of bytes is compact and fast data structure allowing
	 * fast test cases and huge boards, like 1000 x 700 with -Xss100m
	 * processing data inplace in a single array.
	 */
	private final byte[][] board;

	private final UiStrategy uiStrategy;
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
	
	public static boolean isHole(byte cell) {
		// using defensive programming style to prevent assignment.
		return HOLE_CELL == cell;
	}

	public static boolean isClosed(byte cell) {
		return 0 > cell;
	}

	public ByteArrayBoardImpl(int width, int height, int holesCnt, UiStrategy uiStrategy) {
		super();
		this.width = width;
		this.heigth = height;
		this.uiStrategy = uiStrategy;
		board = new byte[heigth][width];
		this.holesCnt = holesCnt;
		
		int totalCells = width * height;
		remainingCellsToOpen = totalCells - holesCnt;
		
		if (holesCnt > totalCells) {
			throw new IllegalArgumentException(THE_NUMBER_OF_WHOLES_IS_LARGER); 
		}
	}
	
	boolean outOfBounds(int x, int y) {
		return x < 0 || y < 0 || x > width - 1 || y > heigth - 1; 
	}

	/**
	 * Simple implementation of recursive search to inspect all cells
	 * adjacent	to the cell, which was chosen by user. 
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
	 * Use recurrent cell inspection to open space on the board.
	 */
	@Override
	public int next(int x, int y) throws GameIsOverException { // todo move check here
		byte cell = board[y][x];
		if (HOLE_CELL == cell) {
			throw new GameIsOverException(THIS_CELL_IS_OCCUPIED_BY_A_HOLE);
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
				throw new IllegalArgumentException(NOT_POSSIBLE_TO_FIND);
			}
			int y = r.nextInt(heigth);
			int x = r.nextInt(width);
			if (HOLE_CELL != board[y][x]) {
				board[y][x] = HOLE_CELL; 
				++i;
			}
		}
	}
	
	boolean isHoleAdjacent(int x, int y) {
		if (outOfBounds(x, y)) {
			return false;
		} else {
			return HOLE_CELL == board[y][x];
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
				byte cnt = -1;
				if (isHoleAdjacent(x - 1, y - 1)) --cnt;
				if (isHoleAdjacent(x - 1, y    )) --cnt;
				if (isHoleAdjacent(x - 1, y + 1)) --cnt;
				if (isHoleAdjacent(x    , y - 1)) --cnt;
				if (isHoleAdjacent(x    , y + 1)) --cnt;
				if (isHoleAdjacent(x + 1, y - 1)) --cnt;
				if (isHoleAdjacent(x + 1, y    )) --cnt;
				if (isHoleAdjacent(x + 1, y + 1)) --cnt;
				board[y][x] = cnt;
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

	@Override
	public void ui() {
		uiStrategy.uiChar(' ');
		uiStrategy.uiChar(' ');
		for (int x = 0; x < board[0].length; ++x) {
			uiStrategy.uiChar((char) ('0' + (x % 10)));
		}
		uiStrategy.uiLine();

		int y = 0;
		for (byte[] line : board) {
			uiStrategy.uiChar((char) ('0' + (y++ % 10)));
			uiStrategy.uiChar(' ');
			for (byte cell : line) {
				uiStrategy.uiCell(cell);
			}
			uiStrategy.uiLine();
		}
	}

}
