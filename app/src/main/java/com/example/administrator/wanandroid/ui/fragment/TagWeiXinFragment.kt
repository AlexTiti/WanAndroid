package com.example.administrator.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.administrator.wanandroid.Constants.PROJECT_FRAGMENTS
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.ProjectViewPageAdapter
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
class TagWeiXinFragment : BaseCompatFragment() {

    override val layoutId = R.layout.fragment_project

    private val supportFragmentManager by lazy {
        activity?.supportFragmentManager
    }

    private val fragmentList: MutableList<BaseCompatFragment> by lazy {
        mutableListOf<BaseCompatFragment>()
    }

    val adapter by lazy {
        ProjectViewPageAdapter(supportFragmentManager!!,PROJECT_FRAGMENTS)

    }


    override fun initUI(view: View, savedInstanceState: Bundle?) {

        fragmentList.let {
            it.add(TagArticleFragment(408))
            it.add(TagArticleFragment(409))
            it.add(TagArticleFragment(410))
            it.add(TagArticleFragment(413))
            it.add(TagArticleFragment(415))
            it.add(TagArticleFragment(417))

        }
        adapter.setFragments(fragmentList)
        vpProject.adapter = adapter
        vpProject.offscreenPageLimit = 6
        tabLayout.setupWithViewPager(vpProject)

    }

    override fun lazyLoadData() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment PageFragment.
         */
        fun newInstance(): TagWeiXinFragment {
            return TagWeiXinFragment()
        }
    }

}
