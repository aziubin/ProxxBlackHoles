package com.aziubin.proxx.blackholes;

public enum RndBoardFactory implements BoardFactory {
	INSTANCE;

	@Override
	public Board getBoard(Integer width, Integer height) {
		var board = new Board(width, height, 10, new ConsoleUiStrategy());
		board.generate();
		board.inspect();
		return board;
	}

}
