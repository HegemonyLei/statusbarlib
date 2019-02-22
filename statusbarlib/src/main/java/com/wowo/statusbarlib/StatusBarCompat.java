package com.wowo.statusbarlib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 设置系统状态栏颜色
 *
 * @author Lei.Jiang
 * @version [1.0.0]
 * @since [1.0.0]
 */

public class StatusBarCompat {

    static final IStatusBar IMPL;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IMPL = new StatusBarMImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isEMUI()) {
            IMPL = new StatusBarLollipopImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMPL = new StatusBarKitkatImpl();
        } else {
            IMPL = new IStatusBar() {
                @Override
                public void setStatusBarColor(Window window, @ColorInt int color) {
                }
            };
        }
    }

    private static boolean isEMUI() {
        File file = new File(Environment.getRootDirectory(), "build.prop");
        if (file.exists()) {
            Properties properties = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                properties.load(fis);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return properties.containsKey("ro.build.hw_emui_api_level");
        }
        return false;
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        boolean isLightColor = toGrey(color) > 225;
        setStatusBarColor(activity, color, isLightColor);
    }

    /**
     * 把颜色转换成灰度值。
     */
    public static int toGrey(@ColorInt int color) {
        int blue = Color.blue(color);
        int green = Color.green(color);
        int red = Color.red(color);
        return (red * 38 + green * 75 + blue * 15) >> 7;
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color, boolean lightStatusBar) {
        setStatusBarColor(activity.getWindow(), color, lightStatusBar);
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Window window, @ColorInt int color, boolean lightStatusBar) {
        if ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) > 0
                || StatusBarExclude.exclude) {
            return;
        }
        IMPL.setStatusBarColor(window, color);
        LightStatusBarCompat.setLightStatusBar(window, lightStatusBar);
    }

    public static void setFitsSystemWindows(Window window, boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            internalSetFitsSystemWindows(window, fitSystemWindows);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    static void internalSetFitsSystemWindows(Window window, boolean fitSystemWindows) {
        final ViewGroup contentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        final View childView = contentView.getChildAt(0);
        if (childView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            childView.setFitsSystemWindows(fitSystemWindows);
        }
    }

    public static void resetActionBarContainerTopMargin(Window window) {
        View contentView = window.findViewById(android.R.id.content);
        ViewGroup group = (ViewGroup) contentView.getParent();
        if (group.getChildCount() > 1) {
            View view = group.getChildAt(1);
            internalResetActionBarContainer(view);
        }
    }

    public static void resetActionBarContainerTopMargin(Window window, @IdRes int actionBarContainerId) {
        View view = window.findViewById(actionBarContainerId);
        internalResetActionBarContainer(view);
    }

    private static void internalResetActionBarContainer(View actionBarContainer) {
        if (actionBarContainer != null) {
            ViewGroup.LayoutParams params = actionBarContainer.getLayoutParams();
            if (params instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) params).topMargin = 0;
                actionBarContainer.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置状态栏
     */
    public static void setLightStatusBar(Window window, boolean isLightStatusBar) {
        LightStatusBarCompat.setLightStatusBar(window, isLightStatusBar);
    }

    /**
     * 设置状态栏透明
     */
    public static void setTranslucent(Window window, boolean translucent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (translucent) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                internalSetFitsSystemWindows(window, false);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }
}
