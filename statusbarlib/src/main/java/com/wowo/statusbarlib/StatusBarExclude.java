package com.wowo.statusbarlib;

import android.os.Build;

/**
 * 适配魅族
 *
 * @author Lei.Jiang
 * @version [1.0.0]
 * @since [1.0.0]
 */
public class StatusBarExclude {

    static boolean exclude = false;

    public static void excludeIncompatibleFlyMe() {
        try {
            Build.class.getMethod("hasSmartBar");
        } catch (NoSuchMethodException e) {
            exclude |= Build.BRAND.contains("Meizu");
        }
    }
}
