package com.example.administrator.wanandroid.ui.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.administrator.wanandroid.Constants
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.ArticleAdapter
import com.example.administrator.wanandroid.adapter.ArticleDiffUtil
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.RetrofitApi
import com.example.administrator.wanandroid.http.paging.QueryArticleRepository
import com.example.administrator.wanandroid.listener.OnItemClickListener
import com.example.administrator.wanandroid.ui.ArticleDetailActivity
import com.example.administrator.wanandroid.utils.FIXED_EXECUTOR
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.search_result_fragment.*

@SuppressLint("ValidFragment")
class SearchResultFragment(val string: String?) : BaseCompatFragment() , OnItemClickListener<ArticleBean> {
    override val layoutId = R.layout.search_result_fragment

    override fun initUI(view: View, savedInstanceState: Bundle?) {

    }

    override fun lazyLoadData() {

    }

    override fun itemClick(t: ArticleBean, position: Int) {
        val intent = Intent()

        intent.putExtra(Constants.CONTENT_Id, t.id)
        intent.putExtra(Constants.CONTENT_URL, t.link)
        intent.putExtra(Constants.CONTENT_TITLE, t.title)
        intent.putExtra(Constants.CONTENT_AUTHOR, t.author)
        intent.putExtra(Constants.CONTENT_CHAPTER, t.chapterName)
        intent.putExtra(Constants.CONTENT_CHAPTER_ID, t.chapterId)

        startActivity(ArticleDetailActivity::class.java, intent)
    }

    private var key: String = "Studio3"

    companion object {
        fun newInstance(string: String?) = SearchResultFragment(string)
    }

    private val adapter by lazy {
        ArticleAdapter(ArticleDiffUtil())
    }

    private lateinit var viewModel: SearchResultViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (string == null){
            return
        }
        initViewMOdel()
        initRecyclerView()
    }

    private fun initViewMOdel(){

        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val api = RetrofitApi.instence
                val articleResposity = QueryArticleRepository(string!!, api, FIXED_EXECUTOR)
                return SearchResultViewModel(articleResposity) as T
            }
        })[SearchResultViewModel::class.java]
    }

    private fun initRecyclerView() {

        rvSearchResult.adapter = adapter

        adapter.let { it.itemClickListener = this }

        viewModel.pagedList.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.setPageSize(5)
    }

}
