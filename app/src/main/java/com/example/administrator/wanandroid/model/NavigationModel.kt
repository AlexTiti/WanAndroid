package com.example.administrator.wanandroid.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.administrator.wanandroid.bean.CategoryBean
import com.example.administrator.wanandroid.http.RetrofitApi
import com.example.library.helper.RxHelper
import io.reactivex.functions.Consumer

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
class NavigationModel : ViewModel() {


    val listLive = MutableLiveData<List<CategoryBean>>()

    fun getCategory(){
        RetrofitApi.instence.getCategories()
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    listLive.value = it.data
                })
    }

}