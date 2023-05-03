/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

/**
 * Helper interface to decouple game logic from nuances of
 * board representation on the user interface, so it can be easy
 * for example to run the game in debug mode, when holes are visible. 
 */
public interface BoardUi {
	/**
	 * Presents a cell of game board to user.
	 * @param cell the cell, which should be presented.
	 */
	public void uiCell(byte cell);

	/**
	 * Presents an indication of the next row to user.
	 */
	public void uiLine();

	/**
	 * Presents printable character passed in as parameter.
	 * @param c character to present.
	 */
	public void uiChar(char c);

}
