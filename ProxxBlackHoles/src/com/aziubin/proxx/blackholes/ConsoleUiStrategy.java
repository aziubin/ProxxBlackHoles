package com.aziubin.proxx.blackholes;

public class ConsoleUiStrategy implements UiStrategy {

	@Override
	public void uiCell(int cell) {
		System.out.print(cell);
	}

	@Override
	public void uiLine(int size) {
		System.out.println();
	}

}
