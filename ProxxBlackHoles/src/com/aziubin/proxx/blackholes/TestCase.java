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

	@Test
	void testHoles() {
		for (int y = 3; y < 91; ++y) {
			for (int x = 4; x < 91; ++x) {
				for (int h = 0; h < 11; ++h) {
					Board board = RndBoardFactory.INSTANCE.getBoard(x, y, h);
					assertEquals(board.holes.size(), h);
				}
			}
		}
	}

}
