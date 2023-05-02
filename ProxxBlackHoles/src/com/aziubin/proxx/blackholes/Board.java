package com.aziubin.proxx.blackholes;

import java.security.SecureRandom;

public class Board {
	final int width;
	final int heigth;
	protected final int[][]board;
	final UiStrategy uiStrategy;

	public Board(int width, int height, UiStrategy uiStrategy) {
		super();
		this.width = width;
		this.heigth = height;
		this.uiStrategy = uiStrategy;
		board = new int[width][heigth];
	}
	
	public void generate() {
		var r = new SecureRandom();
		for (int[] line : board) {
			for (int i = 0; i < line.length; ++i) {
				line[i] = r.nextInt(2);
			}
		}
	}

	public void inspect() {
		for (int y = 0; y < width; ++y ) {
			for (int x = 0; x < heigth; ++x) {
				
			}
		}
	}
	
	public int holes() {
		int result = 0;
		for (int[] line : board) {
			for (int cell : line) {
				if (1 == cell) {
					++result;
				}
			}
		}
		return result;
	}

	public void ui() {
		for (int[] line : board) {
			for (int cell : line) {
				uiStrategy.uiCell(cell);
			}
			uiStrategy.uiLine(heigth);
		}
	}

	public void next(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
