/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

/**
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
