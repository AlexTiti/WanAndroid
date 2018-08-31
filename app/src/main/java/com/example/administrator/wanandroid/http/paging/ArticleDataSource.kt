package com.example.administrator.wanandroid.http.paging

import com.example.administrator.paging.paging.BaseDataSource
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.Api
import com.example.library.helper.RxHelper
import java.util.concurrent.Executor

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
class ArticleDataSource(private val api: Api, retryExecutor: Executor) : BaseDataSource<Int, ArticleBean>(retryExecutor) {
    var page = 0


    override fun setKey(item: ArticleBean) = item.id

    override fun setLoadAfterResult(params: LoadParams<Int>, callback: LoadCallback<ArticleBean>) {
        api.getArticleList(page)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    page = it.data?.curPage!!
                    networkSuccess()
                    callback.onResult(it.data!!.datas!!)
                }, {
                    networkFailed(it.message, params, callback)
                })
    }

    override fun setLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<ArticleBean>) {
        api.getArticleList(page)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({

                    page = it.data?.curPage!!
                    refreshSuccess()
                    networkSuccess()
                    callback.onResult(it?.data!!.datas!!)

                }, {
                    refreshFailed(it.message, params, callback)
                })
    }
}