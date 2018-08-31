package com.example.administrator.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.ui.MainActivity
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.fragment_page.*

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
class ProjectFragment : BaseCompatFragment() {

    override val layoutId = R.layout.fragment_project

    override fun initUI(view: View, savedInstanceState: Bundle?) {

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
