package com.example.administrator.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.ProjectViewPageAdapter
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.fragment_knowledge.*


/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
class ProjectFragment : BaseCompatFragment() {

    override val layoutId = R.layout.fragment_knowledge
    private val supportFragmentManager by lazy {
        activity?.supportFragmentManager
    }

    private val fragmentList: MutableList<BaseCompatFragment> by lazy {
        mutableListOf<BaseCompatFragment>()
    }

    val adapter by lazy {
        ProjectViewPageAdapter(supportFragmentManager!!, arrayOf("全部项目","学习项目"))
    }


    override fun initUI(view: View, savedInstanceState: Bundle?) {

        fragmentList.let {
            it.add(TagArticleFragment(294))
            it.add(StudyProjectFragment())

        }
        adapter.setFragments(fragmentList)
        vpKnowledge.adapter = adapter
        tabLayout.setupWithViewPager(vpKnowledge)

    }

    override fun lazyLoadData() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment PageFragment.
         */
        fun newInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }

}
