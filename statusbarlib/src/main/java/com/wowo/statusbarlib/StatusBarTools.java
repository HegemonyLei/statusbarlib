package com.wowo.statusbarlib;

import android.content.Context;
import android.content.res.Resources;

/**
 * 工具类
 *
 * @author Lei.Jiang
 * @version [1.0.0]
 * @since [1.0.0]
 */
public class StatusBarTools {

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
