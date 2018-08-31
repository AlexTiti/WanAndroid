package com.example.administrator.paging.paging.base

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

/**
 * 用于封装需要监听的对象和执行的操作，用于系统交互
 * pagedList : 观察获取数据列表
 * networkStatus：观察网络状态
 * refreshState ： 观察刷新状态
 * refresh ： 执行刷新操作
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val networkStatus: LiveData<Resource<String>>,
        val refreshState: LiveData<Resource<String>>,
        val refresh: () -> Unit,
        val retry: () -> Unit)
