package com.aziubin.proxx.blackholes;

public enum RndBoardFactory implements BoardFactory {
	INSTANCE;

	@Override
	public Board getBoard(Integer width, Integer height, Integer holesNumber) {
		var board = new Board(width, height, holesNumber, new ConsoleUiStrategy());
		board.generate();
		board.inspect();
		return board;
	}

}
