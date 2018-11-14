package com.example.administrator.wanandroid.model

import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.administrator.wanandroid.respository.LoginResposity

/**
 * @author  : Alex
 * @date    : 2018/10/18
 * @version : V 2.0.0
 */
class LoginModel(val loginResposity: LoginResposity) : ViewModel() {

    val loginState = Transformations.map(loginResposity.login) { it }!!
    val register = Transformations.map(loginResposity.register) { it }!!

    public fun login(account: String, pswd: String) {
        loginResposity.login(account, pswd)
    }

    public fun register(account: String, pswd: String, rpswd: String) {
        loginResposity.register(account, pswd, rpswd)
    }

}