package com.example.administrator.wanandroid.http.paging

import android.annotation.SuppressLint
import com.example.administrator.paging.paging.BaseItemDataSource
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.http.Api
import com.example.library.helper.RxHelper
import io.reactivex.functions.Consumer
import java.util.concurrent.Executor

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
class CollectArticleSource(val api: Api, executor: Executor) : BaseItemDataSource<Int, CollectArticleBean>(executor) {

    var page = 0
    override fun setKey(item: CollectArticleBean) = item.id

    @SuppressLint("CheckResult")
    override fun setLoadAfterResult(params: LoadParams<Int>, callback: LoadCallback<CollectArticleBean>) {
        api.getCollectArticleList(page)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    page = it.data!!.curPage
                    networkSuccess()
                    callback.onResult(it.data?.datas!!)
                }, Consumer {
                    networkFailed(it.message, params, callback)
                })
    }

    @SuppressLint("CheckResult")
    override fun setLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<CollectArticleBean>) {
        api.getCollectArticleList(page)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    page = it.data!!.curPage
                    refreshSuccess()
                    callback.onResult(it.data?.datas!!)
                }, Consumer {
                    refreshFailed(it.message,params,callback)
                })
    }
}