/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

/**
 * Implementation of the board factory, which produces boards with uniformly randomly generated holes.
 * There is no requirement to have singleton here, but enum is a convenient and thread-safe way to 
 * instantiate factory.
 */
public enum RndBoardFactory implements BoardFactory {
	INST;

	@Override
	public Board getBoard(Integer width, Integer height, Integer holesNumber) {
		ByteArrayBoardImpl board = new ByteArrayBoardImpl(width, height, holesNumber, new ConsoleGameUiStrategy());
		//Board board = new Board(width, height, holesNumber, new ConsoleDebugUiStrategy());
		board.generate();
		board.inspectBoard();
		return board;
	}

}
