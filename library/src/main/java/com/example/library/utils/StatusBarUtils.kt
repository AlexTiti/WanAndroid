package com.example.library.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


/**
 *
 *
 * StatusBar工具类
 *
 * @author Administrator
 */
object StatusBarUtils {

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    private fun setColor(activity: Activity, @ColorInt color: Int) {
        setBarColor(activity, color)
    }

    /**
     * 设置状态栏背景色
     * 4.4以下不处理
     * 4.4使用默认沉浸式状态栏
     *
     * @param color 要为状态栏设置的颜色值
     */
    private fun setBarColor(activity: Activity, color: Int) {
        val win = activity.window
        val decorView = win.decorView
        //沉浸式状态栏(4.4-5.0透明，5.0以上半透明)
        win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //android5.0以上设置透明效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //让应用的主体内容占用系统状态栏的空间
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = decorView.systemUiVisibility or option
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //设置状态栏背景色
            win.statusBarColor = color
        }
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        setColor(activity, Color.LTGRAY)
    }

    /**
     * 修正 Toolbar 的位置
     * 在 Android 4.4 版本下无法显示内容在 StatusBar 下，所以无需修正 Toolbar 的位置
     *
     * @param toolbar Toolbar
     */
    fun fixToolbar(toolbar: Toolbar, activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val statusHeight = ScreenUtils.getStatusBarHeight(activity)
            val layoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0, statusHeight, 0, 0)
        }
    }


}
