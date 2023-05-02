package com.aziubin.proxx.blackholes;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	private static final String THE_NUMBER_OF_WHOLES_IS_LARGER = "The number of wholes is larger than the number of cells on the board.";
	private static final String NOT_POSSIBLE_TO_FIND = "Not possible to find a place for a new hole in specified number of iterations.";
	private static byte HOLE_CELL = 127;

	private final int width;
	private final int heigth;
	private final byte[][]board;
	private final UiStrategy uiStrategy;
	private final int holesCnt;

	private int remainingCellsToOpen;
	Set<List<Integer>> holes;

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
			for (int i = x - 1; i <= x + 1; ++i) {
				for (int j = y - 1; j <= y + 1; ++j) {
					openAdjacentCells(i, j);
//				if (i != x && j != y) {
//				}
				}
			}
		}
		
	}
	
	public int next(int x, int y) throws GameIsOver { // todo move check here
		byte cell = board[y][x];
		if (HOLE_CELL == cell) {
			throw new GameIsOver("This palce is occupied by a hole, The gamne is over.");
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

	
	public void generate() {
		var r = new SecureRandom();
		
		for (int i = 0; i < holesCnt; ++i) {
			List<Integer> coordinates = new ArrayList<>(2);
			coordinates.add(null);
			coordinates.add(null);
			int repositionNum = 0;
			do {
				if (++repositionNum > HOLE_CELL) {
					throw new IllegalArgumentException(NOT_POSSIBLE_TO_FIND);
				}
				coordinates.set(0, r.nextInt(heigth));
				coordinates.set(1, r.nextInt(width));
			} while (holes.contains(coordinates));

			holes.add(coordinates);
			board[coordinates.get(0)][coordinates.get(1)] = HOLE_CELL;
		}
	}
	
	public int spaces() {
		int result = 0;
		for (byte[] line : board) {
			for (byte cell : line) {
				if (0 != cell) {
					++result;
				}
			}
		}
		return result;
	}

	public void ui() {
		int x = 0;
		uiStrategy.uiCell(" ");
		uiStrategy.uiCell(" ");
		for (byte cell : board[0]) {
			uiStrategy.uiCell(String.valueOf(x++ % 10));
		}
		uiStrategy.uiLine(heigth);

		int y = 0;
		for (byte[] line : board) {
			uiStrategy.uiCell(String.valueOf((y++)));
			uiStrategy.uiCell(" ");
			for (byte cell : line) {
				if (HOLE_CELL == cell) {
					uiStrategy.uiCell("!");
				} else if (cell < 0) {
					uiStrategy.uiCell(".");
				} else if (0 == cell) {
					// There are no adjacent holes, indicate space.
					uiStrategy.uiCell(" ");
				} else {
					uiStrategy.uiCell(String.valueOf(cell));
				}
			}
			uiStrategy.uiLine(heigth);
		}
	}

}
