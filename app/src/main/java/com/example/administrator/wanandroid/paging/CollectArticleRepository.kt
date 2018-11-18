package com.example.administrator.wanandroid.http.paging

import com.example.administrator.paging.paging.BaseDataSourceFactory
import com.example.administrator.paging.paging.BaseRepository
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.http.Api
import java.util.concurrent.Executor

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
class CollectArticleRepository(private val api: Api, private val retryExecutor: Executor) : BaseRepository<Int, CollectArticleBean>() {
    override fun createDataBaseFactory(): BaseDataSourceFactory<Int, CollectArticleBean> {
        return CollectArticleFactory(api, retryExecutor)
    }
}