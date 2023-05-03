/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

/**
 * Implementation of the interface suitable for users.
 */
public class ConsoleGameUi extends AbstractGameUi implements BoardUi {

    @Override
    public void hole() {
        System.out.print(".");
    }

    @Override
    public void closed() {
        System.out.print(".");
    }

}
