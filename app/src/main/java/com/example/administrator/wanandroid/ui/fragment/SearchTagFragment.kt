package com.example.administrator.wanandroid.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.bean.HotTagBean
import com.example.administrator.wanandroid.database.RecentSearch
import com.example.administrator.wanandroid.http.paging.SearchTagModel
import com.example.administrator.wanandroid.ui.SearchActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.search_tag_fragment.*

class SearchTagFragment : Fragment() {

    companion object {
        fun newInstance() = SearchTagFragment()
    }

    private lateinit var viewModel: SearchTagModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_tag_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchTagModel::class.java)

        viewModel.recentList.observe(this, Observer {
            tagFlowLayoutRecent.adapter = object : TagAdapter<RecentSearch>(it) {
                override fun getView(parent: FlowLayout?, position: Int, t: RecentSearch?): View {
                    val textView = LayoutInflater.from(parent?.getContext()).inflate(R.layout.tag, tagFlowLayoutRecent, false) as TextView
                    textView.text = t!!.string
                    tagFlowLayoutRecent.setOnTagClickListener { view, position, parent ->
                        (activity as SearchActivity).searchView.setQuery(it?.get(position)?.string!!, true)
                        true
                    }
                    return textView
                }
            }
        })

        viewModel.tagList.observe(this, Observer {
            hostTag.adapter = object : TagAdapter<HotTagBean.DataBean>(it) {
                override fun getView(parent: FlowLayout?, position: Int, t: HotTagBean.DataBean?): View {
                    val textView = LayoutInflater.from(parent?.getContext()).inflate(R.layout.tag, tagFlowLayoutRecent, false) as TextView
                    textView.text = t?.name
                    hostTag.setOnTagClickListener { view, position, parent ->
                        (activity as SearchActivity).searchView.setQuery(it?.get(position)?.name!!, true)
                        true
                    }
                    return textView
                }
            }
        })
        viewModel.getHostTag()
    }
}
