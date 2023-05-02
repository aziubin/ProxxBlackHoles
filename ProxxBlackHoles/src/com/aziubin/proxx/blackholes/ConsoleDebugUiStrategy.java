package com.aziubin.proxx.blackholes;

/**
 * @author aziub
 * Implementation of the {@link #UiStrategy} interface suitable for developers when holes are visible.
 */
public class ConsoleDebugUiStrategy extends AbstractGameUiStrategy implements UiStrategy {

	@Override
	public void hole() {
		System.out.print('*');
	}

	@Override
	public void closed() {
		System.out.print('.');
	}

}
