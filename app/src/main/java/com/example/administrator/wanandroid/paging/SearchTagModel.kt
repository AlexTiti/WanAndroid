package com.example.administrator.wanandroid.http.paging

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.administrator.wanandroid.bean.HotTagBean
import com.example.administrator.wanandroid.database.AndroidDataBase
import com.example.administrator.wanandroid.http.RetrofitApi
import com.example.library.helper.RxHelper
import io.reactivex.functions.Consumer

/**
 * @author  : Alex
 * @date    : 2018/10/29
 * @version : V 2.0.2
 */
class SearchTagModel(application: Application) : AndroidViewModel(application) {

    var tagList = MutableLiveData<List<HotTagBean.DataBean>>()
    var recentList = Transformations.map(AndroidDataBase.getInstence( getApplication()).getRecentSearchDao().getRecentSearch()){it}!!

    fun getHostTag(){
        RetrofitApi.instence.getHostTag()
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    tagList.value = it.data

                })
    }





}