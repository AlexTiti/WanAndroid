package com.example.administrator.wanandroid.respository

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.example.administrator.wanandroid.bean.LoginBean
import com.example.administrator.wanandroid.http.RetrofitApi
import com.example.library.helper.RxHelper
import io.reactivex.functions.Consumer

/**
 * @author  : Alex
 * @date    : 2018/10/18
 * @version : V 2.0.0
 */
class LoginResposity {

    val login = MutableLiveData<LoginBean>()
    val register = MutableLiveData<LoginBean>()

    @SuppressLint("CheckResult")
    fun login(account: String, pswd: String) {

        RetrofitApi.instence.login(account, pswd)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    login.value = it
                }, Consumer {
                    login.value = LoginBean(LoginBean.DataBean(), 500, it.message)
                })

    }

    @SuppressLint("CheckResult")
    fun register(account: String, pswd: String, rpswd: String) {

        RetrofitApi.instence.register(account, pswd, rpswd)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    register.value = it
                }, Consumer {
                    register.value = LoginBean(LoginBean.DataBean(), 500, it.message)
                })

    }


}