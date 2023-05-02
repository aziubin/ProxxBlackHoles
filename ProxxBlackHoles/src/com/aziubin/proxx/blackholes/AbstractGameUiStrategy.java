package com.aziubin.proxx.blackholes;

public abstract class AbstractGameUiStrategy implements UiStrategy {

	public abstract void hole();

	public abstract void closed();

	public void opened(byte cell) {
		if (0 == cell) {
			System.out.print(' ');
		} else {
			System.out.print(String.valueOf(cell));
		}
	}	

	@Override
	public void uiCell(byte cell) {
		if (Board.isHole(cell)) {
			hole();
		} else if(Board.isClosed(cell)) {
			closed();
		} else {
			opened(cell);
		}
	}

	@Override
	public void uiChar(char c) {
		System.out.print(c);
	}

	@Override
	public void uiLine() {
		System.out.println();
	}

}
