/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */
package com.wowo.statusbarlib;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Lei.Jiang
 * @version [1.0.0]
 * @since [1.0.0]
 */
public class StatusBarView extends View {

    private int mStatusBarHeight;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarHeight = StatusBarTools.getStatusBarHeight(context);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight);
    }
}
