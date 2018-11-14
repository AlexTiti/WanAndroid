package com.example.administrator.wanandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.administrator.wanandroid.Constants.TITLE_FRAGMENTS
import com.example.library.base.fragment.BaseCompatFragment

/**
 * @author  : Alex
 * @date    : 2018/08/29
 * @version : V 2.0.0
 */
class ViewPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    var fragment = mutableListOf<BaseCompatFragment>()


    override fun getItem(p0: Int): Fragment {
        return fragment[p0]
    }

    override fun getCount() = fragment.size

    override fun getPageTitle(position: Int) = TITLE_FRAGMENTS[position]

    fun setFragments(fragments: MutableList<BaseCompatFragment>) {
        fragment = fragments
        notifyDataSetChanged()
    }
}