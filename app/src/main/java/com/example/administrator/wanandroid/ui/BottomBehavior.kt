package com.example.administrator.wanandroid

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * @author  : Alex
 * @date    : 2018/08/27
 * @version : V 2.0.0
 */
class BottomBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val distance = ((dependency.layoutParams as CoordinatorLayout.LayoutParams).behavior as AppBarLayout.Behavior).topAndBottomOffset
        child.translationY = -distance.toFloat()
        return true
    }
}