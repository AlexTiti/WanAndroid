package com.example.administrator.wanandroid.ui.fragment

import android.annotation.SuppressLint
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
import com.example.administrator.wanandroid.adapter.ArticleAdapter
import com.example.administrator.wanandroid.adapter.ArticleDiffUtil
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.RetrofitApi
import com.example.administrator.wanandroid.http.paging.TagArticleRepository
import com.example.administrator.wanandroid.listener.OnItemClickListener
import com.example.administrator.wanandroid.model.TagArticleViewModel
import com.example.administrator.wanandroid.ui.ArticleDetailActivity
import com.example.administrator.wanandroid.ui.TagArticleActivity
import com.example.administrator.wanandroid.utils.FIXED_EXECUTOR
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.article_fragment.*

@SuppressLint("ValidFragment")
/**
 * @author  : Alex
 * @date    : 2018/10/24
 * @version : V 2.0.0
 */
class TagArticleFragment(var articleId:Int = -1)  : BaseCompatFragment(), OnItemClickListener<ArticleBean> {

    private var articleTagName: String? = null


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

    private val adapter by lazy {
        ArticleAdapter(ArticleDiffUtil())
    }

    private val model by lazy {
        initModel()
    }


    override val layoutId = R.layout.article_fragment


    override fun initUI(view: View, savedInstanceState: Bundle?) {
        if (activity is TagArticleActivity && activity?.intent != null) {
            articleId = activity?.intent!!.getIntExtra(CATEGORY_ID, -1)
            articleTagName = activity?.intent!!.getStringExtra(CATEGORY_NAME)
            (activity as TagArticleActivity).initTool(articleTagName!!)
        }
        initSwipe()
        initRecyclerView()
    }

    override fun lazyLoadData() {
        model.setPageSize(5)
    }

    private fun initModel(): TagArticleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val api = RetrofitApi.instence
                val articleResposity = TagArticleRepository(api, FIXED_EXECUTOR, articleId)
                return TagArticleViewModel(articleResposity) as T
            }
        })[TagArticleViewModel::class.java]
    }

    private fun initRecyclerView() {
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
}