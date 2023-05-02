package com.aziubin.proxx.blackholes;

public enum RndBoardFactory implements BoardFactory {
	INSTANCE;

	@Override
	public Board getBoard(Integer width, Integer height, Integer holesNumber) {
		Board board = new Board(width, height, holesNumber, new ConsoleGameUiStrategy());
		//Board board = new Board(width, height, holesNumber, new ConsoleDebugUiStrategy());
		board.generate();
		board.inspect();
		return board;
	}

}
