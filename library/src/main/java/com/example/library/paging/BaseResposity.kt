package com.example.administrator.paging.paging

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.administrator.paging.paging.base.Listing
import com.example.administrator.paging.paging.base.Resposity

/**
 * BaseResposity 配置并实例化LivePagedListBuilder（）对象，根据设定的监听状态和数据，封装List<M>对象
 * T :
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
abstract class BaseResposity<T, M> : Resposity<M> {

    /**
     * 配置PagedList.Config实例化List<M>对象，初始化加载的数量默认为{@link #pageSize} 的两倍
     *  @param pageSize : 每次加载的数量
     */
    override fun getDataList(pageSize: Int): Listing<M> {

        val pageConfig = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setPrefetchDistance(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()

        val stuDataSourceFactory = createDataBaseFactory()
        val pagedList = LivePagedListBuilder(stuDataSourceFactory, pageConfig)
        val refreshState = Transformations.switchMap(stuDataSourceFactory.sourceLivaData) { it.refreshStatus }
        val networkStatus = Transformations.switchMap(stuDataSourceFactory.sourceLivaData) { it.networkStatus }

        return Listing<M>(
                pagedList.build(),
                networkStatus,
                refreshState,
                refresh = {
                    stuDataSourceFactory.sourceLivaData.value?.invalidate()
                },
                retry = {
                    stuDataSourceFactory.sourceLivaData.value?.retryFailed()
                }
        )
    }

    /**
     * 创建DataSourceFactory
     */
    abstract fun createDataBaseFactory(): BaseDataSourceFactory<T, M>
}