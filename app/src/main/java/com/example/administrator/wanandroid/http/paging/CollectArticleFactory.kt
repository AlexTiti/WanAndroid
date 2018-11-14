package com.example.administrator.wanandroid.http.paging

import com.example.administrator.paging.paging.BaseItemDataSource
import com.example.administrator.paging.paging.BaseDataSourceFactory
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.http.Api
import java.util.concurrent.Executor

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
class CollectArticleFactory(private val api: Api, private val retryExecutor: Executor) : BaseDataSourceFactory<Int, CollectArticleBean>() {

    override fun createDataSource(): BaseItemDataSource<Int, CollectArticleBean> {
        return CollectArticleSource(api, retryExecutor)
    }
}