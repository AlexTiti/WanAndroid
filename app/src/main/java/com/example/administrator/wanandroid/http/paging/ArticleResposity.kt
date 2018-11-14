package com.example.administrator.wanandroid.http.paging

import com.example.administrator.paging.paging.BaseDataSourceFactory
import com.example.administrator.paging.paging.BaseResposity
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.Api
import java.util.concurrent.Executor

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
class ArticleResposity(private val api: Api, private val retryExecutor: Executor) : BaseResposity<Int, ArticleBean>() {

    override fun createDataBaseFactory(): BaseDataSourceFactory<Int, ArticleBean> {
        return ArticleDataSourceFactory(api, retryExecutor)
    }
}