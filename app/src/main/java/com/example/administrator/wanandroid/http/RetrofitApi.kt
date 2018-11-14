package com.example.administrator.wanandroid.http

import com.example.library.helper.RetrofitCreateHelper

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/10/18
 */
object RetrofitApi {

    private  var api: Api? = null

    val instence: Api
        get() {
            if (api == null) {
                synchronized(RetrofitApi::class.java) {
                    if (api == null) {
                        api = RetrofitCreateHelper.createApi(Api::class.java, BASE_URL)
                    }
                }
            }
            return api!!
        }
}
