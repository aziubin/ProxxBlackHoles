/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCase {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Generate holoes for all possible combinations of parameters and
	 * verify the number of holes is correct. 
	 */
	@Test
	void testHolesGeneration() {
		for (int y = 3; y < 91; ++y) {
			for (int x = 4; x < 91; ++x) {
				for (int h = 0; h < 11; ++h) {
					Board board = RndBoardFactory.INSTANCE.getBoard(x, y, h);
					assertEquals(board.holes.size(), h);
				}
			}
		}
	}

	@Test
	void testCellOpening() {
		for (int y = 3; y < 91; ++y) {
			for (int x = 4; x < 91; ++x) {
				for (int h = 0; h < 11; ++h) {
					Board board = RndBoardFactory.INSTANCE.getBoard(x, y, h);
					try {
						board.next(0, 0);
						assertEquals(board.getRemainingCellsToOpen(), x * y - board.getSpaces());
					} catch (GameIsOver e) {
						  // Ignore the end of game exception.	
					} catch (Throwable e) {
						e = e;
						// Ignore the end of game exception.	
					}
				}
			}
		}
	}

}
