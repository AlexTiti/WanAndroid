package com.example.administrator.paging.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource

/**
 * 创建 DataSourceFactory，用于创建LiveData<PageList<T>>
 *     T:表示区分每个Item标志的类型 如：id ： Int
 *     M:表示获取数据的Bean
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
abstract class BaseDataSourceFactory<T, M> : DataSource.Factory<T, M>() {

    //创建观察的LivaData<DataSource> ,操作的改变都是修改sourceLivaData的值，触发系列操作
    val sourceLivaData = MutableLiveData<BaseItemDataSource<T, M>>()

    override fun create(): BaseItemDataSource<T, M> {
        val dataSource: BaseItemDataSource<T, M> = createDataSource()
        sourceLivaData.postValue(dataSource)
        return dataSource
    }

    /**
     * 创建 实现的DataSources实例
     */
    abstract fun createDataSource(): BaseItemDataSource<T, M>

}