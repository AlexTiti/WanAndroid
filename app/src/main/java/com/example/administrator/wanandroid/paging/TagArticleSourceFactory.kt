package com.example.administrator.wanandroid.http.paging

import com.example.administrator.paging.paging.BaseItemDataSource
import com.example.administrator.paging.paging.BaseDataSourceFactory
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.Api
import java.util.concurrent.Executor

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
class TagArticleSourceFactory(private val api: Api, private val retryExecutor: Executor,val tagId:Int) : BaseDataSourceFactory<Int, ArticleBean>() {

    override fun createDataSource(): BaseItemDataSource<Int, ArticleBean> {
        return TagArticleSource(api, retryExecutor,tagId)
    }

}