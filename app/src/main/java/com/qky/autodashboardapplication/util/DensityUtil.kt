package com.qky.autodashboardapplication.util

import android.content.Context

/**
 * DensityUtil class
 *
 * @author kaiyuan.qin
 * @date 2024/3/15
 */
class DensityUtil {
    /**
     * dp转px
     */
    fun dip2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }
    /**
     * px转dp
     */
    fun px2dip(context: Context, pxValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxValue / scale + 0.5f
    }
}