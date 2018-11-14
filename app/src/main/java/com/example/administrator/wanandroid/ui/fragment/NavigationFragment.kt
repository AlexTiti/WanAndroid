package com.example.administrator.wanandroid.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.CategoryAdapter
import com.example.administrator.wanandroid.bean.CategoryBean
import com.example.administrator.wanandroid.listener.OnItemNavigationClickListener
import com.example.administrator.wanandroid.model.NavigationModel
import com.example.administrator.wanandroid.ui.TagArticleActivity
import com.example.library.base.fragment.BaseCompatFragment
import com.zhy.view.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.fragment_navigation.*

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
const val CATEGORY_ID = "CATEGORY_ID"
const val CATEGORY_NAME = "CATEGORY_NAME"

class NavigationFragment : BaseCompatFragment() {


    override fun lazyLoadData() {
        model.getCategory()
    }

    override val layoutId = R.layout.fragment_navigation


    private val listener: OnItemNavigationClickListener = object : OnItemNavigationClickListener {
        override fun itemClick(view: View, position: Int, parent: FlowLayout, children: List<CategoryBean>) {
            val intent = Intent(activity, TagArticleActivity::class.java)
            intent.putExtra(CATEGORY_ID, children[position].id)
            intent.putExtra(CATEGORY_NAME, children[position].name)
            startActivity(intent)
        }
    }

    private val adapter by lazy {
        CategoryAdapter(listener)
    }

    private val model by lazy {
        initModel()
    }

    override fun initUI(view: View, savedInstanceState: Bundle?) {

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_navigation_layout.adapter = adapter
        rv_navigation_layout.layoutManager = LinearLayoutManager(activity)
        model.listLive.observe(this, Observer {
            adapter.list = it
        })
    }


    private fun initModel(): NavigationModel {
        return ViewModelProviders.of(activity!!)[NavigationModel::class.java]
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment PageFragment.
         */
        fun newInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }

}
