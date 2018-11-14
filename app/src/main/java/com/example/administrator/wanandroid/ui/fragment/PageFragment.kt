package com.example.administrator.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.ViewPageAdapter
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.fragment_page.*

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
class PageFragment : BaseCompatFragment() {

    override val layoutId = R.layout.fragment_page

    private val supportFragmentManager by lazy {
        activity?.supportFragmentManager
    }

    private val fragmentList: MutableList<BaseCompatFragment> by lazy {
        mutableListOf<BaseCompatFragment>()
    }

    val adapter by lazy {
        ViewPageAdapter(supportFragmentManager!!)
    }


    override fun initUI(view: View, savedInstanceState: Bundle?) {
        fragmentList.let {
            it.add(ArticleFragment.newInstance())
            it.add(CollectArticleFragment.newInstance())
            it.add(PlanArticleFragment.newInstance())
        }
        adapter.setFragments(fragmentList)

        vpMain.adapter = adapter
        vpMain.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(vpMain)
    }


    override fun lazyLoadData() {


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment PageFragment.
         */
        fun newInstance(): PageFragment {
            return PageFragment()
        }
    }

}
