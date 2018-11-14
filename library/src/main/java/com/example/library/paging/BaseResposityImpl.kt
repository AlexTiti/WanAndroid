package com.example.library.paging

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.administrator.paging.paging.BaseDataSourceFactory
import com.example.administrator.paging.paging.BaseItemDataSource
import com.example.administrator.paging.paging.base.Listing
import com.example.administrator.paging.paging.base.Resposity
import java.lang.Exception

/**
 * @author  : Alex
 * @date    : 2018/10/29
 * @version : V 2.0.2
 */
data class BaseResposityImpl <T, M>(var itemDataSource: BaseItemDataSource<T,M>) : Resposity<M> {



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
     fun createDataBaseFactory(): BaseDataSourceFactory<T, M>{
        if (itemDataSource == null){
         throw Exception("必须添加BaseItemDataSource的实例")
        }
       val itemDataFactory = object :BaseDataSourceFactory<T,M>(){

            override fun createDataSource(): BaseItemDataSource<T, M> {
                return itemDataSource!!
            }
        }
        return itemDataFactory!!

    }

    companion object class Builder<T,M>(){

        var itemDataSource: BaseItemDataSource<T,M>? = null

        fun buildDataSource(itemDataSource: BaseItemDataSource<T,M>):Builder<T,M>{
            this.itemDataSource = itemDataSource
            return this
        }


        fun build():BaseResposityImpl<T,M>{
            if (itemDataSource == null){
                throw Exception("必须添加BaseItemDataSource的实例")
            }
          return BaseResposityImpl<T,M>(itemDataSource!!)
        }

    }


}