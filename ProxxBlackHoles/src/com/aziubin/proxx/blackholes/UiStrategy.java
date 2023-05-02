package com.aziubin.proxx.blackholes;

public interface UiStrategy {
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
