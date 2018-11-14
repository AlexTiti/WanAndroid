package com.example.administrator.paging.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import com.example.administrator.paging.paging.base.Resource
import java.util.concurrent.Executor


/**
 * The {@link BaseItemDataSource} 设置配置DataSource必须的参数和方法
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
abstract class BaseItemDataSource<T, M>(private var retryExecutor: Executor) : ItemKeyedDataSource<T, M>() {
    /**
     * 控制重新加载
     */
    private var retry: (() -> Any)? = null
    /**
     * 检测网络状态
     */
     val networkStatus by lazy {
        MutableLiveData<Resource<String>>()
    }
    /**
     * 检测加载状态
     */
    val refreshStatus by lazy {
        MutableLiveData<Resource<String>>()
    }

    /**
     * 重新加载的方法
     */
    fun retryFailed() {
        val preRetry = retry
        retry = null
        preRetry.let {
            retryExecutor.execute {
                it?.invoke()
            }
        }
    }

    /**
     * 初始化时的加载
     */
    override fun loadInitial(params: LoadInitialParams<T>, callback: LoadInitialCallback<M>) {
        refreshStatus.postValue(Resource.loading())
        setLoadInitial(params, callback)
    }

    /**
     * 加载更多
     */
    override fun loadAfter(params: LoadParams<T>, callback: LoadCallback<M>) {
        networkStatus.postValue(Resource.loading())
        setLoadAfterResult(params, callback)
    }

    override fun loadBefore(params: LoadParams<T>, callback: LoadCallback<M>) {
    }

    /**
     * 加载、刷新成功
     */
    fun refreshSuccess() {
        refreshStatus.postValue(Resource.success())
        retry = null
    }

    /**
     * 网络状态可用
     */
    fun networkSuccess() {
        retry = null
        networkStatus.postValue(Resource.success())
    }

    /**
     * 加载、刷新失败
     */
    fun networkFailed(msg: String?, params: LoadParams<T>, callback: LoadCallback<M>) {
        networkStatus.postValue(Resource.error(msg))
        retry = {
            loadAfter(params, callback)
        }
    }

    /**
     * 网络状态不可用
     */
    fun refreshFailed(msg: String?, params: LoadInitialParams<T>, callback: LoadInitialCallback<M>) {
        refreshStatus.postValue(Resource.error(msg))
        retry = {
            loadInitial(params, callback)
        }
    }


    override fun getKey(item: M) = setKey(item)

    /**
     * 设置区分item的标志
     */
    abstract fun setKey(item: M): T

    /**
     * 加载更多
     */
    abstract fun setLoadAfterResult(params: LoadParams<T>, callback: LoadCallback<M>)

    /**
     * 初始化刷新
     */
    abstract fun setLoadInitial(params: LoadInitialParams<T>, callback: LoadInitialCallback<M>)
}


