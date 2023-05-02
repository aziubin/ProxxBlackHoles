/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	private static final String THIS_CELL_IS_OCCUPIED_BY_A_HOLE = "This cell is occupied by a hole, The gamne is over.";
	private static final String THE_NUMBER_OF_WHOLES_IS_LARGER = "The number of wholes is larger than the number of cells on the board.";
	private static final String NOT_POSSIBLE_TO_FIND = "Not possible to find a place for a new hole in specified number of iterations.";
	public static byte HOLE_CELL = 127;

	private final int width;
	private final int heigth;
	private final byte[][]board;
	private final UiStrategy uiStrategy;
	private final int holesCnt;

	Set<List<Integer>> holes;

	private int remainingCellsToOpen;
	public int getRemainingCellsToOpen() {
		return remainingCellsToOpen;
	}
	
	public static boolean isHole(byte cell) {
		return HOLE_CELL == cell;
	}

	public static boolean isClosed(byte cell) {
		return 0 > cell;
	}

	public Board(int width, int height, int holesCnt, UiStrategy uiStrategy) {
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
		holes = new HashSet<>(holesCnt);
	}
	
	boolean outOfBounds(int x, int y) {
		return x < 0 || y < 0 || x > width - 1 || y > heigth - 1; 
	}
	
	/**
	 * Simple implementation of recursive search to inspect all cells adjacent to the
	 * cell, which was chosen by user. 
	 * @param x horizontal coordinate of chosen cell.
	 * @param y vertical coordinate of chosen cell.
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
			// opened for user.
			for (int i = x - 1; i <= x + 1; ++i) {
				for (int j = y - 1; j <= y + 1; ++j) {
					openAdjacentCells(i, j);
				}
			}
		}
	}
	
	public int next(int x, int y) throws GameIsOver { // todo move check here
		byte cell = board[y][x];
		if (HOLE_CELL == cell) {
			throw new GameIsOver(THIS_CELL_IS_OCCUPIED_BY_A_HOLE);
		} else if (cell < 0) {
			// Open this cell, so the number of holes is visible.
			openAdjacentCells(x, y);
		}
		return remainingCellsToOpen;
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
	public void inspect() {
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
	 * Generate holes randomly with o(n) algorithm complexity.
	 * It is possible that subsequent generated holes will overlap, so
	 * and as a result, the total number of holes will be less than required
	 * if not addressed.
	 * Use hash set to verify that new hole does not overlap with exiting. 
	 */
	public void generate() {
		SecureRandom r = new SecureRandom();
		
		for (int i = 0; i < holesCnt; ++i) {
			List<Integer> coordinates = new ArrayList<>(2);
			coordinates.add(null);
			coordinates.add(null);
			int repositionNum = 0;
			do {
				// Keep re-generating hole coordinates until new
				// hole does not overlap or iteration limit exceeded.
				if (++repositionNum > HOLE_CELL) {
					throw new IllegalArgumentException(NOT_POSSIBLE_TO_FIND);
				}
				coordinates.set(0, r.nextInt(heigth));
				coordinates.set(1, r.nextInt(width));
				// No need to use combined x and y coordinates object as Set has built-in support for lists, 
			} while (holes.contains(coordinates));

			holes.add(coordinates);
			board[coordinates.get(0)][coordinates.get(1)] = HOLE_CELL;
		}
	}
	
	/**
	 * Inspects the board cell by cell to calculate the number of opened cells including holes for test.
	 * @return the number of opened cells and holes.
	 */
	public int getSpaces() {
		int result = 0;
		for (byte[] line : board) {
			for (byte cell : line) {
//				if (!isHole(cell) && !isClosed(cell)) {
				if (!isClosed(cell)) {
					++result;
				}
			}
		}
		return result;
	}

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
