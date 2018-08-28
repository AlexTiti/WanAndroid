package com.example.library.anim

import android.content.Context
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

/**
 *
 * @date 2017/9/11
 *
 *
 * Toolbar动画Manager
 */

object ToolbarAnimManager {
    /**
     * Toolbar 进场动画
     *
     *
     * ActionMenuView渐变动画
     *
     * @param context context
     * @param toolbar toolbar
     */
    fun animIn(context: Context, toolbar: Toolbar) {
        var ibIcon: ImageButton? = null
        var tvTitle: TextView? = null
        var amvTheme: ActionMenuView? = null
        val childCount = toolbar.childCount
        for (i in 0 until childCount) {
            val child = toolbar.getChildAt(i)
            if (child is ImageButton) {
                ibIcon = child
                continue
            }

            if (child is ActionMenuView) {
                amvTheme = child
                continue
            }

            if (child is TextView) {
                tvTitle = child
            }
        }

        if (ibIcon != null) {
            animNavigationIcon(context, ibIcon)
        }

        if (tvTitle != null) {
            animTitle(context, tvTitle)
        }

        if (amvTheme != null) {
            animMenu(context, amvTheme)
        }
    }

    /**
     * Toolbar Title动画
     *
     *
     * NavigationIcon渐变动画
     *
     * @param context  context
     * @param imageButton 执行动画的view
     */
    private fun animNavigationIcon(context: Context, imageButton: ImageButton) {
        AnimManager.animAlphaAndScaleX(context, imageButton, 500, 900)
    }

    /**
     * Toolbar Title动画
     *
     *
     * ActionMenuView渐变动画
     *
     * @param context  context
     * @param textView 执行动画的view
     */
    private fun animTitle(context: Context, textView: TextView) {
        AnimManager.animAlphaAndScaleX(context, textView, 500, 900)
    }

    /**
     * Toolbar ActionMenuView动画
     *
     *
     * ActionMenuView渐变动画
     *
     * @param context context
     * @param avm     执行动画的view
     */
    private fun animMenu(context: Context, avm: ActionMenuView) {
        AnimManager.animAlphaAndScale(context, avm, 500, 200) // filter
        AnimManager.animAlphaAndScale(context, avm, 700, 200) // overflow
    }
}
