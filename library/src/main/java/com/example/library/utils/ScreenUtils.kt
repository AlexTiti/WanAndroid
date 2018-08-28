package com.example.library.utils

import android.app.Activity
import android.app.ActivityGroup
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import java.lang.reflect.Field


/**
 *
 *
 * 屏幕相关工具类
 */

class ScreenUtils private constructor() {
    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {


        private val mStatusHeight = -1

        /**
         * 获取屏幕的宽度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            val manager = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

        /**
         * 获取屏幕的高度
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val manager = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * @param activity
         * @return bp
         */
        fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache ?: return null
            val frame = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top
            val bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, bmp.width, bmp.height - statusBarHeight)
            view.destroyDrawingCache()
            view.isDrawingCacheEnabled = false

            return bp
        }

        /**
         * 获取actionbar的像素高度，默认使用android官方兼容包做actionbar兼容
         *
         * @return
         */
        fun getActionBarHeight(context: Context): Int {
            var actionBarHeight = 0
            if (context is AppCompatActivity && context
                            .supportActionBar != null) {

                actionBarHeight = context.supportActionBar!!.height
            } else if (context is Activity && context.actionBar != null) {

                actionBarHeight = context.actionBar!!.height
            } else if (context is ActivityGroup) {
                if (context.currentActivity is AppCompatActivity && (context.currentActivity as AppCompatActivity)
                                .supportActionBar != null) {
                    actionBarHeight = (context
                            .currentActivity as AppCompatActivity).supportActionBar!!.height
                } else if (context.currentActivity is Activity && (context.currentActivity as Activity).actionBar != null) {
                    actionBarHeight = (context.currentActivity as Activity)
                            .actionBar!!.height
                }
            }
            if (actionBarHeight != 0) {
                return actionBarHeight
            }
            val tv = TypedValue()
            if (context.theme.resolveAttribute(android.support.v7.appcompat.R.attr
                            .actionBarSize, tv, true)) {
                if (context.theme.resolveAttribute(android.support.v7.appcompat.R.attr
                                .actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context
                            .resources.displayMetrics)
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context
                            .resources.displayMetrics)
                }
            } else {
                if (context.theme.resolveAttribute(android.support.v7.appcompat.R.attr
                                .actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context
                            .resources.displayMetrics)
                }
            }
            return actionBarHeight
        }

        /**
         * 反射获取
         */
        fun getStatusBarHeight(context: Context): Int {
            val c: Class<*>
            val obj: Any
            val field: Field
            val x: Int
            return try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field.get(obj).toString())
                context.resources.getDimensionPixelSize(x)
            } catch (e1: Exception) {
                e1.printStackTrace()
                75
            }
        }

        /**
         * 获取系统状态栏高度(会随着修改displayMetrics而改变)
         */
        fun getStatusBarHeight(): Int {
            val resources = AppUtils.context.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }

        /**
         * 获取系统状态栏高度（系统本身处于的状态）
         */
        fun getStatusBarHeightSystem(): Int {
            val resources = Resources.getSystem()
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }


        /**
         * 设置view margin
         *
         * @param v
         * @param l
         * @param t
         * @param r
         * @param b
         */
        fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
            if (v.layoutParams is ViewGroup.MarginLayoutParams) {
                val p = v.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(l, t, r, b)
                v.requestLayout()
            }
        }
    }
}
