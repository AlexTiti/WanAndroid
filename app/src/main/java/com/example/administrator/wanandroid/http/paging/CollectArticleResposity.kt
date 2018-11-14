package com.example.administrator.wanandroid.http.paging

import com.example.administrator.paging.paging.BaseDataSourceFactory
import com.example.administrator.paging.paging.BaseResposity
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.http.Api
import java.util.concurrent.Executor

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
class CollectArticleResposity(private val api: Api, private val retryExecutor: Executor) : BaseResposity<Int, CollectArticleBean>() {
    override fun createDataBaseFactory(): BaseDataSourceFactory<Int, CollectArticleBean> {
        return CollectArticleFactory(api, retryExecutor)
    }
}