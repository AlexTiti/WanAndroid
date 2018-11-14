package com.example.administrator.wanandroid

import android.arch.paging.ItemKeyedDataSource
import com.example.administrator.wanandroid.bean.ArticleBean

/**
 * @author  : Alex
 * @date    : 2018/10/22
 * @version : V 2.0.0
 */
class ItemDataSource : ItemKeyedDataSource<Int,ArticleBean>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<ArticleBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ArticleBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ArticleBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getKey(item: ArticleBean): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}