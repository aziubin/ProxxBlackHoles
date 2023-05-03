/*Use of this source code is governed by an MIT-style
license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.*/
package com.aziubin.proxx.blackholes;

import java.util.ResourceBundle;
import java.text.MessageFormat;

/**
 * Helper utility class to simplify formatting of the text messages stored in the bundle.
 * There is no requirement to have singleton here, but enum is a convenient and thread-safe way to 
 * instantiate helper class.
 */
public enum MsgFmtBundle {
    INST;
    private ResourceBundle resourceBundle;

    MsgFmtBundle() {
        resourceBundle = ResourceBundle.getBundle("com.aziubin.proxx.blackholes.MessageFormatBundle");
    }

    public String format(String bundleKey, Object... args) {
        MessageFormat fmt = new MessageFormat(resourceBundle.getString(bundleKey));
        return fmt.format(args);
    }

}
