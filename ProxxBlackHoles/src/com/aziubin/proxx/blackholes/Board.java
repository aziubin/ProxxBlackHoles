package com.aziubin.proxx.blackholes;

public interface Board {

	/**
	 * Calculates the state of the board opening the specified cell and propagating opened space
	 * through the neighbors without adjacent holes.
	 * @param x the horizontal coordinate of the cell to open.
	 * @param y the vertical coordinate of the cell to open.
	 * @return the number of cells, which are not opened yet. Zero means that the game is finished successfully. 
	 * @throws GameIsOverException when selected cell contains a hole.
	 */
	int next(int x, int y) throws GameIsOverException;

	/**
	 * Generate holes and place them on the board.
	 */
	void generate();

	/**
	 * Prepare user interface representation of the board, which can be for example console or Web. 
	 */
	void ui();

	/**
	 * @return the number of cells, which are not opened on the board yet.
	 * This number is decreased on each move and when zero, the game is finished successfully. 
	 */
	int getRemainingCellsToOpen();

	/**
	 * Inspects the board to calculate the number of opened cells including holes for unit testing.
	 * @return the number of opened cells and holes.
	 */
	int inspectSpaces();

	/**
	 * Inspects the board to calculate the number of holes for unit testing.
	 * @return the number of holes on the board.
	 */
	int inspectHoles();

}
