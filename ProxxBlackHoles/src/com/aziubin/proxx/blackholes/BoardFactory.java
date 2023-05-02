/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

public interface BoardFactory {
	Board getBoard(Integer width, Integer height, Integer holesNumber);
}
