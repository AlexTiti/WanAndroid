package com.example.library.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * 显示相关工具类
 */
object DisplayUtils {

    /**
     * 将px值转换为dp值
     */
    fun px2dp(pxValue: Float, context: Context): Int {
        val scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, context.resources.displayMetrics)

        return scale.toInt()
    }

    /**
     * 将dp值转换为px值
     */
    fun dp2px(dpValue: Float, context: Context): Int {
        val scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics)
        return scale.toInt()
    }

    /**
     * 将px值转换为sp值
     */
    fun px2sp(pxValue: Float, context: Context): Int {
        val scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, context.resources.displayMetrics)
        return scale.toInt()
    }

    /**
     * 将sp值转换为px值
     */
    fun sp2px(spValue: Float, context: Context): Int {
        val scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics)
        return scale.toInt()
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidthPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeightPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }


}
