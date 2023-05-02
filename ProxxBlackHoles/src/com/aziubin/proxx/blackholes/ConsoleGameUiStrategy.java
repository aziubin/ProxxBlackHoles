package com.aziubin.proxx.blackholes;

/**
 * @author aziub
 * Implementation of the {@link #UiStrategy} interface suitable for users.
 */
public class ConsoleGameUiStrategy extends AbstractGameUiStrategy implements UiStrategy {

	@Override
	public void hole() {
		System.out.print(".");
	}

	@Override
	public void closed() {
		System.out.print(".");
	}

}
