package com.example.administrator.wanandroid.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.administrator.paging.paging.base.Status
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.ArticleAdapter
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.Api
import com.example.administrator.wanandroid.http.BASE_URL
import com.example.administrator.wanandroid.http.paging.ArticleResposity
import com.example.administrator.wanandroid.http.paging.ArticleViewModel
import com.example.library.base.fragment.BaseCompatFragment
import com.example.library.helper.RetrofitCreateHelper
import kotlinx.android.synthetic.main.article_fragment.*
import java.util.concurrent.Executors

/**
 * @author  : Alex
 * @date    : 2018/08/29
 * @version : V 2.0.0
 */
class ArticleFragment : BaseCompatFragment(), ArticleAdapter.ItemClickListener {
    override fun itemClick(t: ArticleBean) {
        t.isCollect = !t.isCollect

    }

    override val layoutId = R.layout.article_fragment

    val diffUtil =
            object : DiffUtil.ItemCallback<ArticleBean>() {
                override fun areItemsTheSame(p0: ArticleBean, p1: ArticleBean): Boolean {
                    return p0.id == p1.id
                }

                override fun areContentsTheSame(p0: ArticleBean, p1: ArticleBean): Boolean {
                    return p0 == p1
                }

            }

    private val adapter by lazy {
        ArticleAdapter(diffUtil)
    }
    private val linearLayoutManager by lazy {
        LinearLayoutManager(mActivity)
    }
    val executor = Executors.newFixedThreadPool(5)!!

    val model by lazy {
        initModel()
    }

    override fun initUI(view: View, savedInstanceState: Bundle?) {
        initSwipe()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        rvArticle.layoutManager = linearLayoutManager
        rvArticle.adapter = adapter
        adapter.let { it.itemClickListener = this }
        model.pagedList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initSwipe() {
        swRefreshArticle.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE)
        swRefreshArticle.setOnRefreshListener { model.refresh() }
        model.refreshState.observe(this, Observer {
            swRefreshArticle.isRefreshing = it?.status == Status.LOADING
        })

    }

    private fun initModel(): ArticleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val api = RetrofitCreateHelper.createApi(Api::class.java, BASE_URL)
                val articleResposity = ArticleResposity(api, executor)
                return ArticleViewModel(articleResposity) as T
            }
        })[ArticleViewModel::class.java]
    }

    override fun lazyLoadData() {
        model.setPageSize(5)
    }

    companion object {
        fun newInstance() = ArticleFragment()
    }
}