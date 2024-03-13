package com.qky.autodashboardapplication.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * EdgeTransparentViewUtils class
 *
 * @author kaiyuan.qin
 * @date 2024/3/13
 */
public class EdgeTransparentViewUtils {
    public static int d2p(Context context, float dpi) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, context.getResources().getDisplayMetrics());
    }
}
