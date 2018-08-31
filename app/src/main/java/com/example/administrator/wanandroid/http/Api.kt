package com.example.administrator.wanandroid.http


import com.example.administrator.wanandroid.bean.ArticleResponseBody
import com.example.administrator.wanandroid.bean.HttpResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
const val BASE_URL = "http://www.wanandroid.com/"

interface Api {




    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param page
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<HttpResponse<ArticleResponseBody>>

}