/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

public interface BoardFactory {
	/**
	 * Construct a board with specified width, height and holes number.
	 * @param width horizontal dimensions of the board
	 * @param height vertical dimensions of the board
	 * @param holesNumber the number of holes which constructed board should have.
	 * @return
	 */
	Board getBoard(Integer width, Integer height, Integer holesNumber);
}
