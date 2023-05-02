package com.aziubin.proxx.blackholes;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	private static final String THE_NUMBER_OF_WHOLES_IS_LARGER = "The number of wholes is larger than the number of cells on the board.";
	private static final String NOT_POSSIBLE_TO_FIND = "Not possible to find a place for a new hole in specified number of iterations.";

	private final int width;
	private final int heigth;
	private final byte[][]board;
	private final UiStrategy uiStrategy;

	private int holesCnt;
	Set<List<Integer>> holes;

	public Board(int width, int height, int holesCnt, UiStrategy uiStrategy) {
		super();
		this.width = width;
		this.heigth = height;
		this.uiStrategy = uiStrategy;
		board = new byte[heigth][width];
		this.holesCnt = holesCnt;
		if (holesCnt > width * height) {
			throw new IllegalArgumentException(THE_NUMBER_OF_WHOLES_IS_LARGER); 
		}
		holes = new HashSet<>(holesCnt);
	}

	public void generate() {
		var r = new SecureRandom();
		
		for (int i = 0; i < holesCnt; ++i) {
			List<Integer> coordinates = new ArrayList<>(2);
			coordinates.add(null);
			coordinates.add(null);
			int repositionNum = 0;
			do {
				if (++repositionNum > 100) {
					throw new IllegalArgumentException(NOT_POSSIBLE_TO_FIND);
				}
				coordinates.set(0, r.nextInt(heigth));
				coordinates.set(1, r.nextInt(width));
			} while (holes.contains(coordinates));

			holes.add(coordinates);
			board[coordinates.get(0)][coordinates.get(1)] = 100;
		}
	}
	
	boolean isAdjacent(int x, int y) {
		if (x < 0 || y < 0 || x > width - 1 || y > heigth - 1) {
			return false;
		} else {
			return 100 == board[y][x];
		}
	}

	public void inspect() {
		for (int y = 0; y < heigth; ++y ) {
			for (int x = 0; x < width; ++x) {
				if (100 == board[y][x]) {
					continue;
				}
				byte cnt = 0;
				if (isAdjacent(x - 1, y - 1)) ++cnt;
				if (isAdjacent(x - 1, y    )) ++cnt;
				if (isAdjacent(x - 1, y + 1)) ++cnt;
				if (isAdjacent(x    , y - 1)) ++cnt;
				if (isAdjacent(x    , y + 1)) ++cnt;
				if (isAdjacent(x + 1, y - 1)) ++cnt;
				if (isAdjacent(x + 1, y    )) ++cnt;
				if (isAdjacent(x + 1, y + 1)) ++cnt;
				board[y][x] = cnt;
			}
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
		for (byte[] line : board) {
			for (byte cell : line) {
				if (100 == cell) {
					uiStrategy.uiCell(".");
				} else {
					uiStrategy.uiCell(String.valueOf(cell));
				}
			}
			uiStrategy.uiLine(heigth);
		}
	}

	public void next(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
