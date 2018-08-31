package com.example.administrator.paging.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.administrator.paging.paging.base.Resposity


/**
 * BasePagingViewModel
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
open class BasePagingViewModel<T>(resposity: Resposity<T>) : ViewModel() {

    //开始时建立DataSource和LiveData<Ling<StudentBean>>的连接
    val data = MutableLiveData<Int>()

    // map的数据修改时，会执行studentResposity 重新创建 LiveData<Ling<StudentBean>>
    private val repoResult = Transformations.map(data) {
        resposity.getDataList(it)
    }

    // 从Ling对象中获取要观察的数据，调用switchMap当repoResult 修改时会自动更新 生成的LiveData
    // 监听加载的数据
    val pagedList = Transformations.switchMap(repoResult) {
        it.pagedList
    }
    // 网络状况
    val networkStatus = Transformations.switchMap(repoResult) { it.networkStatus }
    // 刷新和加载更多的状态
    val refreshState = Transformations.switchMap(repoResult) { it.refreshState }

    /**
     * 执行刷新操作
     */
    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    /**
     * 设置每次加载次数，初始化 data 和 repoResult
     * @param int 加载个数
     */
    fun setPageSize(int: Int = 10): Boolean {
        if (data.value == int)
            return false
        data.value = int
        return true
    }

    /**
     * 执行点击重试操作
     */
    fun retry() {
        repoResult.value?.retry?.invoke()
    }
}