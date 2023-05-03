/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

public abstract class AbstractGameUi implements BoardUi {

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
		if (ByteArrayBoardImpl.isHole(cell)) {
			hole();
		} else if(ByteArrayBoardImpl.isClosed(cell)) {
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
