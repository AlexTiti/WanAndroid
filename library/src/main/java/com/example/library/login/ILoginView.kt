package com.example.administrator.login.aop.login

/**
 * @author  : Alex
 * @date    : 2018/09/03
 * @version : V 2.0.0
 */
/**
 * 登陆接口
 */
interface ILoginView {

    /**
     * 判断是否登陆
     * @return true 表示登陆状态
     */
    fun isLogin(): Boolean

    /**
     * 实现登陆（或跳转到登陆的功能）
     * @param userDefine 表示操作的标志
     */
    fun login(userDefine: Int)

    /**
     * 推出登陆功能
     */
    fun exitLogin()
}