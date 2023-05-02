/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

public enum RndBoardFactory implements BoardFactory {
	INSTANCE;

	@Override
	public Board getBoard(Integer width, Integer height, Integer holesNumber) {
		Board board = new Board(width, height, holesNumber, new ConsoleGameUiStrategy());
		//Board board = new Board(width, height, holesNumber, new ConsoleDebugUiStrategy());
		board.generate();
		board.inspectBoard();
		return board;
	}

}
