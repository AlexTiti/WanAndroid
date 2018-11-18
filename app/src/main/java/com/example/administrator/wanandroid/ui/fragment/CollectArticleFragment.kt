package com.example.administrator.wanandroid.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.administrator.paging.paging.base.Status
import com.example.administrator.wanandroid.Constants
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.CollectArticleAdapter
import com.example.administrator.wanandroid.adapter.CollectArticleDiffUtil
import com.example.administrator.wanandroid.listener.OnItemClickListener
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.http.RetrofitApi
import com.example.administrator.wanandroid.http.paging.CollectArticleRepository
import com.example.administrator.wanandroid.model.CollectArticleViewModel
import com.example.administrator.wanandroid.ui.ArticleDetailActivity
import com.example.administrator.wanandroid.utils.FIXED_EXECUTOR
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.article_fragment.*

@Suppress("UNCHECKED_CAST")
/**
 * @author  : Alex
 * @date    : 2018/08/29
 * @version : V 2.0.0
 */
class CollectArticleFragment : BaseCompatFragment(), OnItemClickListener<CollectArticleBean>{

    override fun itemClick(t: CollectArticleBean, position: Int){
        val intent = Intent()
        intent.putExtra(Constants.CONTENT_Id, t.originId)
        intent.putExtra(Constants.CONTENT_URL, t.link)
        intent.putExtra(Constants.CONTENT_TITLE, t.title)
        intent.putExtra(Constants.CONTENT_AUTHOR, t.author)
        intent.putExtra(Constants.CONTENT_CHAPTER, t.chapterName)
        intent.putExtra(Constants.CONTENT_CHAPTER_ID, t.chapterId)
        startActivity(ArticleDetailActivity::class.java, intent)
    }

    override val layoutId = R.layout.article_fragment


    private val adapter by lazy {
        CollectArticleAdapter(CollectArticleDiffUtil())
    }

    private val model by lazy {
        initModel()
    }

    override fun initUI(view: View, savedInstanceState: Bundle?){
        initSwipe()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        rvArticle.adapter = adapter
        adapter.let { it.itemClickListener = this }
        model.pagedList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initSwipe(){
        swRefreshArticle.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE)
        swRefreshArticle.setOnRefreshListener { model.refresh() }
        model.refreshState.observe(this, Observer {
            swRefreshArticle.isRefreshing = it?.status == Status.LOADING
        })
    }

    private fun initModel(): CollectArticleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val api = RetrofitApi.instence
                val articleResposity = CollectArticleRepository(api, FIXED_EXECUTOR)
                return CollectArticleViewModel(articleResposity) as T
            }
        })[CollectArticleViewModel::class.java]
    }

    override fun lazyLoadData(){
        model.setPageSize(5)
    }

    companion object{
        fun newInstance() = CollectArticleFragment()
    }
}