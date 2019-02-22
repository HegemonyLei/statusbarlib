package com.wowo.statusbarlib;

import android.view.Window;

/**
 * 状态栏接口
 *
 * @author Lei.Jiang
 * @version [1.0.0]
 * @since [1.0.0]
 */

public interface IStatusBar {
    /**
     * Set the status bar color
     *
     * @param window The window to set the status bar color
     * @param color  Color value
     */
    void setStatusBarColor(Window window, int color);
}
